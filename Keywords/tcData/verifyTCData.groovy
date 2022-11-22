package tcData

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

public class verifyTCData {

	@Keyword
	public verifyTCList(Sql instanceLOS, Sql instanceFOU, String MrCustModelName, String appNo){
		HashMap<String, ArrayList> result = new HashMap<>()
		ArrayList<String> TCCode = new ArrayList<>()
		ArrayList<String> TCMandatory = new ArrayList<>()
		ArrayList<String> TCPriorTo = new ArrayList<>()
		ArrayList<String> TCIsWaivable = new ArrayList<>()
		String appId, lobCode, MrCustModelCode, nationalityCode, maritalStatCode, mrCustTypeCode

		instanceLOS.eachRow(("select app_id from app where app_no = '"+appNo+"'"), { def row ->
			appId = row[0]
		})

		instanceLOS.eachRow(("select lob_code from app where app_id = '"+appId+"'"), { def row ->
			lobCode = row[0]
		})

		instanceLOS.eachRow(("select ref_master_code from ref_master_los where ref_master_name = '"+MrCustModelName+"' and ref_master_type_code = 'cust_model'"), { def row ->
			MrCustModelCode = row[0]
		})

		instanceLOS.eachRow(("select mr_nationality_code, mr_marital_stat_code from app_cust_personal a join app_cust b on a.APP_CUST_ID=b.APP_CUST_ID where app_id = '"+appId+"' and is_customer=1"), { def row ->
			nationalityCode = row[0]
			maritalStatCode = row[1]
		})

		instanceLOS.eachRow(("select mr_cust_type_code from app_cust where app_id = '"+appId+"' and is_customer = 1"), { def row ->
			mrCustTypeCode = row[0]
		})

		String userDir = System.getProperty('user.dir')
		String filePath = userDir+GlobalVariable.TCRulePath

		Integer lobCodeRow = -1
		lobCodeRow = (new customizeKeyword.getRow()).getExcelRow(filePath, 'TC', lobCode)+1

		def ruleTC = findTestData('DownloadRule/TCRule')

		int matchLOB = 0, matchCustType = 0, matchCustModel = 0, matchNationalityCode = 0, matchMaritalStat = 0

		for(int i = lobCodeRow;i<=ruleTC.getRowNumbers();i++){
			if(ruleTC.getValue(1,i)!=lobCode && ruleTC.getValue(1,i)!="" && ruleTC.getValue(1,i)!="-"){
				matchLOB=0
			}
			if(ruleTC.getValue(1,i)==lobCode || (matchLOB==1 && ruleTC.getValue(1,i)=="") || ruleTC.getValue(1,i)=="-"){
				if(matchLOB==0){
					matchLOB=1
				}
				if(ruleTC.getValue(2,i)==mrCustTypeCode || ruleTC.getValue(2,i)=="-"){
					matchCustType = 1
				}
				else if(ruleTC.getValue(2,i)!=mrCustTypeCode && ruleTC.getValue(2,i)!="" && ruleTC.getValue(2,i)!="-"){
					matchCustType = 0
				}
				if(ruleTC.getValue(3,i)==MrCustModelCode || ruleTC.getValue(3,i)=="-"){
					matchCustModel = 1
				}
				else if(ruleTC.getValue(3,i)!=MrCustModelCode && ruleTC.getValue(3,i)!="" && ruleTC.getValue(3,i)!="-"){
					matchCustModel = 0
				}
				if(ruleTC.getValue(4,i)==nationalityCode || ruleTC.getValue(4,i)=="-"){
					matchNationalityCode = 1
				}
				else if(ruleTC.getValue(4,i)!=nationalityCode && ruleTC.getValue(4,i)!="" && ruleTC.getValue(4,i)!="-"){
					matchNationalityCode = 0
				}
				if(ruleTC.getValue(5,i)==maritalStatCode || ruleTC.getValue(5,i)=="-"){
					matchMaritalStat = 1
				}
				else if(ruleTC.getValue(5,i)!=maritalStatCode && ruleTC.getValue(5,i)!="" && ruleTC.getValue(5,i)!="-"){
					matchMaritalStat = 0
				}
				if(((ruleTC.getValue(2,i)=="" && matchCustType==1) || matchCustType==1) && ((ruleTC.getValue(3,i)=="" && matchCustModel==1) || matchCustModel==1)
				&& ((ruleTC.getValue(4,i)=="" && matchNationalityCode==1) || matchNationalityCode==1) && ((ruleTC.getValue(5,i)=="" && matchMaritalStat==1) || matchMaritalStat==1) ){
					TCCode.add(ruleTC.getValue(6,i))
					TCMandatory.add(ruleTC.getValue(7,i))
					TCPriorTo.add(ruleTC.getValue(8,i))
					TCIsWaivable.add(ruleTC.getValue(10,i))
				}
			}
			else if((ruleTC.getValue(1,i)=="" && ruleTC.getValue(2,i)=="" && ruleTC.getValue(3,i)=="" && ruleTC.getValue(4,i)=="" && ruleTC.getValue(5,i)=="" && ruleTC.getValue(6,i)=="")
			|| matchLOB==0){
				break
			}
		}
		result.put("TCCode",TCCode)
		result.put("TCMdt",TCMandatory)
		result.put("TCPrior",TCPriorTo)
		result.put("TCWaive",TCIsWaivable)
		return result
	}

	@Keyword
	public checkTCCode(Sql instanceFOU, String docName){
		String tcCode
		instanceFOU.eachRow(("SELECT tc_code FROM REF_TC where tc_name = '"+docName+"'"), { def row ->
			tcCode = row[0]
		})
		return tcCode
	}
}
