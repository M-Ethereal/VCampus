//package server.dao;
//
//import vo.BankRecord;
//
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//
//public class BankDao {
//    private DbHelper access = new DbHelper();
//    private Connection con = access.connection;
//    private PreparedStatement stmt = null;
//    private ResultSet rs = null;
//
//
//    public Vector<BankRecord>queryRecord(String uID){
//        //查询账户历史记录
//        Vector<BankRecord> res = new Vector<>();
//        try{
//            String sql="select* from tbl_Record where uID='"+uID+"'";
//            rs= stmt.executeQuery(sql);
//            while(rs.next()){
//                BankRecord tmp=new BankRecord(rs.getString("uID"),
//                        rs.getString("record"),rs.getDouble("amount"),
//                        rs.getString("time"));
//                res.addElement(tmp);
//            }
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//        return res;
//    }
//
//
//}
