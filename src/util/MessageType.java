package util;



public final class MessageType {
    public MessageType() {
        // TODO Auto-generated constructor stub
    }


    //Message-Type
    public static final String operFeedback = "OPER_FEEDBACK";

    public static final String fail = "FAIL";

    //用户操作
    public static final String userLogin = "USER_LOGIN";
    public static final String userLoginByNickName = "USER_LOGIN_N";
    public static final String getUserInfo = "GET_USER_INFO";
    public static final String updateUserInfo = "UPDATE_USER_INFO";
    public static final String register = "REGISTER";
    public static final String punishQUERY = "PUNISH_QUERY";
    //public static final String register_state = "REGISTER_STATE"; 暂时没有必要，会和oper_feedback冲突
    //图书馆
    public static final String libraryQuery = "LIBRARY_QUERY";	//初始化，客户端向服务端发起请求
    public static final String libraryInit = "LIBRARY_INIT";		//服务端向客户端发回信息
    public static final String Borrow = "BORROW";					//客户端发起借书操作
    public static final String Return ="RETURN";
    public static final String addBook = "ADD_BOOK";
    public static final String returnQuery="return_query";
    public static final String returnInit="return_init";
    //商店
    public static final String shopQuery = "SHOP_QUERY";
    public static final String shopInit = "SHOP_INIT";
    public static final String Buy = "BUY";
    public static final String Restore = "RESTORE";
    //选课
    public static final String course_query = "COURSE_QUERY";	//客户端向服务端
    public static final String courseinit = "COURSE_INIT";	//给学生或者老师发送已有课表
    public static final String choose_query = "CHOOSE_QUERY";	//学生发出选课申请
    public static final String choose_init = "CHOOSE_INIT";	//服务器给学生返回教师已开课表
    public static final String course_choose = "COURSE_CHOOSE";//学生的选课信息
    public static final String course_add = "COURSE_ADD";		//教师向服务端发送添加课的申请
    public static final String course_delete = "COURSE_DELETE";
    //添加商品或图书的管理员请求
    public static final String add_product = "ADD_PRODUCT";
    public static final String add_book = "ADD_BOOK";
    //Teacher-Title
    public static final String lecturer = "Lecturer";
    public static final String professor = "Professor";
    public static final String mentor = "Mentor";
    //Punish-Type
    public static final String none="None";
    public static final String warning = "Warning";
    public static final String demerit = "Demerit";
    public static final String expulsion = "Expulsion";

    //医院
    public static final String doc_query_all = "DOC_QUERY_ALL";
}