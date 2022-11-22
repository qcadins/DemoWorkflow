package dbConnection

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

public class DupCheckVerif {

	@Keyword
	public checkDupcheck (Sql instance, String appNo){
		String dupcheckcount
		instance.eachRow(("select count(BUSINESS_KEY_) from [dbo].[ACT_HI_PROCINST] AHP join [dbo].[ACT_HI_VARINST] AHV WITH (NOLOCK) ON AHP.PROC_INST_ID_ = AHV.PROC_INST_ID_ where BUSINESS_KEY_ = '" + appNo + "' and AHV.PROC_DEF_KEY_ = 'WF_DUP_CHECK_MD_CF4W' and AHV.TEXT_ = 'true'"), {  row ->

			dupcheckcount = (row[0])
		})
		return dupcheckcount
	}

	@Keyword
	public checkCustomerType (Sql instance, String Appno, String CustName){
		String Custtype
		instance.eachRow((" select MR_CUST_TYPE_CODE from APP_CUST a join APP b WITH (NOLOCK) ON a.APP_ID = b.APP_ID where APP_NO = '" + Appno + "' and CUST_NAME = '" + CustName + "'"), {  row ->

			Custtype = (row[0])
		})
		return Custtype
	}

	@Keyword
	public checkDupcheckRulePersonal1 (Sql instance, String idno, String idtype, String Mothername){
		String Result1
		instance.eachRow(("SELECT count(a.CUST_ID) FROM CUST a WITH (NOLOCK) JOIN CUST_PERSONAL b WITH (NOLOCK) ON a.CUST_ID = b.CUST_ID JOIN REF_MASTER c WITH (NOLOCK) ON a.MR_ID_TYPE_CODE = c.MASTER_CODE WHERE MR_CUST_TYPE_CODE = 'PERSONAL' AND ID_NO = '" + idno + "' AND DESCR = '" + idtype + "' AND MOTHER_MAIDEN_NAME = '" + Mothername + "'"), { row ->

			Result1 = (row[0])
		})
		return Result1
	}

	@Keyword
	public checkDupcheckRulePersonal1LOS (Sql instance, String idno, String idtype, String Mothername, String appno){
		String Result1LOS
		instance.eachRow((" SELECT COUNT(DISTINCT a.APP_CUST_ID) FROM APP_CUST a WITH (NOLOCK) JOIN APP_CUST_PERSONAL b WITH (NOLOCK) ON a.APP_CUST_ID = b.APP_CUST_ID JOIN  REF_MASTER_LOS c WITH (NOLOCK) ON a.MR_ID_TYPE_CODE = c.REF_MASTER_CODE JOIN APP d WITH (NOLOCK) ON a.APP_ID = d.APP_ID WHERE MR_CUST_TYPE_CODE = 'PERSONAL' AND ID_NO = '" + idno + "' AND REF_MASTER_NAME = '" + idtype + "' AND MOTHER_MAIDEN_NAME = '" + Mothername + "' AND APP_NO != '"+ appno +"' AND APP_CURR_STEP not in( 'CUST', 'FAM', 'SHR', 'GUAR')"), { row ->

			Result1LOS = (row[0])
		})
		return Result1LOS
	}

	@Keyword
	public checkDupcheckRulePersonal2 (Sql instance, String idno, String idtype, String custname){
		String Result2
		instance.eachRow(("SELECT count(CUST_ID) FROM CUST a WITH (NOLOCK) JOIN REF_MASTER c WITH (NOLOCK) ON a.MR_ID_TYPE_CODE = c.MASTER_CODE WHERE MR_CUST_TYPE_CODE = 'PERSONAL' AND ID_NO = '" + idno + "' AND DESCR = '" + idtype + "' AND CUST_NAME = '" + custname + "'"), { row ->

			Result2 = (row[0])
		})
		return Result2
	}

	@Keyword
	public checkDupcheckRulePersonal2LOS (Sql instance, String idno, String idtype, String custname, String appno){
		String Result2LOS
		instance.eachRow((" SELECT COUNT(DISTINCT APP_CUST_ID) FROM APP_CUST a  WITH (NOLOCK) JOIN  REF_MASTER_LOS c WITH (NOLOCK) ON a.MR_ID_TYPE_CODE = c.REF_MASTER_CODE JOIN APP d WITH (NOLOCK) ON a.APP_ID = d.APP_ID  WHERE MR_CUST_TYPE_CODE = 'PERSONAL' AND ID_NO = '" + idno + "' AND REF_MASTER_NAME = '" + idtype + "' AND CUST_NAME = '" + custname + "' AND APP_NO != '"+ appno +"' AND APP_CURR_STEP not in( 'CUST', 'FAM', 'SHR', 'GUAR')"), { row ->

			Result2LOS = (row[0])
		})
		return Result2LOS
	}

