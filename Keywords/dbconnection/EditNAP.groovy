package dbconnection

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

import groovy.sql.Sql
import internal.GlobalVariable

public class EditNAP {

	@Keyword
	public CustomerDataPersonal (Sql instance, String appno){
		String customerdata
		ArrayList<String> listcustdata = new ArrayList<>()
		instance.eachRow(("Select CUST_NAME, rml.REF_MASTER_NAME, FORMAT(BIRTH_DT, 'MM/dd/yyyy') , ID_NO, acp.MOTHER_MAIDEN_NAME FROM APP_CUST ac WITH(NOLOCK) JOIN APP a WITH(NOLOCK) ON ac.APP_ID = a.APP_ID JOIN APP_CUST_PERSONAL acp WITH(NOLOCK) ON acp.APP_CUST_ID = ac.APP_CUST_ID JOIN REF_MASTER_LOS rml WITH(NOLOCK) ON rml.REF_MASTER_CODE = MR_ID_TYPE_CODE WHERE APP_NO = '"+ appno +"' AND IS_CUSTOMER = 1 AND rml.REF_MASTER_TYPE_CODE = 'ID_TYPE' AND rml.IS_ACTIVE = '1'"), {  row ->

			customerdata = (row[0])
			listcustdata.add(customerdata)
			customerdata = (row[1])
			listcustdata.add(customerdata)
			customerdata = (row[2])
			listcustdata.add(customerdata)
			customerdata = (row[3])
			listcustdata.add(customerdata)
			customerdata = (row[4])
			listcustdata.add(customerdata)
		})
		return listcustdata
	}

	@Keyword
	public FamilyDataPersonal (Sql instance, String appno){
		String customerdata
		ArrayList<String> listcustdata = new ArrayList<>()
		instance.eachRow(("Select CUST_NAME, rml.REF_MASTER_NAME, FORMAT(BIRTH_DT, 'MM/dd/yyyy') , ID_NO, acp.MOTHER_MAIDEN_NAME FROM APP_CUST ac WITH(NOLOCK) JOIN APP a WITH(NOLOCK) ON ac.APP_ID = a.APP_ID JOIN APP_CUST_PERSONAL acp WITH(NOLOCK) ON acp.APP_CUST_ID = ac.APP_CUST_ID JOIN REF_MASTER_LOS rml WITH(NOLOCK) ON rml.REF_MASTER_CODE = MR_ID_TYPE_CODE WHERE APP_NO = '"+ appno +"' AND IS_FAMILY = 1 AND rml.REF_MASTER_TYPE_CODE = 'ID_TYPE'"), {  row ->

			customerdata = (row[0])
			listcustdata.add(customerdata)
			customerdata = (row[1])
			listcustdata.add(customerdata)
			customerdata = (row[2])
			listcustdata.add(customerdata)
			customerdata = (row[3])
			listcustdata.add(customerdata)
			customerdata = (row[4])
			listcustdata.add(customerdata)
		})
		return listcustdata
	}

	@Keyword
	public GuarantorDataPersonal (Sql instance, String appno){
		String customerdata
		ArrayList<String> listcustdata = new ArrayList<>()
		instance.eachRow(("Select CUST_NAME, rml.REF_MASTER_NAME, FORMAT(BIRTH_DT, 'MM/dd/yyyy') , ID_NO, acp.MOTHER_MAIDEN_NAME FROM APP_CUST ac WITH(NOLOCK) JOIN APP a WITH(NOLOCK) ON ac.APP_ID = a.APP_ID JOIN APP_CUST_PERSONAL acp WITH(NOLOCK) ON acp.APP_CUST_ID = ac.APP_CUST_ID JOIN REF_MASTER_LOS rml WITH(NOLOCK) ON rml.REF_MASTER_CODE = MR_ID_TYPE_CODE WHERE APP_NO = '"+ appno +"' AND IS_GUARANTOR = 1 AND rml.REF_MASTER_TYPE_CODE = 'ID_TYPE'"), {  row ->

			customerdata = (row[0])
			listcustdata.add(customerdata)
			customerdata = (row[1])
			listcustdata.add(customerdata)
			customerdata = (row[2])
			listcustdata.add(customerdata)
			customerdata = (row[3])
			listcustdata.add(customerdata)
			customerdata = (row[4])
			listcustdata.add(customerdata)
		})
		return listcustdata
	}

