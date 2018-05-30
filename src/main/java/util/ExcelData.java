package util;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import comm.CONST;

/**
 *	@author	qin.hua   
 *
 *	@date	2017/6/1
 * 
 **/
public class ExcelData {
	public Workbook workbook;
	public Sheet sheet;
	public Cell cell;
	int rows;
	int columns;
	public String fileName;
	public String caseName;
	public ArrayList<String> arrkey = new ArrayList<String>();
	String sourceFile;

	/**
	 * @param fileName   excel文件名
	 * @param caseName   sheet名
	 */
	public ExcelData(String fileName, String caseName) {
		super();
		this.fileName = fileName;
		this.caseName = caseName;
	}

    
    public  List<Map<String, String>> readExcelWithTitle() throws Exception{
    	String filepath = getPath();
//        String fileType = filepath.substring(filepath.lastIndexOf(".") + 1, filepath.length());
        
        InputStream is = null;
        List<Map<String, String>> result = new ArrayList<Map<String, String>>();
        
        try{
        	is = new FileInputStream(filepath);
            XSSFWorkbook xssfWorkbook = new XSSFWorkbook(is);		//获取整个excel
            
            XSSFSheet xssfSheet = xssfWorkbook.getSheet(caseName);
            if(xssfSheet == null){
            	return null;
            }
            
            Map<Integer, String> title = new HashMap<Integer, String>();
            
            //处理sheet中的页
            for(int rowNum = 0; rowNum <= xssfSheet.getLastRowNum(); rowNum++){
            	XSSFRow xssfRow = xssfSheet.getRow(rowNum);
            	int minColIx = xssfRow.getFirstCellNum();
            	int maxColIx = xssfRow.getLastCellNum();
//            	List<String> rowList = new ArrayList<String>();
            	Map<String, String> rowList = new HashMap<String, String>();
            	//遍历该行，获取处理每个cell元素
            	for(int colIx = minColIx; colIx < maxColIx; colIx++){
            		XSSFCell cell = xssfRow.getCell(colIx);
            		if(cell == null || cell.toString().equals("")){
            			continue;
            		}
            		int cellType = cell.getCellType();
                	if (rowNum == 0){			//获取标题，预定标题为第一行
                		title.put(colIx, cell.toString());
                	}
                	else{
                		String cellValue = cell.toString();
                		if(cellType == Cell.CELL_TYPE_NUMERIC){		//如果是数字类型，则转换成String类型
                			cell.setCellType(Cell.CELL_TYPE_STRING);
                			cellValue = String.valueOf(cell.getRichStringCellValue().getString());
                		}
                		if(cellValue.equals("\"\"")){
                			rowList.put(title.get(colIx), "");	
                		}
                		else{
                			rowList.put(title.get(colIx), cellValue);	
                		}
                	}
            	}
            	if(rowList.size() != 0){
            		if(!rowList.containsKey(CONST.EXCEPTED_RESULT)){
            			rowList.put(CONST.EXCEPTED_RESULT, "");
//            			Assert.assertTrue(false, "Excel表[" + this.fileName + "]中的预期结果列不能为空\t");
            		}
            		/*else{
            			Assert.assertTrue(judgeJsonFormat(rowList.get(CONST.EXCEPTED_RESULT).toString()), 
            							"预期结果["+ rowList.get(CONST.EXCEPTED_RESULT).toString() +"]必须是json格式\t");
            		}*/
            		result.add(rowList);	
            	}
            }
        }
        catch(Exception e){
        	e.printStackTrace();
        }
        finally{
        	if(is != null){
        		is.close();	
        	}
        }

        return result;
    }
    
  

    /**
     * 获得excel文件的路径
     * @return
     * @throws IOException
     */
    public String getPath() throws IOException {
        File directory = new File(".");
        sourceFile = directory.getCanonicalPath() + "/src/test/resources/testdata/"
                + fileName;
        return sourceFile;
    }
    
/*	private boolean judgeJsonFormat(String jsonStr){
		try{
			JSONObject.fromObject(jsonStr);
		}
		catch(Exception e){
			return false;
		}
		return true;
	}*/

}
