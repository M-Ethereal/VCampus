package util;



public final class MessageType {
    public MessageType() {
        // TODO Auto-generated constructor stub
    }


    //Message-Type
    public static final String operFeedback = "OPER_FEEDBACK";


    //管理员：管理用户
    public static final String outputAllStudent = "OUTPUT_ALL_STUDENT";
    public static final String outputAllTeacher = "OUTPUT_ALL_TEACHER";
    public static final String outputAllRetailer = "OUTPUT_ALL_RETAILER";
    public static final String outputAllDoctor = "OUTPUT_ALL_DOCTOR";
    public static final String addStudent = "ADD_STUDENT";
    public static final String deleteStudent = "DELETE_STUDENT";
    public static final String updateStudent = "UPDATE_STUDENT";
    public static final String queryStudent = "QUERY_STUDENT";
    public static final String queryStudentByMajor = "QUERY_STUDENT_BY_MAJOR";
    public static final String addTeacher = "ADD_TEACHER";
    public static final String deleteTeacher = "DELETE_TEACHER";
    public static final String updateTeacher = "UPDATE_TEACHER";
    public static final String queryTeacher = "QUERY_TEACHER";
    public static final String addRetailer = "ADD_RETAILER";
    public static final String deleteRetailer = "DELETE_RETAILER";
    public static final String updateRetailer = "UPDATE_RETAILER";
    public static final String queryRetailer = "QUERY_RETAILER";
    public static final String addDoctor = "ADD_DOCTOR";
    public static final String deleteDoctor = "DELETE_DOCTOR";
    public static final String updateDoctor = "UPDATE_DOCTOR";
    public static final String queryDoctor = "QUERY_DOCTOR";


    //用户操作
    public static final String userLogin = "USER_LOGIN";
    public static final String register = "REGISTER";


    //图书馆
    public static final String outputallbooks = "OUTPUT_ALL_BOOKS";//返回所有书目
    public static final String outputallbooksbystudy="OUTPUT_ALL_BOOKS_BYSTUDY";//学业书籍推荐
    public static final String outputallbooksbyhappy="OUTPUT_ALL_BOOKS_BYHAPPY";//休闲书籍推荐
    public static final String outputbyindistinctsearch="OUTPUT_BY_INDISTINCTSEARCH";//模糊查询
    public static final String outputbybookidsearch="OUTPUT_BY_BOOKIDSEARCH";//通过书ID查询
    public static final String outputbybooknamesearch="OUTPUT_BY_BOOKNAMESEARCH";//通过书名查询
    public static final String outputbybookauthorsearch="OUTPUT_BY_BOOKAUTHORSEARCH";//通过书的作者查询
    public static final String outputbysearchbyrecordstudentidisreturn="OUTPUT_BY_SEARCHBYRECORDSTUDENTIDISRETURN";//返回我的已还
    public static final String outputbysearchbyrecordstudentidnotreturn="OUTPUT_BY_SEARCHBYRECORDSTUDENTIDNOTRETURN";//返回我的待还
    public static final String outputbysearchbyrecordid="OUTPUTBYSEARCHBYRECORDID";
    public static final String insertbook="INSERT_BOOK";//插入书
    public static final String deletelibbook="DELETE_LIBBOOK";//删除书
    public static final String updatebook="UPDATE_BOOK";//更新书的信息
    public static final String returnbook="RETURN_BOOK";//还书
    public static final String lendbook="LEND_BOOK";//借书


    //商店
    public static final String shopQuery = "SHOP_QUERY";//查询商品
    public static final String shopInit = "SHOP_INIT";//查询所有
    public static final String Buy = "BUY";//购买
    public static final String PurchaseRecordQuery = "PURCHASERECORD_QUERY";//查询购买记录
    public static final String SellingRecordQuery = "SELLINGRECORD_QUERY";//查询售出记录
    public static final String Score = "SCORE";
    public static final String shop_queryStudent = "SHOP_QUERYSTUDENT";
    public static final String shop_queryRetailer = "SHOP_QUERYRETAILER";