	@Keyword
	public GuarantorDataCompany (Sql instance, String appno){
		String customerdata
		ArrayList<String> listcustdata = new ArrayList<>()
		instance.eachRow(("Select CUST_NAME, TAX_ID_NO FROM APP_CUST ac WITH(NOLOCK) JOIN APP a WITH(NOLOCK) ON ac.APP_ID = a.APP_ID JOIN APP_CUST_COMPANY acp WITH(NOLOCK) ON acp.APP_CUST_ID = ac.APP_CUST_ID WHERE APP_NO = '"+ appno +"' AND IS_GUARANTOR = 1"), {  row ->

			customerdata = (row[0])
			listcustdata.add(customerdata)
			customerdata = (row[1])
			listcustdata.add(customerdata)
		})
		return listcustdata
	}

	@Keyword
	public CustomerDataCompany (Sql instance, String appno){
		String customerdata
		ArrayList<String> listcustdata = new ArrayList<>()
		instance.eachRow(("Select CUST_NAME, TAX_ID_NO FROM APP_CUST ac WITH(NOLOCK) JOIN APP a WITH(NOLOCK) ON ac.APP_ID = a.APP_ID JOIN APP_CUST_COMPANY acp WITH(NOLOCK) ON acp.APP_CUST_ID = ac.APP_CUST_ID WHERE APP_NO = '"+ appno +"' AND IS_CUSTOMER = 1"), {  row ->

			customerdata = (row[0])
			listcustdata.add(customerdata)
			customerdata = (row[1])
			listcustdata.add(customerdata)
		})
		return listcustdata
	}

	@Keyword
	public ShareholderDataPersonal (Sql instance, String appno){
		String customerdata
		ArrayList<String> listcustdata = new ArrayList<>()
		instance.eachRow(("Select CUST_NAME, rml.REF_MASTER_NAME, FORMAT(BIRTH_DT, 'MM/dd/yyyy') , ID_NO, acp.MOTHER_MAIDEN_NAME FROM APP_CUST ac WITH(NOLOCK) JOIN APP a WITH(NOLOCK) ON ac.APP_ID = a.APP_ID JOIN APP_CUST_PERSONAL acp WITH(NOLOCK) ON acp.APP_CUST_ID = ac.APP_CUST_ID JOIN REF_MASTER_LOS rml WITH(NOLOCK) ON rml.REF_MASTER_CODE = MR_ID_TYPE_CODE WHERE APP_NO = '"+ appno +"' AND IS_SHAREHOLDER = 1 AND rml.REF_MASTER_TYPE_CODE = 'ID_TYPE' AND rml.IS_ACTIVE = '1'"), {  row ->

			customerdata = (row[0])
			listcustdata.add(customerdata)
			customerdata = (row[1])
			listcustdata.add(customerdata)
			customerdata = (row[2])
			listcustdata.add(customerdata)
			customerdata = (row[3])
			listcustdata.add(customerdata)
			customerdata = (row[4])
			listcustdata.add(customerdata)
		})
		return listcustdata
	}

	@Keyword
	public ShareholderDataCompany (Sql instance, String appno){
		String customerdata
		ArrayList<String> listcustdata = new ArrayList<>()
		instance.eachRow(("Select CUST_NAME, TAX_ID_NO FROM APP_CUST ac WITH(NOLOCK) JOIN APP a WITH(NOLOCK) ON ac.APP_ID = a.APP_ID JOIN APP_CUST_COMPANY acp WITH(NOLOCK) ON acp.APP_CUST_ID = ac.APP_CUST_ID WHERE APP_NO = '"+ appno +"' AND IS_SHAREHOLDER = 1"), {  row ->

			customerdata = (row[0])
			listcustdata.add(customerdata)
			customerdata = (row[1])
			listcustdata.add(customerdata)
		})
		return listcustdata
	}


