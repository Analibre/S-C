package ATMServer_Group10;

import ATMServer_Group10.DbManager;
import ATMServer_Group10.User;

public class Manager {
	private DbManager dbManager;

    public Manager(DbManager dbManager) {
        this.dbManager = dbManager;
    }

    public boolean addUser(User user) {
        return dbManager.addUser(user);
    }
}