	@Keyword
	public checkDupcheckRulePersonal3 (Sql instance, String idno, String idtype, String birthdate){
		String Result3
		instance.eachRow(("SELECT count(a.CUST_ID) FROM CUST a WITH (NOLOCK) JOIN CUST_PERSONAL b WITH (NOLOCK) ON a.CUST_ID = b.CUST_ID JOIN REF_MASTER c WITH (NOLOCK) ON a.MR_ID_TYPE_CODE = c.MASTER_CODE WHERE MR_CUST_TYPE_CODE = 'PERSONAL' AND ID_NO = '" + idno + "' AND DESCR = '" + idtype + "' AND FORMAT(b.BIRTH_DT, 'MM/dd/yyyy') = '" + birthdate + "'"), { row ->

			Result3 = (row[0])
		})
		return Result3
	}

	@Keyword
	public checkDupcheckRulePersonal3LOS (Sql instance, String idno, String idtype, String birthdate, String appno){
		String Result3LOS
		instance.eachRow((" SELECT COUNT(DISTINCT a.APP_CUST_ID) FROM APP_CUST a WITH (NOLOCK) JOIN APP_CUST_PERSONAL b WITH (NOLOCK) ON a.APP_CUST_ID = b.APP_CUST_ID JOIN  REF_MASTER_LOS c WITH (NOLOCK) ON a.MR_ID_TYPE_CODE = c.REF_MASTER_CODE JOIN APP d WITH (NOLOCK) ON a.APP_ID = d.APP_ID WHERE MR_CUST_TYPE_CODE = 'PERSONAL' AND ID_NO = '" + idno + "' AND REF_MASTER_NAME = '" + idtype + "' AND FORMAT(b.BIRTH_DT, 'MM/dd/yyyy') = '" + birthdate + "' AND APP_NO != '"+ appno +"' AND APP_CURR_STEP not in( 'CUST', 'FAM', 'SHR', 'GUAR')"), { row ->

			Result3LOS = (row[0])
		})
		return Result3LOS
	}

	@Keyword
	public checkDupcheckRulePersonal4 (Sql instance, String custname){
		String Result4
		instance.eachRow(("SELECT COUNT(DISTINCT CUST_NAME) FROM CUST WITH (NOLOCK) WHERE CUST_NAME LIKE '%" + custname + "%' AND MR_CUST_TYPE_CODE = 'PERSONAL'"), { row ->

			Result4 = (row[0])
		})
		return Result4
	}

	@Keyword
	public checkDupcheckRulePersonal4LOS (Sql instance, String custname, String appno){
		String Result4LOS
		instance.eachRow((" SELECT COUNT(DISTINCT CUST_NAME) FROM APP_CUST a WITH (NOLOCK) JOIN APP d WITH (NOLOCK) ON a.APP_ID = d.APP_ID  WHERE CUST_NAME LIKE '%" + custname + "%' AND MR_CUST_TYPE_CODE = 'PERSONAL' AND APP_NO != '"+ appno +"' AND APP_CURR_STEP not in( 'CUST', 'FAM', 'SHR', 'GUAR')"), { row ->

			Result4LOS = (row[0])
		})
		return Result4LOS
	}

	@Keyword
	public checkDupcheckRulePersonal5 (Sql instance, String idno, String idtype, String custname){
		String Result5
		instance.eachRow(("SELECT count(CUST_ID) FROM CUST a WITH (NOLOCK) JOIN REF_MASTER c WITH (NOLOCK) ON a.MR_ID_TYPE_CODE = c.MASTER_CODE WHERE MR_CUST_TYPE_CODE = 'PERSONAL' AND ID_NO = '" + idno + "' AND DESCR = '" + idtype + "' AND CUST_NAME LIKE '%" + custname + "%'"), { row ->

			Result5 = (row[0])
		})
		return Result5
	}