	@Keyword
	public GetFamilyDataforEditNAP(Sql instance, String appno){
		ArrayList<String> arrayFamilyData = new ArrayList<String>()
		String familydata
		instance.eachRow(("SELECT CUST_NAME AS HEADER, [CUST_TYPE] AS HEADER,[RELATIONSHIP] AS HEADER ,[CUST_MODEL] AS HEADER FROM (SELECT CUST_NAME, mastername.Code, REF_MASTER_NAME,app_cust_id FROM (select cust_name, [Code], value,app_cust_id FROM (select cust_name, mr_cust_type_code AS [CUST_TYPE], (CASE WHEN MR_CUST_RELATIONSHIP_CODE = '' THEN 'SELF' ELSE MR_CUST_RELATIONSHIP_CODE END) as [RELATIONSHIP], MR_CUST_MODEL_CODE AS [CUST_MODEL],app_cust_id from app_cust ac with(nolock) join app a with(nolock) on ac.app_id = a.app_id where app_no ='"+appno+"' and (is_customer=1 or is_family=1)) as Orig unpivot (value for [Code] in ([CUST_TYPE],[RELATIONSHIP],[CUST_MODEL]))as unpiv) as mastername JOIN REF_MASTER_LOS rf WITH(NOLOCK) ON rf.REF_MASTER_Code = mastername.value WHERE rf.IS_ACTIVE = '1' and rf.REF_MASTER_TYPE_CODE IN('CUST_PERSONAL_RELATIONSHIP','CUST_TYPE','CUST_MODEL')) AS ref PIVOT (MAX(ref.REF_MASTER_NAME) for [Code] in ([CUST_TYPE],[RELATIONSHIP],[CUST_MODEL])) as piv order by [RELATIONSHIP],app_cust_id"), {  row ->
			familydata = (row)
			println("family"+familydata)
			if(familydata!=null){
				familydata = familydata.replace('HEADER:', '').replace('[', '').replace(']', '')
				arrayFamilyData.add(familydata)
			}
		})


		return arrayFamilyData
	}

	@Keyword
	public GetMSDataforEditNAP (Sql instance, String appno){
		String customerdata
		ArrayList<String> listcustdata = new ArrayList<>()
		instance.eachRow(("SELECT CUST_NAME AS HEADER, MR_CUST_TYPE_CODE AS HEADER, SHARE_PRCNT AS HEADER, IS_ACTIVE AS HEADER, IS_OWNER AS HEADER, IS_SIGNER AS HEADER FROM APP_CUST ac WITH(NOLOCK) JOIN APP a WITH(NOLOCK) ON ac.APP_ID = a.APP_ID JOIN APP_CUST_COMPANY_MGMNT_SHRHOLDER accms WITH(NOLOCK) ON accms.APP_CUST_ID = ac.APP_CUST_ID WHERE APP_NO = '"+ appno +"' AND IS_SHAREHOLDER = '1'"), {  row ->

			customerdata = (row)
			if(customerdata!=null){
				customerdata = customerdata.replace('HEADER:', '').replace('[', '').replace(']', '')
				listcustdata.add(customerdata)
			}
		})
		return listcustdata
	}


	@Keyword
	public GetGuarantorDataforEditNAP(Sql instance, String appno){
		ArrayList<String> arrayGuarantorData = new ArrayList<String>()
		String guardata
		instance.eachRow(("SELECT CUST_NAME as HEADER, MR_CUST_TYPE_CODE as HEADER, MR_CUST_RELATIONSHIP_CODE as HEADER FROM APP_CUST ac WITH(NOLOCK) JOIN APP a WITH(NOLOCK) ON ac.APP_ID = a.APP_ID WHERE APP_NO = '"+appno+"' AND IS_GUARANTOR = '1' order by app_cust_id"), {  row ->
			guardata = (row)
			println("guar"+guardata)
			if(guardata!=null){
				guardata = guardata.replace('HEADER:', '').replace('[', '').replace(']', '')
				arrayGuarantorData.add(guardata)
			}
		})


		return arrayGuarantorData
	}
}



