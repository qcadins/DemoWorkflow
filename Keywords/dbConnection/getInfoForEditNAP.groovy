package dbConnection

import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject

import java.sql.ResultSetMetaData

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

import groovy.sql.Sql
import internal.GlobalVariable

public class getInfoForEditNAP {

	int colmcount
	int i

	@Keyword
	public customerDataPersonal (Sql instance, String appno){
		String customerdata
		ArrayList<String> listcustdata = new ArrayList<>()
		instance.eachRow(("Select CUST_NAME, rml.REF_MASTER_NAME, FORMAT(BIRTH_DT, 'MM/dd/yyyy') , ID_NO, acp.MOTHER_MAIDEN_NAME FROM APP_CUST ac WITH(NOLOCK) JOIN APP a WITH(NOLOCK) ON ac.APP_ID = a.APP_ID JOIN APP_CUST_PERSONAL acp WITH(NOLOCK) ON acp.APP_CUST_ID = ac.APP_CUST_ID JOIN REF_MASTER_LOS rml WITH(NOLOCK) ON rml.REF_MASTER_CODE = MR_ID_TYPE_CODE WHERE APP_NO = '"+ appno +"' AND IS_CUSTOMER = 1 AND rml.REF_MASTER_TYPE_CODE = 'ID_TYPE' AND rml.IS_ACTIVE = '1'"), {  row ->

			ResultSetMetaData rsmd = row.getMetaData()
			colmcount = rsmd.getColumnCount()


			for(i = 0 ; i < colmcount ; i++){
				customerdata = (row[i])
				listcustdata.add(customerdata)
			}
		})
		return listcustdata
	}

	@Keyword
	public familyDataPersonal (Sql instance, String appno){
		String customerdata
		ArrayList<String> listcustdata = new ArrayList<>()
		instance.eachRow(("Select CUST_NAME, rml.REF_MASTER_NAME, FORMAT(BIRTH_DT, 'MM/dd/yyyy') , ID_NO, acp.MOTHER_MAIDEN_NAME FROM APP_CUST ac WITH(NOLOCK) JOIN APP a WITH(NOLOCK) ON ac.APP_ID = a.APP_ID JOIN APP_CUST_PERSONAL acp WITH(NOLOCK) ON acp.APP_CUST_ID = ac.APP_CUST_ID JOIN REF_MASTER_LOS rml WITH(NOLOCK) ON rml.REF_MASTER_CODE = MR_ID_TYPE_CODE WHERE APP_NO = '"+ appno +"' AND IS_FAMILY = 1 AND rml.REF_MASTER_TYPE_CODE = 'ID_TYPE'"), {  row ->

			ResultSetMetaData rsmd = row.getMetaData()
			colmcount = rsmd.getColumnCount()


			for(i = 0 ; i < colmcount ; i++){
				customerdata = (row[i])
				listcustdata.add(customerdata)
			}
		})
		return listcustdata
	}

	@Keyword
	public guarantorDataPersonal (Sql instance, String appno){
		String customerdata
		ArrayList<String> listcustdata = new ArrayList<>()
		instance.eachRow(("Select CUST_NAME, rml.REF_MASTER_NAME, FORMAT(BIRTH_DT, 'MM/dd/yyyy') , ID_NO, acp.MOTHER_MAIDEN_NAME FROM APP_CUST ac WITH(NOLOCK) JOIN APP a WITH(NOLOCK) ON ac.APP_ID = a.APP_ID JOIN APP_CUST_PERSONAL acp WITH(NOLOCK) ON acp.APP_CUST_ID = ac.APP_CUST_ID JOIN REF_MASTER_LOS rml WITH(NOLOCK) ON rml.REF_MASTER_CODE = MR_ID_TYPE_CODE WHERE APP_NO = '"+ appno +"' AND IS_GUARANTOR = 1 AND rml.REF_MASTER_TYPE_CODE = 'ID_TYPE'"), {  row ->

			ResultSetMetaData rsmd = row.getMetaData()
			colmcount = rsmd.getColumnCount()


			for(i = 0 ; i < colmcount ; i++){
				customerdata = (row[i])
				listcustdata.add(customerdata)
			}
		})
		return listcustdata
	}

