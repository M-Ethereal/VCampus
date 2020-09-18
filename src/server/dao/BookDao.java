//package server.dao;
//
//import java.sql.*;
//
//public class BookDao{
//    static Connection con=null;
//    static PreparedStatement sql=null;
//    static Statement sqll=null;
//    static ResultSet res=null;
//    public Connection getConnectionn()
//    {
//        try
//        {
//            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
//        }
//        catch(ClassNotFoundException e)
//        {
//            e.printStackTrace();
//        }
//        String path="D://Database2.accdb";
//        try
//        {
//            con=DriverManager.getConnection("jdbc:ucanaccess://"+path,"","");
//
//        }
//        catch(SQLException e)
//        {
//            e.printStackTrace();
//        }
//        System.out.println("success");
//        return con;
//    }
//
//    //增完整的一条记录
//    public static void Insert(String bookID,String bookName,String bookAuthor,boolean bookSite,
//                              boolean bookStatus,String bookTag,int bookStar,String bookPublisher,String bookPubDate)
//    {
//        LibBookCon c=new LibBookCon();
//        con=c.getConnectionn();
//        String bookSearchIndex = null;
//        bookSearchIndex=bookID+","+bookName+","+bookAuthor+","+String.valueOf(bookSite)+","+String.valueOf(bookStatus)+","+bookTag+","+bookPublisher+","+bookPubDate;
//        try
//        {
//            sql=con.prepareStatement("insert into LibBook values(?,?,?,?,?,?,?,?,?,?,?)");
//            sql.setInt(1, 1);
//            sql.setString(2, bookID);
//            sql.setString(3, bookName);
//            sql.setString(4,bookAuthor);
//
//            sql.setBoolean(5, bookSite);
//            sql.setBoolean(6, bookStatus);
//
//            sql.setString(7, bookTag);
//
//            sql.setInt(8, bookStar);
//
//            sql.setString(9, bookPublisher);
//            sql.setString(10,bookPubDate);
//            sql.setString(11, bookSearchIndex);
//            sql.executeUpdate();
//
//        }
//        catch(Exception e)
//        {
//            e.printStackTrace();
//        }
//
//
//    }
//
//    //删一条完整的记录
//    public static void Delete(String bookID)
//    {
//        LibBookCon c=new LibBookCon();
//        con=c.getConnectionn();
//        try
//        {
//            sqll=con.createStatement();
//            sqll.executeUpdate("delete from LibBook where BookID="+bookID);
//
//
//        }
//        catch(Exception e)
//        {
//            e.printStackTrace();
//        }
//
//
//    }
//
//    //改 整条记录大改 bookID不能改 它是主键 通过它来唯一确定一条记录
//    public static void UpDate(String bookID,String bookName,String bookAuthor,boolean bookSite,
//                              boolean bookStatus,String bookTag,int bookStar,String bookPublisher,String bookPubDate)
//    {
//        LibBookCon c=new LibBookCon();
//        con=c.getConnectionn();
//        try
//        {
//            if(bookName!=null)
//                sql=con.prepareStatement("update LibBook set BookName =?where BookID="+bookID);
//            sql.setString(1,bookName);
//            if(bookAuthor!=null)
//                sql=con.prepareStatement("update LibBook set BookAuthor =?where BookID="+bookID);
//            sql.setString(1,bookAuthor);
//            sql=con.prepareStatement("update LibBook set BookSite =?where BookID="+bookID);
//            sql.setBoolean(1,bookSite);
//            sql=con.prepareStatement("update LibBook set BookStatus =?where BookID="+bookID);
//            sql.setBoolean(1,bookStatus);
//            sql=con.prepareStatement("update LibBook set BookTag =?where BookID="+bookID);
//            sql.setString(1,bookTag);
//            sql=con.prepareStatement("update LibBook set BookStar =?where BookID="+bookID);
//            sql.setInt(1,bookStar);
//            sql=con.prepareStatement("update LibBook set BookPublisher =?where BookID="+bookID);
//            sql.setString(1,bookPublisher);
//            sql=con.prepareStatement("update LibBook set BookPubDate =?where BookID="+bookID);
//            sql.setString(1,bookPubDate);
//            String temp=null;
//            temp=bookID+","+bookName+","+bookAuthor+","+String.valueOf(bookSite)+","+String.valueOf(bookStatus)+","+bookTag+","+String.valueOf(bookStar)+","+bookPublisher+","+bookPubDate;
//            sql=con.prepareStatement("update LibBook set BookSearchIndex =?where BookID="+bookID);
//            sql.setString(1,temp);
//            sql.executeUpdate();
//
//        }
//        catch(Exception e)
//        {
//            e.printStackTrace();
//        }
//
//
//    }
//
//    //改 只改书名
//    public static void UpDateByBookName(String bookID,String bookNewName)
//    {
//        LibBookCon c=new LibBookCon();
//        con=c.getConnectionn();
//        try
//        {
//            sql=con.prepareStatement("update LibBook set BookName =?where BookID="+bookID);
//            sql.setString(1,bookNewName);
//            sql.executeUpdate();
//            sql=con.prepareStatement("select * from LibBook where BookID = ?");
//            sql.setString(1, bookID);
//            res=sql.executeQuery();
//            String temp=null;
//            while(res.next())
//            {
//                temp=bookID+","+res.getString(3)+","+res.getString(4)+","+String.valueOf(res.getBoolean(5))+","
//                        +String.valueOf(res.getBoolean(6))+","+res.getString(7)+","+String.valueOf(res.getInt(8))+
//                        ","+res.getString(9)+","+res.getString(10);
//                sql=con.prepareStatement("update LibBook set BookSearchIndex =?where BookID="+bookID);
//                sql.setString(1,temp);
//                sql.executeUpdate();
//            }
//
//        }
//        catch(Exception e)
//        {
//            e.printStackTrace();
//        }
//
//
//    }
//    //改 只改作者
//    public static void UpDateByBookAuthor(String bookID,String bookNewAuthor)
//    {
//        LibBookCon c=new LibBookCon();
//        con=c.getConnectionn();
//        try
//        {
//            sql=con.prepareStatement("update LibBook set BookAuthor =?where BookID="+bookID);
//            sql.setString(1,bookNewAuthor);
//            sql.executeUpdate();
//            sql=con.prepareStatement("select * from LibBook where BookID = ?");
//            sql.setString(1, bookID);
//            res=sql.executeQuery();
//            String temp=null;
//            while(res.next())
//            {
//                temp=bookID+","+res.getString(3)+","+res.getString(4)+","+String.valueOf(res.getBoolean(5))+","
//                        +String.valueOf(res.getBoolean(6))+","+res.getString(7)+","+String.valueOf(res.getInt(8))+
//                        ","+res.getString(9)+","+res.getString(10);
//                sql=con.prepareStatement("update LibBook set BookSearchIndex =?where BookID="+bookID);
//                sql.setString(1,temp);
//                sql.executeUpdate();
//            }
//
//        }
//        catch(Exception e)
//        {
//            e.printStackTrace();
//        }
//    }
//    //改 只改书是否在馆
//    public static void UpDateByBookSite(String bookID,boolean bookNewSite)
//    {
//        LibBookCon c=new LibBookCon();
//        con=c.getConnectionn();
//        try
//        {
//            sql=con.prepareStatement("update LibBook set BookSite =?where BookID="+bookID);
//            sql.setBoolean(1,bookNewSite);
//            sql.executeUpdate();
//            sql=con.prepareStatement("select * from LibBook where BookID = ?");
//            sql.setString(1, bookID);
//            res=sql.executeQuery();
//            String temp=null;
//            while(res.next())
//            {
//                temp=bookID+","+res.getString(3)+","+res.getString(4)+","+String.valueOf(res.getBoolean(5))+","
//                        +String.valueOf(res.getBoolean(6))+","+res.getString(7)+","+String.valueOf(res.getInt(8))+
//                        ","+res.getString(9)+","+res.getString(10);
//                sql=con.prepareStatement("update LibBook set BookSearchIndex =?where BookID="+bookID);
//                sql.setString(1,temp);
//                sql.executeUpdate();
//            }
//
//        }
//        catch(Exception e)
//        {
//            e.printStackTrace();
//        }
//
//
//    }
//    //改 只改本书是否可借
//    public static void UpDateByBookStatus(String bookID,boolean bookNewStatus)
//    {
//        LibBookCon c=new LibBookCon();
//        con=c.getConnectionn();
//        try
//        {
//
//            sql=con.prepareStatement("update LibBook set BookStatus =?where BookID="+bookID);
//            sql.setBoolean(1,bookNewStatus);
//            sql.executeUpdate();
//            sql=con.prepareStatement("select * from LibBook where BookID = ?");
//            sql.setString(1, bookID);
//            res=sql.executeQuery();
//            String temp=null;
//            while(res.next())
//            {
//                temp=bookID+","+res.getString(3)+","+res.getString(4)+","+String.valueOf(res.getBoolean(5))+","
//                        +String.valueOf(res.getBoolean(6))+","+res.getString(7)+","+String.valueOf(res.getInt(8))+
//                        ","+res.getString(9)+","+res.getString(10);
//                sql=con.prepareStatement("update LibBook set BookSearchIndex =?where BookID="+bookID);
//                sql.setString(1,temp);
//                sql.executeUpdate();
//            }
//
//        }
//        catch(Exception e)
//        {
//            e.printStackTrace();
//        }
//
//
//    }
//    //改 改本书的出版社
//    public static void UpDateByBookPublisher(String bookID,boolean bookNewPublisher)
//    {
//        LibBookCon c=new LibBookCon();
//        con=c.getConnectionn();
//        try
//        {
//            sql=con.prepareStatement("update LibBook set BookPublisher =?where BookID="+bookID);
//            sql.setBoolean(1,bookNewPublisher);
//            sql.executeUpdate();
//            sql=con.prepareStatement("select * from LibBook where BookID = ?");
//            sql.setString(1, bookID);
//            res=sql.executeQuery();
//            String temp=null;
//            while(res.next())
//            {
//                temp=bookID+","+res.getString(3)+","+res.getString(4)+","+String.valueOf(res.getBoolean(5))+","
//                        +String.valueOf(res.getBoolean(6))+","+res.getString(7)+","+String.valueOf(res.getInt(8))+
//                        ","+res.getString(9)+","+res.getString(10);
//                sql=con.prepareStatement("update LibBook set BookSearchIndex =?where BookID="+bookID);
//                sql.setString(1,temp);
//                sql.executeUpdate();
//            }
//
//        }
//        catch(Exception e)
//        {
//            e.printStackTrace();
//        }
//
//
//    }
//    //改本书的出版日期
//    public static void UpDateByBookPubDate(String bookID,boolean bookNewPubDate)
//    {
//        LibBookCon c=new LibBookCon();
//        con=c.getConnectionn();
//        try
//        {
//            sql=con.prepareStatement("update LibBook set BookPublDate =?where BookID="+bookID);
//            sql.setBoolean(1,bookNewPubDate);
//            sql.executeUpdate();
//            sql=con.prepareStatement("select * from LibBook where BookID = ?");
//            sql.setString(1, bookID);
//            res=sql.executeQuery();
//            String temp=null;
//            while(res.next())
//            {
//                temp=bookID+","+res.getString(3)+","+res.getString(4)+","+String.valueOf(res.getBoolean(5))+","
//                        +String.valueOf(res.getBoolean(6))+","+res.getString(7)+","+String.valueOf(res.getInt(8))+
//                        ","+res.getString(9)+","+res.getString(10);
//                sql=con.prepareStatement("update LibBook set BookSearchIndex =?where BookID="+bookID);
//                sql.setString(1,temp);
//                sql.executeUpdate();
//            }
//
//        }
//        catch(Exception e)
//        {
//            e.printStackTrace();
//        }
//
//
//    }
//
//    //模糊查询
//    public static void SearchIndistinct(String searchIndex)
//    {
//        LibBookCon c=new LibBookCon();
//        con=c.getConnectionn();
//        try
//        {
//            String sql1="select * from LibBook where BookSearchIndex like ? ";
//            sql=con.prepareStatement(sql1);
//            sql.setString(1, "%"+searchIndex+"%");
//            res=sql.executeQuery();
//            String A="在馆";
//            String B="可借";
//            String C="不在馆";
//            String D="不可借";
//            while(res.next())
//            {
//                //第5列记录表示图书是否在馆 true表示在馆 false表示不在馆 第6条记录表示图书是否可借 true表示可借 false表示不可借
//                if(res.getBoolean(5)==true&&res.getBoolean(6)==true)//在馆可借
//                    System.out.println("书号="+res.getString(2)+" 书名="+res.getString(3)+"  状态="+A+B);
//                if(res.getBoolean(5)==true&&res.getBoolean(6)==false)//在馆不可借
//                    System.out.println("书号="+res.getString(2)+" 书名="+res.getString(3)+"  状态="+A+D);
//                if(res.getBoolean(5)==false&&res.getBoolean(6)==false)//不在馆不可借
//                    System.out.println("书号="+res.getString(2)+" 书名="+res.getString(3)+"  状态="+C+D);
//            }
//        }
//        catch(Exception e)
//        {
//            e.printStackTrace();
//        }
//
//
//    }
//    //通过BookID来查询
//    public static void SearchByBookID(String bookID)
//    {
//        LibBookCon c=new LibBookCon();
//        con=c.getConnectionn();
//        try
//        {
//            String s="select * from LibBook where BookID = ?";
//            sql=con.prepareStatement(s);
//            sql.setString(1, bookID);
//            res=sql.executeQuery();
//            while(res.next())
//                System.out.println("书号="+bookID+" 书名="+res.getString(3));
//        }
//        catch(Exception e)
//        {
//            e.printStackTrace();
//        }
//
//    }
//    //通过书名来查询
//    public static void SearchByBookName(String bookName)
//    {
//        LibBookCon c=new LibBookCon();
//        con=c.getConnectionn();
//        try
//        {
//            String s="select * from LibBook where BookName = ?";
//            sql=con.prepareStatement(s);
//            sql.setString(1, bookName);
//            res=sql.executeQuery();
//            while(res.next())
//                System.out.println("书号="+res.getString(2)+" 书名="+res.getString(3));
//        }
//        catch(Exception e)
//        {
//            e.printStackTrace();
//        }
//
//    }
//    //通过书的作者来查询
//    public static void SearchByBookAuthor(String bookAuthor)
//    {
//        LibBookCon c=new LibBookCon();
//        con=c.getConnectionn();
//        try
//        {
//            String s="select * from LibBook where BookAuthor = ?";
//            sql=con.prepareStatement(s);
//            sql.setString(1, bookAuthor);
//            res=sql.executeQuery();
//            while(res.next())
//                System.out.println("书号="+res.getString(2)+" 书名="+res.getString(3));
//        }
//        catch(Exception e)
//        {
//            e.printStackTrace();
//        }
//
//    }
//    //通过书的标签来查询
//    public static void SearchByBookTag(String bookTag)
//    {
//        LibBookCon c=new LibBookCon();
//        con=c.getConnectionn();
//        try
//        {
//            String s="select * from LibBook where BookTag = ?";
//            sql=con.prepareStatement(s);
//            sql.setString(1, bookTag);
//            res=sql.executeQuery();
//            while(res.next())
//                System.out.println("书号="+res.getString(2)+" 书名="+res.getString(3));
//        }
//        catch(Exception e)
//        {
//            e.printStackTrace();
//        }
//
//    }
//    //通过本书的出版商来查询
//    public static void SearchByBookPublisher(String bookPublisher)
//    {
//        LibBookCon c=new LibBookCon();
//        con=c.getConnectionn();
//        try
//        {
//            String s="select * from LibBook where BookPublisher = ?";
//            sql=con.prepareStatement(s);
//            sql.setString(1, bookPublisher);
//            res=sql.executeQuery();
//            while(res.next())
//                System.out.println("书号="+res.getString(2)+" 书名="+res.getString(3));
//        }
//        catch(Exception e)
//        {
//            e.printStackTrace();
//        }
//
//    }
//
//    //通过本书的出版日期来查询
//    public static void SearchByBookPubDate(String bookPubDate)
//    {
//        LibBookCon c=new LibBookCon();
//        con=c.getConnectionn();
//        try
//        {
//            String s="select * from LibBook where BookPubDate = ?";
//            sql=con.prepareStatement(s);
//            sql.setString(1, bookPubDate);
//            res=sql.executeQuery();
//            while(res.next())
//                System.out.println("书号="+res.getString(2)+" 书名="+res.getString(3));
//        }
//        catch(Exception e)
//        {
//            e.printStackTrace();
//        }
//
//    }
//}
//
//
