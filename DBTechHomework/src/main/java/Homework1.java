import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;
import java.sql.Statement;


public class Homework1 {
	/**
	 * ex1.1
	 */
	public void createTables() {
		/*建立类别的的表格*/
		
		ArrayList<String> list=new ArrayList<String>();
		
		list.add("DROP TABLE IF EXISTS `category`");
		list.add("DROP TABLE IF EXISTS `book`");
		list.add("DROP TABLE IF EXISTS `book_category`");
		list.add("DROP TABLE IF EXISTS `borrow`");
		
		String create_category="create table `category`(`c_id` int(10) not null,"
				+ "`c_name` varchar(255)not null,`c_path` varchar(255) not null,primary key(`c_id`))"
				+ "ENGINE=InnoDB DEFAULT CHARSET=utf8";
		list.add(create_category);
		
		String create_book="create table book(`book_id` int(10) not null,"
				+ "`b_name` varchar(255) not null,"
				+ "`author` varchar(255) not null,publish_info varchar(255) not null,"
				+ "primary key(book_id))ENGINE=InnoDB DEFAULT CHARSET=utf8";
		list.add(create_book);
		
		String create_book_c= "create table book_category(`bc_id` int(10) not null,"
				+ "`book_id` int(10) not null,`c_id` varchar(10) not null,primary key(bc_id))"
				+ "ENGINE=InnoDB DEFAULT CHARSET=utf8";
		list.add(create_book_c);
		
		String create_borrow="create table borrow(`borrow_id` int(10) not null,student_id varchar(10) not null,book_id int(10) not null,"
				+ "`start_date` varchar(255) not null,`return_date` varchar(255) Default NULL,primary key(borrow_id))"
				+ "ENGINE=InnoDB DEFAULT CHARSET=utf8";
		list.add(create_borrow);
		
		String sql=create_book/*+create_book_c+create_borrow*/;
		
		try {
			Connection conn=getMysqlConnection();
			Statement stmt = conn.createStatement();
			
			for(int i=0;i<list.size();i++){
				stmt.executeUpdate(list.get(i));
			}
			stmt.close();
		     conn.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("数据库建表出错！原因："+e.toString());
		}
		 
//		System.out.println("建表成功！");
	}

	/**
	 * ex1.2
	 */
	public void initCategoryData() {
		String path="ex1/category.txt"; 				//读取文件的路径
        String encoding="utf-8";		//以utf-8的编码方式读取文件
        Connection conn;
	
		
        try{ 
        	InputStreamReader read = new InputStreamReader(
                    new FileInputStream(new File(path)),encoding);
        	BufferedReader br = new BufferedReader(read);
        	String line=null;
        	int c_id_f=0;
        	
        	int id=0;
        	int c_id_c=0;
        	String c_path="";
        	String tab="    ";
        	
        	conn = getMysqlConnection();
			Statement stmt = conn.createStatement();
        	/*BEGIN;
        	INSERT INTO `Customer` VALUES ('1', 'lucy', '13888899999');
        	COMMIT;*/
        	String sql;
        	while((line=(String)br.readLine())!=null){
        		if(line.charAt(0)==9){
        			c_id_c++;
        			c_path=c_id_f+"."+c_id_c;
        			//System.out.println(tab+c_path+" "+line.trim());
        		}
        		else{
        			
        			
        			c_id_f++;
        			c_id_c=0;
        			c_path=(c_id_f)+"";
        			//System.out.println(c_path+" "+line);
        		}
        		
        		line=line.trim();
        		if(line.contains(":")){
        			line=line.substring(0, line.length()-1);
        		}
        		
        		/*INSERT INTO `category` (`c_id`, `c_name`, `c_path`) VALUES ('1', 'k', '1')*/
        		
        		sql="INSERT INTO `category` (`c_id`, `c_name`, `c_path`) VALUES ('"+id+"','"+line+"','"+c_path+"')";
        		
//        		System.out.println(sql);
        		stmt.executeUpdate(sql);
        		id++;
        	}//end while
       
        	stmt.close();
        	conn.close();
//        	System.out.println("-----------------end--------------------");
        }catch(Exception e){
        	e.printStackTrace();
        	System.out.println("写入分类数据，出错原因："+e.toString());
        }
        	
	}