	@Keyword
	public guarantorDataCompany (Sql instance, String appno){
		String customerdata
		ArrayList<String> listcustdata = new ArrayList<>()
		instance.eachRow(("Select CUST_NAME, TAX_ID_NO FROM APP_CUST ac WITH(NOLOCK) JOIN APP a WITH(NOLOCK) ON ac.APP_ID = a.APP_ID JOIN APP_CUST_COMPANY acp WITH(NOLOCK) ON acp.APP_CUST_ID = ac.APP_CUST_ID WHERE APP_NO = '"+ appno +"' AND IS_GUARANTOR = 1"), {  row ->

			ResultSetMetaData rsmd = row.getMetaData()
			colmcount = rsmd.getColumnCount()


			for(i = 0 ; i < colmcount ; i++){
				customerdata = (row[i])
				listcustdata.add(customerdata)
			}
		})
		return listcustdata
	}

	@Keyword
	public customerDataCompany (Sql instance, String appno){
		String customerdata
		ArrayList<String> listcustdata = new ArrayList<>()
		instance.eachRow(("Select CUST_NAME, TAX_ID_NO FROM APP_CUST ac WITH(NOLOCK) JOIN APP a WITH(NOLOCK) ON ac.APP_ID = a.APP_ID JOIN APP_CUST_COMPANY acp WITH(NOLOCK) ON acp.APP_CUST_ID = ac.APP_CUST_ID WHERE APP_NO = '"+ appno +"' AND IS_CUSTOMER = 1"), {  row ->

			ResultSetMetaData rsmd = row.getMetaData()
			colmcount = rsmd.getColumnCount()


			for(i = 0 ; i < colmcount ; i++){
				customerdata = (row[i])
				listcustdata.add(customerdata)
			}
		})
		return listcustdata
	}

	@Keyword
	public shareholderDataPersonal (Sql instance, String appno){
		String customerdata
		ArrayList<String> listcustdata = new ArrayList<>()
		instance.eachRow(("Select CUST_NAME, rml.REF_MASTER_NAME, FORMAT(BIRTH_DT, 'MM/dd/yyyy') , ID_NO, acp.MOTHER_MAIDEN_NAME FROM APP_CUST ac WITH(NOLOCK) JOIN APP a WITH(NOLOCK) ON ac.APP_ID = a.APP_ID JOIN APP_CUST_PERSONAL acp WITH(NOLOCK) ON acp.APP_CUST_ID = ac.APP_CUST_ID JOIN REF_MASTER_LOS rml WITH(NOLOCK) ON rml.REF_MASTER_CODE = MR_ID_TYPE_CODE WHERE APP_NO = '"+ appno +"' AND IS_SHAREHOLDER = 1 AND rml.REF_MASTER_TYPE_CODE = 'ID_TYPE' AND rml.IS_ACTIVE = '1'"), {  row ->

			ResultSetMetaData rsmd = row.getMetaData()
			colmcount = rsmd.getColumnCount()


			for(i = 0 ; i < colmcount ; i++){
				customerdata = (row[i])
				listcustdata.add(customerdata)
			}
		})
		return listcustdata
	}

	@Keyword
	public shareholderDataCompany (Sql instance, String appno){
		String customerdata
		ArrayList<String> listcustdata = new ArrayList<>()
		instance.eachRow(("Select CUST_NAME, TAX_ID_NO FROM APP_CUST ac WITH(NOLOCK) JOIN APP a WITH(NOLOCK) ON ac.APP_ID = a.APP_ID JOIN APP_CUST_COMPANY acp WITH(NOLOCK) ON acp.APP_CUST_ID = ac.APP_CUST_ID WHERE APP_NO = '"+ appno +"' AND IS_SHAREHOLDER = 1"), {  row ->

			ResultSetMetaData rsmd = row.getMetaData()
			colmcount = rsmd.getColumnCount()


			for(i = 0 ; i < colmcount ; i++){
				customerdata = (row[i])
				listcustdata.add(customerdata)
			}
		})
		return listcustdata
	}


