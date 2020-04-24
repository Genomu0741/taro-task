# taro-task
## firestore_helper
--------
# Firestore Helper Spec.
###### tags: `程式專案`,`Firestore`
## 簡易UML
![](https://i.imgur.com/6tn0Jnn.jpg)
## package概覽
>- **DBCommand**
>    - 操作瀚文的巨觀命令
>    - 抽象方法work
>- **DBEmcee**
>    - 發送Firestore資料給UI的介面
>    - 方法goLive有兩個重載
>    - 具有IntentFilter的「ACTION常字串」
>- **DBReceiver**
>    - 繼承**BroadcastReceiver**
>    - 實作onReceive提供extras的分流
>    - 抽象方法onReceiveText接onReceive的參數
>    - 抽象方法onReceiveRecycler接onReceive的參數
>    - 具有extras「描述常字串」
>- **HanWen**
>    - 接觸Firestore的類別
>    - 建構子初始化集合的起始路徑
>    - sprout系方法，在指定的集合、文件中寫入資料
>    - seek系方法，在指定的集合進行讀取(包括篩選複合條件)
>    
## 使用前置
### 建立package
在根目錄(\app\src\main\java)底下建立package
取名為**genomu.firestore_helper**
將四個類別複製丟進去
![](https://i.imgur.com/Hb3VCwr.png)

### 找到mafifest
加入下列段落
```xml=
<receiver
        android:name="genomu.firestore_helper.DBReceiver"
        android:enabled="true"
        android:exported="true" />
```
## 操作方式
### 1.建立具體命令
#### 分成兩類:Set、Get，通常前者不需要推送更新UI的資料(不用實作DBEmcee)
#### 有三種作法：匿名、內部以及外部類別(相對於UI類別)，這裡示範外部類別
**SetCommand**
(1) 建構子可用DI(Dependency Injection)，降低耦合度，形成更巨觀、抽象的命令
(2) work實作，基本上就是透過瀚文sprout系的方法設定資料
> !!如需要監聽過程可以透過sproutOnComplete方法
```java=
package com.genomu.myapplication;


import com.genomu.myapplication.data.User;

import genomu.firestore_helper.DBCommand;
import genomu.firestore_helper.HanWen;

/***
 * 低耦合度寫法
 * */
public class SetCommand extends DBCommand {
    private Object object;
    public SetCommand(HanWen hanWen,Object object) {
        super(hanWen);
        this.object = object;
    }

    @Override
    public void work() {
        hanWen.sprout(object);
    }
}


/*******
 *  中耦合度寫法
public class SetCommand extends DBCommand {
    private User user;
    public SetCommand(HanWen hanWen,User user) {
        super(hanWen);
        this.user = user;
    }

    @Override
    public void work() {
        hanWen.sprout(user);
    }
}
 *************************************************/


/*******
 *  高耦合度寫法
public class SetCommand extends DBCommand {
    public SetCommand(HanWen hanWen) {
        super(hanWen);
    }

    @Override
    public void work() {
        hanWen.sprout(new User());
    }
}
 *************************************************/
```
**GetCommand**
(1) 實作DBEmcee介面、必須多一個Activity屬性
(2) 建構子可用DI(Dependency Injection)，降低耦合度，形成更巨觀、抽象的命令
(3) work實作，可以純使用瀚文的seek系方法或是複合使用兩系的方法
(4) seek系的方法會回傳Query或其子類別，再傳給goLive()做資料析出
(5) goLive實作，基本上檢索方式是固定的，可以去看firestore指南
```java=
query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
    @Override
    public void onComplete(@NonNull Task<QuerySnapshot> task) {
        if (task.isSuccessful()){
            for(QueryDocumentSnapshot document:task.getResult()){
                //...retrieving by document.toObject()
            }
        }
    }
});
```
(6) 在onComplete中透過Intent底下的putExtra傳資料並用activity廣播
```java=
User user = document.toObject(User.class);
Intent intent = new Intent(ACTION01); //使用01或02
intent.putExtra(DBReceiver.DES_POJO,user); //第一項參數描述資料的型態
activity.sendBroadcast(intent);
```
最終形成：
```java=
package com.genomu.myapplication;

import ...;

public class GetCommand extends DBCommand implements DBEmcee {
    private static final String TAG = GetCommand.class.getSimpleName();
    private Activity activity;
    public GetCommand(HanWen hanWen,Activity activity) {
        super(hanWen);
        this.activity = activity;
    }

    @Override
    public void work() {
        Query q =hanWen.seek("name","Roger");
        goLive(q);
    }

    @Override
    public void goLive(Query query) {
        query.get().addOnCompleteListener(
        new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                    for(QueryDocumentSnapshot document:task.getResult()){
                            User user = document.toObject(User.class);
                            Intent intent = new Intent(ACTION01);
                            intent.putExtra(DBReceiver.DES_POJO,user);
                            activity.sendBroadcast(intent);
                    }
                }
            }
        });
    }

    @Override
    public void goLive(Task<DocumentSnapshot> snapshotTask) {

    }
}

```
### 2.建立具體接收者
#### 同樣有三種作法：匿名、內部以及~~外部類別~~(不建議)，這裡示範內部類別
(1) 新增一個Activity屬性receiver：DBReceiver
(2) 寫內部類別繼承DBRceiver，在三個onReceive中分別會得到不同型態的資料
(3) 可以直接丟Widget、View進去更新
(4) 在主程式進點記得要藉由內部DBRceiver子類別初始化receiver
```java=
public class TestActivity ...{
    ...
    ...
    ...
    public class TestReceiver extends DBReceiver{

        @Override
        public void onReceive(String receivedString) {

        }

        @Override
        public void onReceive(List receivedList) {

        }

        @Override
        public void onReceive(Object receivedPOJO) {
            User user = (User) receivedPOJO;
            // update some views here
        }
    }
}
```
### 3.註冊接收者、組裝命令
#### 準備好接收者物件後，透過Activity底下的registerReceiver方法註冊
#### 提供瀚文、命令所需要的物件(數值、字串、自定義的POJO...)
(1) 註冊的方法所吃的參數是(BroadcastReceiver ,IntentFilter )
(2) 前者由receiver提供，後者直接用匿名類別透過DBEmcee底下的ACTION字串建構
(3) 產生命令的實例，並提供所需要的物件(包括瀚文)
(4) 在需要執行的時候叫命令底下的work
大概會是這個形式：
```java=
protected void onCreate(Bundle savedInstanceState) {
    ...
    //註冊接收者
    receiver = new TestReceiver();
    registerReceiver(receiver,new IntentFilter(ACTION01));
    //準備要提供的物件
    List<Post> posts = new ArrayList<>();
    posts.add(new Post());
    User user = new User("Bread",39,posts);
    //產生命令實例
    final DBCommand getCommand = new GetCommand(new HanWen("users"),this);
    final DBCommand setCommand = new SetCommand(new HanWen("users"), user);
    btn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //執行
            setCommand.work();
            getCommand.work();
        }
    });
}
```
## 更多細節
### 擴充瀚文
待補...
### 覆寫onRecieve(Context,Intent)與自定義DES
待補...
### 取消註冊
待補...