	@Keyword
	public checkDupcheckRulePersonal5LOS (Sql instance, String idno, String idtype, String custname, String appno){
		String Result5LOS
		instance.eachRow((" SELECT COUNT(DISTINCT APP_CUST_ID) FROM APP_CUST a WITH (NOLOCK) JOIN  REF_MASTER_LOS c WITH (NOLOCK) ON a.MR_ID_TYPE_CODE = c.REF_MASTER_CODE JOIN APP d WITH (NOLOCK) ON a.APP_ID = d.APP_ID  WHERE MR_CUST_TYPE_CODE = 'PERSONAL' AND ID_NO = '" + idno + "' AND REF_MASTER_NAME = '" + idtype + "' AND CUST_NAME LIKE '%" + custname + "%' AND APP_NO != '"+ appno +"' AND APP_CURR_STEP not in( 'CUST', 'FAM', 'SHR', 'GUAR')"), { row ->

			Result5LOS = (row[0])
		})
		return Result5LOS
	}

	@Keyword
	public checkDupcheckRulePersonal6 (Sql instance, String custname, String mothername){
		String Result6
		instance.eachRow(("SELECT count(a.CUST_NAME) FROM CUST a WITH (NOLOCK) JOIN CUST_PERSONAL b WITH (NOLOCK) ON a.CUST_ID = b.CUST_ID WHERE MR_CUST_TYPE_CODE = 'PERSONAL'  AND CUST_NAME LIKE '%" + custname + "%' AND MOTHER_MAIDEN_NAME LIKE '%" + mothername + "%'"), { row ->

			Result6 = (row[0])
		})
		return Result6
	}

	@Keyword
	public checkDupcheckRulePersonal6LOS (Sql instance, String custname, String mothername, String appno){
		String Result6LOS
		instance.eachRow((" SELECT COUNT(DISTINCT CUST_NAME) FROM APP_CUST a WITH (NOLOCK) JOIN APP_CUST_PERSONAL b WITH (NOLOCK) ON a.APP_CUST_ID = b.APP_CUST_ID JOIN APP d WITH (NOLOCK) ON a.APP_ID = d.APP_ID  WHERE MR_CUST_TYPE_CODE = 'PERSONAL'  AND CUST_NAME LIKE '%" + custname + "%' AND MOTHER_MAIDEN_NAME LIKE '%" + mothername + "%' AND APP_NO != '"+ appno +"' AND APP_CURR_STEP not in( 'CUST', 'FAM', 'SHR', 'GUAR')"), { row ->

			Result6LOS = (row[0])
		})
		return Result6LOS
	}

	@Keyword
	public checkDupcheckRulePersonal7 (Sql instance, String custname, String birthdate){
		String Result7
		instance.eachRow(("SELECT count(a.CUST_NAME) FROM CUST a WITH (NOLOCK) JOIN CUST_PERSONAL b WITH (NOLOCK) ON a.CUST_ID = b.CUST_ID  WHERE CUST_NAME LIKE '%" + custname + "%' AND FORMAT(b.BIRTH_DT, 'MM/dd/yyyy') = '" + birthdate + "'"), { row ->

			Result7 = (row[0])
		})
		return Result7
	}

	@Keyword
	public checkDupcheckRulePersonal7LOS (Sql instance, String custname, String birthdate, String appno){
		String Result7LOS
		instance.eachRow((" SELECT COUNT(DISTINCT CUST_NAME) FROM APP_CUST a WITH (NOLOCK) JOIN APP_CUST_PERSONAL b WITH (NOLOCK) ON a.APP_CUST_ID = b.APP_CUST_ID JOIN APP d WITH (NOLOCK) ON a.APP_ID = d.APP_ID  WHERE CUST_NAME LIKE '%" + custname + "%' AND MR_CUST_TYPE_CODE = 'PERSONAL' AND FORMAT(b.BIRTH_DT, 'MM/dd/yyyy') = '" + birthdate + "' AND APP_NO != '"+ appno +"' AND APP_CURR_STEP not in( 'CUST', 'FAM', 'SHR', 'GUAR')"), { row ->

			Result7LOS = (row[0])
		})
		return Result7LOS
	}

	@Keyword
	public checkDupcheckRuleCompany1 (Sql instance, String custname, String taxidno){
		String Result8
		instance.eachRow(("SELECT COUNT(a.CUST_NAME) FROM CUST a WITH (NOLOCK) JOIN CUST_COMPANY b WITH (NOLOCK) ON a.CUST_ID = b.CUST_ID WHERE MR_CUST_TYPE_CODE = 'COMPANY' AND CUST_NAME LIKE '%" + custname + "%' AND TAX_ID_NO = '" + taxidno + "'"), { row ->

			Result8 = (row[0])
		})
		return Result8
	}