	@Keyword
	public getFamilyDataforEditNAP(Sql instance, String appno){
		ArrayList<String> arrayFamilyData = new ArrayList<String>()
		String familydata
		instance.eachRow(("SELECT CUST_NAME , [CUST_TYPE] ,[RELATIONSHIP]  ,[CUST_MODEL]  FROM (SELECT CUST_NAME, mastername.Code, REF_MASTER_NAME,app_cust_id FROM (select cust_name, [Code], value,app_cust_id FROM (select cust_name, mr_cust_type_code AS [CUST_TYPE], (CASE WHEN MR_CUST_RELATIONSHIP_CODE = '' THEN 'SELF' ELSE MR_CUST_RELATIONSHIP_CODE END) as [RELATIONSHIP], MR_CUST_MODEL_CODE AS [CUST_MODEL],app_cust_id from app_cust ac with(nolock) join app a with(nolock) on ac.app_id = a.app_id where app_no ='"+appno+"' and (is_customer=1 or is_family=1)) as Orig unpivot (value for [Code] in ([CUST_TYPE],[RELATIONSHIP],[CUST_MODEL]))as unpiv) as mastername JOIN REF_MASTER_LOS rf WITH(NOLOCK) ON rf.REF_MASTER_Code = mastername.value WHERE rf.IS_ACTIVE = '1' and rf.REF_MASTER_TYPE_CODE IN('CUST_PERSONAL_RELATIONSHIP','CUST_TYPE','CUST_MODEL')) AS ref PIVOT (MAX(ref.REF_MASTER_NAME) for [Code] in ([CUST_TYPE],[RELATIONSHIP],[CUST_MODEL])) as piv order by [RELATIONSHIP],app_cust_id"), {  row ->
			ResultSetMetaData rsmd = row.getMetaData()
			colmcount = rsmd.getColumnCount()


			for(i = 0 ; i < colmcount ; i++){
				familydata = (row[i])
				arrayFamilyData.add(familydata)
			}
		})


		return arrayFamilyData
	}

	@Keyword
	public getMSDataforEditNAP (Sql instance, String appno){
		String customerdata
		ArrayList<String> listcustdata = new ArrayList<>()
		instance.eachRow(("SELECT CUST_NAME , MR_CUST_TYPE_CODE , SHARE_PRCNT , IS_ACTIVE , IS_OWNER , IS_SIGNER  FROM APP_CUST ac WITH(NOLOCK) JOIN APP a WITH(NOLOCK) ON ac.APP_ID = a.APP_ID JOIN APP_CUST_COMPANY_MGMNT_SHRHOLDER accms WITH(NOLOCK) ON accms.APP_CUST_ID = ac.APP_CUST_ID WHERE APP_NO = '"+ appno +"' AND IS_SHAREHOLDER = '1'"), {  row ->

			ResultSetMetaData rsmd = row.getMetaData()
			colmcount = rsmd.getColumnCount()


			for(i = 0 ; i < colmcount ; i++){
				customerdata = (row[i])
				listcustdata.add(customerdata)
			}
		})
		return listcustdata
	}


	@Keyword
	public getGuarantorDataforEditNAP(Sql instance, String appno){
		ArrayList<String> arrayGuarantorData = new ArrayList<String>()
		String guardata
		instance.eachRow(("SELECT CUST_NAME , MR_CUST_TYPE_CODE , MR_CUST_RELATIONSHIP_CODE  FROM APP_CUST ac WITH(NOLOCK) JOIN APP a WITH(NOLOCK) ON ac.APP_ID = a.APP_ID WHERE APP_NO = '"+appno+"' AND IS_GUARANTOR = '1' order by app_cust_id"), {  row ->
			ResultSetMetaData rsmd = row.getMetaData()
			colmcount = rsmd.getColumnCount()


			for(i = 0 ; i < colmcount ; i++){
				guardata = (row[i])
				arrayGuarantorData.add(guardata)
			}
		})


		return arrayGuarantorData
	}


