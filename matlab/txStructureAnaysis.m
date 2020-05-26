function sa=txStructureAnaysis(sPara)
% sPara should be a 1~6 by 5 matrix
% each row is [E,A,L,I,angle]
if size(sPara,2)~=5
    disp("input error");
    sa=NaN;
    return;
end
number=size(sPara,1);
fprintf('number of truss(es) : %d\n',number);
if number>6
    disp("not supported");
    sa=NaN;
    return;
end
para=zeros(6,5);
para(1:number,:)=sPara;
stiffnesses=cell(1,6);
for i=1:6
   stiffnesses(i)={zeros(6)}; 
end
for i = 1:number
   stiffness=getStiffness(para(i,1:4));
   globalStiffness=goGlobal(stiffness,para(i,5));
   fprintf('no.%d truss globalStiffness :\n',i);
   disp(globalStiffness);
   stiffnesses(i)={globalStiffness};
end
deterX=[0,1,0,2,1,0];
deterY=[1,2,2,3,3,3];
K=zeros(12);
for i =1:6
    s=cell2mat(stiffnesses(i));
   if ~isnan(s)
       K=K+splitMat(deterX(i),deterY(i),s);
   end
end
if number<2
    sa=K(1:6,1:6);
elseif number<4
    sa=K(1:9,1:9);
else
    sa=K;
end

function output=splitMat(x,y,stf)
    r1=stf(1:3,1:3);
    r2=stf(1:3,4:6);
    r3=stf(4:6,1:3);
    r4=stf(4:6,4:6);
    mat=zeros(12);
    rxs=3*x+1;
    rxe=3*x+3;
    rys=3*y+1;
    rye=3*y+3;
    mat(rxs:rxe,rxs:rxe)=r1;
    mat(rxs:rxe,rys:rye)=r2;
    mat(rys:rye,rxs:rxe)=r3;
    mat(rys:rye,rys:rye)=r4;
    output=mat;
end
end