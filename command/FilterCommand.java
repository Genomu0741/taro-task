package genomu.command;

import android.app.Activity;

import genomu.firestore_helper.DBCommand;

public class FilterCommand extends GetListCommand {
    private String field;
    private Object value;
    public FilterCommand(GetListCommand command,String field,Object value) {
        super(command);
        this.field = field;
        this.value = value;
    }

    @Override
    public void setCommandName() {
        this.commandName = "篩選";
    }

    @Override
    public void work() {
        goLive(hanWen.seek().whereEqualTo(field,value));
    }


}