	@Keyword
	public verifyMatchCopyAppYesNAP2Personal(Sql instance, String copyAppNo, String appNo){
		Boolean isMatch = true
		ArrayList<String> copyappdata = new ArrayList<String>()
		ArrayList<String> appdata = new ArrayList<String>()
		ArrayList<String> insuredBy = new ArrayList<String>()
		instance.eachRow(("select ins_asset_covered_by from app_ins_obj where app_id in (select app_id from app where app_no in('"+copyAppNo+"','"+appNo+"'))"), { def row ->
			insuredBy.add(row[0])
		})

		if(insuredBy.get(0).equalsIgnoreCase(insuredBy.get(1))){
			if(insuredBy.get(0)=="CU"){

				copyappdata = (new dbConnection.CustomerDataVerif()).NAP2InsuranceCustStoreDB(instance, copyAppNo)
				appdata = (new dbConnection.CustomerDataVerif()).NAP2InsuranceCustStoreDB(instance, appNo)
				for(int i = 0;i<copyappdata.size();i++){
					if(WebUI.verifyMatch(copyappdata[i],appdata[i],false,FailureHandling.OPTIONAL)==false){
						isMatch = false
						break
					}
				}
			}
			else if(insuredBy.get(0)=="CUCO"){
				copyappdata = (new dbConnection.CustomerDataVerif()).NAP2InsuranceCustMFStoreDB(instance, copyAppNo)
				appdata = (new dbConnection.CustomerDataVerif()).NAP2InsuranceCustMFStoreDB(instance, appNo)
				for(int i = 0;i<copyappdata.size();i++){
					if(WebUI.verifyMatch(copyappdata[i],appdata[i],false,FailureHandling.OPTIONAL)==false){
						isMatch = false
						break
					}
				}


				copyappdata = (new dbConnection.CustomerDataVerif()).NAP2InsuranceMFStoreDB(instance, copyAppNo)
				appdata = (new dbConnection.CustomerDataVerif()).NAP2InsuranceMFStoreDB(instance, appNo)
				for(int i = 0;i<copyappdata.size();i++){
					if(WebUI.verifyMatch(copyappdata[i],appdata[i],false,FailureHandling.OPTIONAL)==false){
						isMatch = false
						break
					}
				}

				copyappdata = (new dbConnection.CustomerDataVerif()).checkTotalInsurance(instance, copyAppNo)
				appdata = (new dbConnection.CustomerDataVerif()).checkTotalInsurance(instance, appNo)
				for(int i = 0;i<copyappdata.size();i++){
					if(WebUI.verifyMatch(copyappdata[i],appdata[i],false,FailureHandling.OPTIONAL)==false){
						isMatch = false
						break
					}
				}
			}
			else if(insuredBy.get(0)=="CO"){
				copyappdata = (new dbConnection.CustomerDataVerif()).NAP2InsuranceMFStoreDB(instance, copyAppNo)
				appdata = (new dbConnection.CustomerDataVerif()).NAP2InsuranceMFStoreDB(instance, appNo)
				for(int i = 0;i<copyappdata.size();i++){
					if(WebUI.verifyMatch(copyappdata[i],appdata[i],false,FailureHandling.OPTIONAL)==false){
						isMatch = false
						break
					}
				}

				copyappdata = (new dbConnection.CustomerDataVerif()).checkTotalInsurance(instance, copyAppNo)
				appdata = (new dbConnection.CustomerDataVerif()).checkTotalInsurance(instance, appNo)
				for(int i = 0;i<copyappdata.size();i++){
					if(WebUI.verifyMatch(copyappdata[i],appdata[i],false,FailureHandling.OPTIONAL)==false){
						isMatch = false
						break
					}
				}
			}
		}
		else{
			isMatch=false
		}

		copyappdata = (new dbConnection.CustomerDataVerif()).NAP2LifeInsuranceStoreDB(instance, copyAppNo)
		appdata = (new dbConnection.CustomerDataVerif()).NAP2LifeInsuranceStoreDB(instance, appNo)
		for(int i = 0;i<copyappdata.size();i++){
			if(WebUI.verifyMatch(copyappdata[i],appdata[i],false,FailureHandling.OPTIONAL)==false){
				isMatch = false
				break
			}
		}

		copyappdata = (new dbConnection.CustomerDataVerif()).NAP2FinancialStoreDB(instance, copyAppNo)
		appdata = (new dbConnection.CustomerDataVerif()).NAP2FinancialStoreDB(instance, appNo)
		for(int i = 0;i<copyappdata.size();i++){
			if(WebUI.verifyMatch(copyappdata[i],appdata[i],false,FailureHandling.OPTIONAL)==false){
				isMatch = false
				break
			}
		}

		copyappdata = (new dbConnection.CustomerDataVerif()).NAP2SubsidyStoreDB(instance, copyAppNo)
		appdata = (new dbConnection.CustomerDataVerif()).NAP2SubsidyStoreDB(instance, appNo)
		for(int i = 0;i<copyappdata.size();i++){
			if(WebUI.verifyMatch(copyappdata[i],appdata[i],false,FailureHandling.OPTIONAL)==false){
				isMatch = false
				break
			}
		}

		return isMatch
	}