    public static final String add_product = "ADD_PRODUCT";


    //课程
    public static final String course_query = "COURSE_QUERY";//返回所有查询学期的课(学生
    public static final String course_query_admin = "COURSE_QUERY_ADMIN";//返回所有查询学期的课(管理员（所有课
    public static final String course_init = "COURSE_INIT";	//返回所有本学期的课
    public static final String course_choose = "COURSE_CHOOSE";//学生选课，grade表插入记录
    public static final String course_my_table = "COURSE_MYTABLE";//返回该学生该学期的课
    public static final String course_giveup = "COURSE_GIVEUP";//退课
    public static final String course_query_grade = "COURSE_QUERY_GRADE";//查成绩

    public static final String course_teacher_table = "COURSE_TEACHER_TABLE";//老师课程表
    public static final String course_studentList_query = "COURSE_STUDENTLIST_QUERY";//查询该门课程有哪些学生选择
    public static final String course_grade_add = "COURSE_GRADE_ADD";//登成绩

    public static final String course_query_course = "COURSE_QUERY_COURSE";//查一门课
    public static final String course_exam_add = "COURSE_EXAM_ADD";//发布考试
    public static final String course_add = "COURSE_ADD";//管理员加课


    //银行
    public static final String record_Query = "RECORD_QUERY";//查询交易明细
    public static final String phone_Pay = "PHONE_PAY";//支付手机流量包
    public static final String wifi_Pay = "WIFI_PAY";//支付校园网套餐
    public static final String tuition_Pay = "TUITION_PAY";//支付学费
    public static final String card_Invest = "CARD_INVEST";//对一卡通进行充值
    public static final String account_Query = "ACCOUNT_QUERY";//查询具体账单金额

    //场馆
    public static final String place_query_all = "PLACE_QUERY_ALL";//查询场馆列表的公共入口
    public static final String place_ap_insert = "PLACE_AP_INSERT";//新增场馆预约
    public static final String place_ap_query = "PLACE_AP_QUERY";//场馆预约记录查询（按照场馆
    public static final String stu_ap_query = "STU_AP_QUERY";//场馆预约记录查询（按照学生
    public static final String place_ap_delete = "PLACE_AP_DELETE";//场馆预约记录取消
    public static final String place_query_by_name = "PLACE_QUERY_BY_NAME";//查询特定场馆


    //医院
    public static final String doc_query_all = "DOC_QUERY_ALL";//返回所有医生（医生表
    public static final String doc_query_doc = "DOC_QUERY_DOC";//返回指定医生（医生表
    public static final String stu_advice_query_stu = "STU_ADVICE_QUERY_STU";//返回指定学生的咨询记录（咨询记录表
    public static final String stu_advice_query_doc = "STU_ADVICE_QUERY_DOC";//返回指定医生的咨询记录（咨询记录表
    public static final String stu_res_query = "STU_RES_QUERY";//返回指定学生的挂号记录（挂号记录表
    public static final String stu_advice_insert = "STU_ADVICE_INSERT";//增加一条记录（咨询记录表
    public static final String stu_res_insert = "STU_RES_INSERT";//增加一条记录（挂号记录表
    public static final String stu_advice_query_relevant = "STU_ADVICE_QUERY_RELEVANT";//查询相关问题
    public static final String doc_rank = "DOC_RANK";//更新医生的声誉（医生表
    public static final String doc_schedule_query = "DOC_SCHEDULE_QUERY";//返回一个医生的出诊安排（医生安排表
    public static final String doc_advice_ans = "DOC_ADVICE_ANS";//医生的回复-修改记录（咨询记录表
    public static final String doc_schedule_add = "DOC_SCHEDULE_ADD";//添加一个医生的出诊安排（医生安排表
    public static final String doc_schedule_del = "DOC_SCHEDULE_DEL";//删除一个医生的出诊安排（医生安排表
}