	/**
	 * ex1.3
	 */
	public void insertNewCategoryData() {
		int maxC_id=getMaxCategoryId();
		int id=maxC_id+1;
		String path=getCurrentCategoryByName("自动化技术、计算机技术");
		String list[]={"自动化基础理论","自动化技术及设备","计算技术、计算机技术",
				"射流技术（流控技术）","遥感技术","远程技术"};
		String sqlStr=null;
		String c_path="";
		try {
			
			Connection conn = getMysqlConnection();
			Statement stmt = conn.createStatement();
			
			for(int i=0;i<list.length;i++){
				c_path=path+"."+(i+1);
				sqlStr="INSERT INTO `category` (`c_id`, `c_name`, `c_path`) VALUES ('"+id+"','"+list[i]+"','"+c_path+"')";
//        		System.out.println(i+"  "+ sqlStr);	
        		stmt.executeUpdate(sqlStr);
				id++;
			}
			
		} catch (Exception e) {
			// TODO: handle exception
		}

	}
	
	
	private String getCurrentCategoryByName(String name){
		String result=null;
		try {
			Connection conn=getMysqlConnection();
			Statement stmt = conn.createStatement();
			String sqlStr="select c_path from category where `c_name`='"+name+"'";
			ResultSet  rs=stmt.executeQuery(sqlStr);
			while(rs.next()){
				result=rs.getString("c_path");
				break;				//最大的获取一条就可以了
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("获取最大的分类的id出错："+e.toString());
		}
		
		return result;
	}
	
	
	private int  getMaxCategoryId(){
		int id=0;
		try {
			Connection conn=getMysqlConnection();
			Statement stmt = conn.createStatement();
			String sqlStr="select max(c_id) as max_ from category";
			ResultSet  rs=stmt.executeQuery(sqlStr);
			while(rs.next()){
				id=rs.getInt("max_");
//				System.out.println("sefsafsa:"+id);
				break;				//最大的获取一条就可以了
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("获取最大的分类的id出错："+e.toString());
		}
		
		return id;
	}
	

	private HashMap<String,Integer> getC_id(){
		 HashMap<String,Integer> map=new HashMap<String,Integer>();
		 
		 String sqlStr="select c_id,c_name from category";
		 
		 try {
				Connection conn=getMysqlConnection();
				Statement stmt = conn.createStatement();
				ResultSet  rs=stmt.executeQuery(sqlStr);
				while(rs.next()){
					map.put(rs.getString("c_name"), rs.getInt("c_id"));
				}
				
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("获取最大的分类的id出错："+e.toString());
			}
			
		 return map;
		 
	}
	
	/**
	 * ex1.4
	 */
	private void initBookAndBorrowInfoData() {
		/************************************/
		inintBookInfo();
		initBorrowInfoData();
		/**************************************/
	}

	private void inintBookInfo(){
		String path="ex1/books.txt"; 				//读取文件的路径
        String encoding="utf-8";		//以utf-8的编码方式读取文件
        Connection conn;
        
        HashMap<String,Integer> map=getC_id();
		 String line=null;
        try{ 
        	InputStreamReader read = new InputStreamReader(
                    new FileInputStream(new File(path)),encoding);
        	BufferedReader br = new BufferedReader(read);
    	
        	conn = getMysqlConnection();
			Statement stmt = conn.createStatement();
        	int count=0;	//用于标记第一行不读入
        	int b_c_id=0;		//书，分类间的关系表的id
        	String sql1,sql2;
        	while((line=(String)br.readLine())!=null){
        		
        		if(count==0){
        			count=1;
        			continue;
        		}
        		line=line.replace("'","''");
        		String str[]=line.split("	");
        		//System.out.println(count+"---"+str[4]);
        		
        		sql1="INSERT INTO `book` (`book_id`, `b_name`, `author`, `publish_info`) VALUES ('"+str[0]+"', '"+str[1]+"', '"+str[2]+"', '"+str[3]+"')";
        		sql2="INSERT INTO `book_category` (`bc_id`, `book_id`, `c_id`) VALUES ('"+b_c_id+"', '"+str[0]+"', '"+map.get(str[4].trim())+"')";
//        		System.out.println(sql);
        		stmt.executeUpdate(sql1);
        		stmt.executeUpdate(sql2);
        		b_c_id++;
        	}//end while
       
        	stmt.close();
        	conn.close();
//        	System.out.println("-----------------end--------------------");
        }catch(Exception e){
        	e.printStackTrace();
        	System.out.println();
        	System.out.println("写入初始化图书数据，出错原因："+e.toString()+"line="+line);
        }
	}
	
	
	private void initBorrowInfoData(){
		/**********************************/
		String path="ex1/borrow.txt"; 				//读取文件的路径
        String encoding="utf-8";		//以utf-8的编码方式读取文件
        Connection conn;
		 String line=null;
        try{ 
        	InputStreamReader read = new InputStreamReader(
                    new FileInputStream(new File(path)),encoding);
        	BufferedReader br = new BufferedReader(read);
    	
        	conn = getMysqlConnection();
			Statement stmt = conn.createStatement();
        	int count=0;	//用于标记第一行不读入
        	String sql1;
        	int br_id=0;
        	while((line=(String)br.readLine())!=null){
        		
        		if(count==0){
        			count=1;
        			continue;
        		}
        		line=line.replace("'","''");
        		String str[]=line.split("	");
        		//System.out.println(count+"---"+str[4]);
        		
        		sql1="INSERT INTO `borrow` (`borrow_id`, `student_id`, `book_id`, `start_date`, `return_date`) VALUES ('"+br_id+"', '"+str[0]+"', '"+str[1]+"', '"+str[2]+"', '"+str[3]+"')";
//        		System.out.println(sql);
        		stmt.executeUpdate(sql1);
        		br_id++;
        	}//end while
       
        	stmt.close();
        	conn.close();
//        	System.out.println("-----------------end--------------------");
        }catch(Exception e){
        	e.printStackTrace();
        	System.out.println();
        	System.out.println("写入借阅数据，出错原因："+e.toString()+"line="+line);
        }
		/*********************/
		
		
	}
	
	/**
	 * ex1.5
	 */
	public void getBorrowData() {

	}

	/**
	 * get mysql connection
	 * 
	 * @return
	 * @throws Exception
	 */
	public Connection getMysqlConnection() throws Exception {
		// get items from resources/config.properties
		Properties properties = new Properties();
		String path = Thread.currentThread().getContextClassLoader()
				.getResource("config.properties").getPath();
//		properties.load(new FileInputStream(new File(path)));
		properties.load(new FileInputStream(new File("src/main/resources/config.properties")));
		String mysqlIP = properties.getProperty("mysql.server.ip");
		String mysqlPort = properties.getProperty("mysql.server.port");
		String dbName = properties.getProperty("mysql.db.ex1");
		String loginName = properties.getProperty("mysql.login.name");
		String password = properties.getProperty("mysql.login.password");
		String url = "jdbc:mysql://" + mysqlIP + ":" + mysqlPort + "/" + dbName
				+ "?" + "user=" + loginName + "&password=" + password
				+ "&useUnicode=true&varcharacterEncoding=UTF8";

		// load class driver
		Class.forName("com.mysql.jdbc.Driver");

		// return connection
		Connection connection = DriverManager.getConnection(url);
		//System.out.println("数据库连接成功！");
		return connection;
	}
	
	public static void main(String[] args) {
		long x=System.currentTimeMillis();
		System.out.println("开始运行:"+x);
		Homework1 test1 = new Homework1();
		//ex1.1
		//test1.createTables();
		//ex1.2
		//test1.createTables();
		//test1.initCategoryData();
		//test1.insertNewCategoryData();
		test1.initBookAndBorrowInfoData();
		long y=System.currentTimeMillis();
		
		double time=(((double)(y-x)));		//用于计算消耗的时间
		System.out.println("结束运行："+y+"\n所花时间，合计："+time+"ms");
	}
	
}
