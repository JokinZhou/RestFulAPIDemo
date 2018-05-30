package comm;

import java.util.ArrayList;
import java.util.List;

public class CONST {

	// 存储常量，例如sql等信息
	public static String sql = "select *  from jz_business.jz_case where id=148";
	
/*	//存储excel对应的常量名
	public static final String TEST_NAME = "testName";
	public static final String URL = "url";
	
	public static final String EXCEPTED_RESULT = "expectResult";
	public static final String SQL_CHECK = "sqlCheck";
	public static final String SQL = "sql";*/
	
	
/*	//定义异常返回或返回文件等时的验证
	public static final String IMAGE = "IMAGE";
	public static final String JSON_PARAM = "jsonParam";
	
	//存储providerData的名称
	public static final String PROVIDER_DATA = "providerData";*/
	
	//api的种类（是否被废弃，暂时不用等）
	public static final String API_ABANDONED = "被废弃";
	public static final String API_TEMP_NOUSE = "暂时不用";
	
	
	
	public static List<TestDataInfo> testDataInfos = new ArrayList<TestDataInfo>();
	public static String TEMP_FILE = "test.txt";
	public static long startTime;
	public static long endTime;
	
/*	public static ReportInfo reportInfo = new ReportInfo();;
	public static List<DetailBean> list = new ArrayList<DetailBean>();;
	public static SummaryBean data = new SummaryBean();;
*/
	// 以下常量存储Excel列名
	public static final String TEST_NAME = "testName";
	public static final String EXCEPTED_RESULT = "expectResult";
	public static final String SQL_CHECK = "sqlCheck";
	public static final String SQL_UPDATE = "sqlUpdate";
	public static final String SQL_INSERT = "sqlInsert";
	public static final String SQL_DELETE = "sqlDelete";
	public static final String SQL = "sql";
	public static final String URL = "url";
	public static final String SCHEMA = "schema";
	public static final String JSON_PARAM = "jsonParam";
	public static final String SKIP_RESULT = "SKIP";
	public static final String PRD_EXECUTE = "prdExecute";
	public static final String RUN_OR_NOT = "runOrNot";
	public static final String PRIORITY_LEVEL = "priorityLevel";
	public static final String BASE_URL = "baseUrl";
	public static final String METHOD_AT = "methodAT";
	public static final String CLIENT_TYPE_AT = "clientTypeAT";
	public static final String USER_NAME_AT = "userNameAT";
	// public static final String TESTS_ENV_AT = "testsEnvAT";
	public static final String PARAMETER_TYPE_AT = "parameterTypeAT";
	public static String RESPONSE_BODY = "responseBody";

	// 定义异常返回或返回文件等时的验证
	public static final String IMAGE = "IMAGE";

	// 存储providerData的名称
	public static final String PROVIDER_DATA = "providerData";
}