	@Keyword
	public checkDupcheckRuleCompany1LOS (Sql instance, String custname, String taxidno, String appno){
		String Result8LOS
		instance.eachRow((" SELECT COUNT(a.CUST_NAME) FROM APP_CUST a WITH (NOLOCK) JOIN APP_CUST_COMPANY b WITH (NOLOCK) ON a.APP_CUST_ID = b.APP_CUST_ID JOIN APP d WITH (NOLOCK) ON a.APP_ID = d.APP_ID  WHERE MR_CUST_TYPE_CODE = 'COMPANY' AND CUST_NAME LIKE '%" + custname + "%' AND TAX_ID_NO = '" + taxidno + "' AND APP_NO != '"+ appno +"' AND APP_CURR_STEP not in( 'CUST', 'FAM', 'SHR', 'GUAR')"), { row ->

			Result8LOS = (row[0])
		})
		return Result8LOS
	}

	@Keyword
	public checkDupcheckRuleCompany2 (Sql instance, String custname){
		String Result9
		instance.eachRow(("SELECT COUNT(a.CUST_NAME) FROM CUST a WITH (NOLOCK) JOIN CUST_COMPANY b WITH (NOLOCK) ON a.CUST_ID = b.CUST_ID WHERE MR_CUST_TYPE_CODE = 'COMPANY' AND CUST_NAME = '" + custname + "'"), { row ->

			Result9 = (row[0])
		})
		return Result9
	}

	@Keyword
	public checkDupcheckRuleCompany2LOS (Sql instance, String custname, String appno){
		String Result9LOS
		instance.eachRow((" SELECT COUNT(CUST_NAME) FROM APP_CUST a WITH (NOLOCK) JOIN APP d WITH (NOLOCK) ON a.APP_ID = d.APP_ID WHERE MR_CUST_TYPE_CODE = 'COMPANY' AND CUST_NAME = '" + custname + "' AND APP_NO != '"+ appno +"' AND APP_CURR_STEP not in( 'CUST', 'FAM', 'SHR', 'GUAR')"), { row ->

			Result9LOS = (row[0])
		})
		return Result9LOS
	}

	@Keyword
	public checkNegativeRulePersonal1 (Sql instance, String idno, String idtype, String Mothername){
		String Result1
		instance.eachRow(("SELECT count(a.CUST_ID) FROM NEGATIVE_CUST a WITH (NOLOCK) JOIN CUST_PERSONAL b WITH (NOLOCK) ON a.CUST_ID = b.CUST_ID JOIN REF_MASTER c WITH (NOLOCK) ON a.MR_ID_TYPE_CODE = c.MASTER_CODE WHERE MR_CUST_TYPE_CODE = 'PERSONAL' AND ID_NO = '" + idno + "' AND DESCR = '" + idtype + "' AND a.MOTHER_MAIDEN_NAME = '" + Mothername + "' AND a.IS_ACTIVE = 1"), { row ->

			Result1 = (row[0])
		})
		return Result1
	}

	@Keyword
	public checkNegativeRulePersonal2 (Sql instance, String idno, String idtype, String custname){
		String Result2
		instance.eachRow(("SELECT count(CUST_ID) FROM NEGATIVE_CUST a WITH (NOLOCK) JOIN REF_MASTER c WITH (NOLOCK) ON a.MR_ID_TYPE_CODE = c.MASTER_CODE WHERE MR_CUST_TYPE_CODE = 'PERSONAL' AND ID_NO = '" + idno + "' AND DESCR = '" + idtype + "' AND CUST_NAME = '" + custname + "'"), { row ->

			Result2 = (row[0])
		})
		return Result2
	}

	@Keyword
	public checkNegativeRulePersonal3 (Sql instance, String idno, String idtype, String birthdate){
		String Result3
		instance.eachRow(("SELECT count(a.CUST_ID) FROM NEGATIVE_CUST a WITH (NOLOCK) JOIN CUST_PERSONAL b WITH (NOLOCK) ON a.CUST_ID = b.CUST_ID JOIN REF_MASTER c WITH (NOLOCK) ON a.MR_ID_TYPE_CODE = c.MASTER_CODE WHERE MR_CUST_TYPE_CODE = 'PERSONAL' AND ID_NO = '" + idno + "' AND DESCR = '" + idtype + "' AND FORMAT(b.BIRTH_DT, 'MM/dd/yyyy') = '" + birthdate + "' AND a.IS_ACTIVE = 1"), { row ->

			Result3 = (row[0])
		})
		return Result3
	}