	@Keyword
	public verifyMatchCopyAppYesNAP2Company(Sql instance, String copyAppNo, String appNo){

		Boolean isMatch = true
		ArrayList<String> copyappdata = new ArrayList<String>()
		ArrayList<String> appdata = new ArrayList<String>()
		ArrayList<String> insuredBy = new ArrayList<String>()
		instance.eachRow(("select ins_asset_covered_by from app_ins_obj where app_id in (select app_id from app where app_no in('"+copyAppNo+"','"+appNo+"'))"), { def row ->
			insuredBy.add(row[0])
		})

		if(insuredBy.get(0).equalsIgnoreCase(insuredBy.get(1))){
			if(insuredBy.get(0)=="CU"){

				copyappdata = (new dbConnection.CustomerDataVerif()).NAP2InsuranceCustStoreDB(instance, copyAppNo)
				appdata = (new dbConnection.CustomerDataVerif()).NAP2InsuranceCustStoreDB(instance, appNo)
				for(int i = 0;i<copyappdata.size();i++){
					if(WebUI.verifyMatch(copyappdata[i],appdata[i],false,FailureHandling.OPTIONAL)==false){
						isMatch = false
						break
					}
				}
			}
			else if(insuredBy.get(0)=="CUCO"){
				copyappdata = (new dbConnection.CustomerDataVerif()).NAP2InsuranceCustMFStoreDB(instance, copyAppNo)
				appdata = (new dbConnection.CustomerDataVerif()).NAP2InsuranceCustMFStoreDB(instance, appNo)
				for(int i = 0;i<copyappdata.size();i++){
					if(WebUI.verifyMatch(copyappdata[i],appdata[i],false,FailureHandling.OPTIONAL)==false){
						isMatch = false
						break
					}
				}


				copyappdata = (new dbConnection.CustomerDataVerif()).NAP2InsuranceMFStoreDB(instance, copyAppNo)
				appdata = (new dbConnection.CustomerDataVerif()).NAP2InsuranceMFStoreDB(instance, appNo)
				for(int i = 0;i<copyappdata.size();i++){
					if(WebUI.verifyMatch(copyappdata[i],appdata[i],false,FailureHandling.OPTIONAL)==false){
						isMatch = false
						break
					}
				}

				copyappdata = (new dbConnection.CustomerDataVerif()).checkTotalInsurance(instance, copyAppNo)
				appdata = (new dbConnection.CustomerDataVerif()).checkTotalInsurance(instance, appNo)
				for(int i = 0;i<copyappdata.size();i++){
					if(WebUI.verifyMatch(copyappdata[i],appdata[i],false,FailureHandling.OPTIONAL)==false){
						isMatch = false
						break
					}
				}
			}
			else if(insuredBy.get(0)=="CO"){
				copyappdata = (new dbConnection.CustomerDataVerif()).NAP2InsuranceMFStoreDB(instance, copyAppNo)
				appdata = (new dbConnection.CustomerDataVerif()).NAP2InsuranceMFStoreDB(instance, appNo)
				for(int i = 0;i<copyappdata.size();i++){
					if(WebUI.verifyMatch(copyappdata[i],appdata[i],false,FailureHandling.OPTIONAL)==false){
						isMatch = false
						break
					}
				}

				copyappdata = (new dbConnection.CustomerDataVerif()).checkTotalInsurance(instance, copyAppNo)
				appdata = (new dbConnection.CustomerDataVerif()).checkTotalInsurance(instance, appNo)
				for(int i = 0;i<copyappdata.size();i++){
					if(WebUI.verifyMatch(copyappdata[i],appdata[i],false,FailureHandling.OPTIONAL)==false){
						isMatch = false
						break
					}
				}
			}
		}
		else{
			isMatch=false
		}


		copyappdata = (new dbConnection.CustomerDataVerif()).NAP2FinancialStoreDB(instance, copyAppNo)
		appdata = (new dbConnection.CustomerDataVerif()).NAP2FinancialStoreDB(instance, appNo)
		for(int i = 0;i<copyappdata.size();i++){
			if(WebUI.verifyMatch(copyappdata[i],appdata[i],false,FailureHandling.OPTIONAL)==false){
				isMatch = false
				break
			}
		}

		copyappdata = (new dbConnection.CustomerDataVerif()).NAP2SubsidyStoreDB(instance, copyAppNo)
		appdata = (new dbConnection.CustomerDataVerif()).NAP2SubsidyStoreDB(instance, appNo)
		for(int i = 0;i<copyappdata.size();i++){
			if(WebUI.verifyMatch(copyappdata[i],appdata[i],false,FailureHandling.OPTIONAL)==false){
				isMatch = false
				break
			}
		}

		return isMatch
	}
}



