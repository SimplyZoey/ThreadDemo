package com.rocky.mysql;

import com.rocky.mysql.utils.DBHelper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @Author: shtian
 * @Description:
 * @Date: Create in 2018/4/10 21:44
 */
public class LockDemo {

    public void updateByNormalIndex(){
        String sql = "UPDATE student SET CODE = 103 WHERE CODE = 100";
        execUpdate(sql);
    }

    public void updateByPK(){
        String sql = "UPDATE student SET name = 'shitian_1' WHERE id = 1";
        execUpdate(sql);
    }

    private void execUpdate(String sql) {
        DBHelper helper = new DBHelper(sql);//创建DBHelper对象

        try {
            helper.pst.execute();//执行语句，得到结果集
            helper.close();//关闭连接
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args){
        LockDemo demo = new LockDemo();
        Thread t1 = new Thread(new UpdateThread(demo,1));
        Thread t2 = new Thread(new UpdateThread(demo,2));
        t1.start();
        t2.start();
    }
}

class UpdateThread implements Runnable{
    private LockDemo lockDemo;
    private int method;

    public UpdateThread(LockDemo lockDemo,int method){
        this.lockDemo = lockDemo;
        this.method = method;
    }

    @Override
    public void run() {
        if(method == 1){
            lockDemo.updateByNormalIndex();
        }else{
            lockDemo.updateByPK();
        }
    }
}