	@Keyword
	public checkNegativeRulePersonal4 (Sql instance, String custname){
		String Result4
		instance.eachRow(("SELECT COUNT(DISTINCT CUST_NAME) FROM NEGATIVE_CUST WITH (NOLOCK) WHERE CUST_NAME LIKE '%" + custname + "%' AND MR_CUST_TYPE_CODE = 'PERSONAL' AND IS_ACTIVE = 1"), { row ->

			Result4 = (row[0])
		})
		return Result4
	}

	@Keyword
	public checkNegativeRulePersonal5 (Sql instance, String idno, String idtype, String custname){
		String Result5
		instance.eachRow(("SELECT count(CUST_ID) FROM NEGATIVE_CUST a WITH (NOLOCK) JOIN REF_MASTER c WITH (NOLOCK) ON a.MR_ID_TYPE_CODE = c.MASTER_CODE WHERE MR_CUST_TYPE_CODE = 'PERSONAL' AND ID_NO = '" + idno + "' AND DESCR = '" + idtype + "' AND CUST_NAME LIKE '%" + custname + "%' AND a.IS_ACTIVE = 1"), { row ->

			Result5 = (row[0])
		})
		return Result5
	}

	@Keyword
	public checkNegativeRulePersonal6 (Sql instance, String custname, String mothername){
		String Result6
		instance.eachRow(("SELECT count(a.CUST_NAME) FROM NEGATIVE_CUST a WITH (NOLOCK) JOIN CUST_PERSONAL b WITH (NOLOCK) ON a.CUST_ID = b.CUST_ID WHERE MR_CUST_TYPE_CODE = 'PERSONAL'  AND CUST_NAME LIKE '%" + custname + "%' AND a.MOTHER_MAIDEN_NAME LIKE '%" + mothername + "%' AND a.IS_ACTIVE = 1"), { row ->

			Result6 = (row[0])
		})
		return Result6
	}

	@Keyword
	public checkNegativeRulePersonal7 (Sql instance, String custname, String birthdate){
		String Result7
		instance.eachRow(("SELECT count(a.CUST_NAME) FROM NEGATIVE_CUST a WITH (NOLOCK) JOIN CUST_PERSONAL b WITH (NOLOCK) ON a.CUST_ID = b.CUST_ID  WHERE CUST_NAME LIKE '%" + custname + "%' AND FORMAT(b.BIRTH_DT, 'MM/dd/yyyy') = '" + birthdate + "' AND a.IS_ACTIVE = 1"), { row ->

			Result7 = (row[0])
		})
		return Result7
	}

	@Keyword
	public checkNegativeRuleCompany1 (Sql instance, String custname, String taxidno){
		String Result8
		instance.eachRow(("SELECT COUNT(a.CUST_NAME) FROM NEGATIVE_CUST a WITH (NOLOCK) JOIN CUST_COMPANY b WITH (NOLOCK) ON a.CUST_ID = b.CUST_ID WHERE MR_CUST_TYPE_CODE = 'COMPANY' AND CUST_NAME LIKE '%" + custname + "%' AND TAX_ID_NO = '" + taxidno + "' AND a.IS_ACTIVE = 1"), { row ->

			Result8 = (row[0])
		})
		return Result8
	}

	@Keyword
	public checkNegativeRuleCompany2 (Sql instance, String custname){
		String Result9
		instance.eachRow(("SELECT COUNT(a.CUST_NAME) FROM NEGATIVE_CUST a WITH (NOLOCK) JOIN CUST_COMPANY b WITH (NOLOCK) ON a.CUST_ID = b.CUST_ID WHERE MR_CUST_TYPE_CODE = 'COMPANY' AND CUST_NAME = '" + custname + "' AND a.IS_ACTIVE = 1"), { row ->

			Result9 = (row[0])
		})
		return Result9
	}

	@Keyword
	public checkDupCheckStatus (Sql instance, String appno){
		String Result
		Boolean DupCheckStatus
		instance.eachRow(("SELECT CUST_CHECKING_STEP FROM APP WITH(NOLOCK) WHERE APP_NO = '"+ appno + "'"), { row ->

			Result = (row[0])

			if (Result == 'NULL' || Result == 'CDC_AUTO' || Result == 'CDC'){
				DupCheckStatus = true
			}else{
				DupCheckStatus = false
			}
		})
		return DupCheckStatus
	}
}

