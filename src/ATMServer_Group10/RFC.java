package ATMServer_Group10;

import javax.swing.JOptionPane;

import ATMServer_Group10.DbManager;
import ATMServer_Group10.User;
import ATMServer_Group10.DbManager;
import ATMServer_Group10.Manager;

public class RFC {
	private static String AUTH = "500 AUTH REQUIRE";
	private static String OK = "525 OK!";
	private static String ERROR = "401 ERROR!";
	private static String BYE = "BYE";
	private static String AMNT = "AMNT:";
	private DbManager dbManager;
	private String currentUser;
	
	public RFC() {
        dbManager = new DbManager();
	}
	
	public String getAUTH() {
		return AUTH;
	}
	
	public String getOK() {
		return OK;
	}
	
	public String getERROR() {
		return ERROR;
	}
	
	//收到用户账号后传递500（判断用户输入的账号是否存在）
	public String askForPw(String userid) {
		if(dbManager.checkUserid(userid)) {
			return AUTH;
		}else {
			return ERROR;
		}
	}
	
	//验证账号密码是否匹配
	public String check(String userid, String passwd) {
        User tempUser = dbManager.getUser(userid);
        if(tempUser.getPasswd().equals(passwd)) {
        	currentUser = userid;
        	return OK;
        } else {
            return ERROR;
        }
	}
	
	//向客户端传输余额
	public String sendAmount() {
		return AMNT + String.valueOf(dbManager.getAmount(currentUser));
	}
	
	//取款
	public String withdraw(Double wdra) {
		System.out.println("进入server.useRFC.withdraw函数");
		if(wdra <= dbManager.getAmount(currentUser)) {
			//修改数据库中用户余额值
			dbManager.updateAmount(currentUser, wdra);
			return OK;
		}else {
			return ERROR;
		}
	}
	
	//断开连接
	public String closeConnection() {
		return BYE;
	}
	
	//记录操作日志
	public void Journal(String userid, String operation) {
		dbManager.insertJournal(userid, operation);
	}
	
}
