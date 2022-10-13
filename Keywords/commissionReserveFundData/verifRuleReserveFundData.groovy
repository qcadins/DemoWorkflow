package commissionReserveFundData

import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.checkpoint.Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testcase.TestCase
import com.kms.katalon.core.testdata.TestData
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import groovy.sql.Sql as Sql
import internal.GlobalVariable

public class verifRuleReserveFundData {

	@Keyword
	public checkLOBCode(Sql instanceLOS, String appNo){
		String lobCode
		instanceLOS.eachRow(("select BIZ_TEMPLATE_CODE from app WITH(NOLOCK) WHERE APP_NO='"+appNo+"'"), { def row ->
			lobCode = row[0]
		})
		return lobCode
	}

	@Keyword
	public checkReserveFundBasedOnRule(String lobCode){
		HashMap<String,ArrayList> result = new HashMap<>()
		ArrayList<String> allocationFrom = new ArrayList<>()
		ArrayList<String> allocationAmt = new ArrayList<>()
		ArrayList<String> allocationBhv = new ArrayList<>()
		String userDir = System.getProperty('user.dir')
		String filePath = userDir+GlobalVariable.RSVRulePath
		def ruleRSV = findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/CommissionReservedFund/ReservedFundRule')
		Integer lobcodeRow = -1
		lobcodeRow = (new excelGetRow.getRow()).getExcelRow(filePath, 'ReservedFund', lobCode)+1
		int match = 0
		for(int i=lobcodeRow;i<=ruleRSV.getRowNumbers();i++){
			if(ruleRSV.getValue(1,i)!=lobCode&&ruleRSV.getValue(1,i)!=""){
				match=0
			}
			if(ruleRSV.getValue(1,i)==lobCode||match==1){
				if(match==0){
					match=1
				}
				allocationFrom.add(ruleRSV.getValue(2,i))
				allocationAmt.add(ruleRSV.getValue(4,i))
				allocationBhv.add(ruleRSV.getValue(5,i))
			}
			if(ruleRSV.getValue(1,i)==""&&ruleRSV.getValue(2,i)==""){
				break
			}
		}
		result.put("From",allocationFrom)
		result.put("Amt",allocationAmt)
		result.put("Bhv",allocationBhv)
		return result
	}
}
