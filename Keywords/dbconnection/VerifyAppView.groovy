package dbconnection

import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject

import java.sql.ResultSet
import java.sql.ResultSetMetaData
import java.sql.Statement

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

public class VerifyAppView {

	int colmcount
	int i

	@Keyword
	public checkAppViewDataDB (Sql instance, String appno){
		String appdata
		ArrayList <String> listappdata = new ArrayList<String>()
		instance.eachRow(("SELECT APP_NO, ORI_OFFICE_NAME, CUST_NO, CUST_NAME, PROD_OFFERING_NAME, TENOR, FULL_ASSET_NAME, rml.REF_MASTER_NAME FROM APP_CUST ac WITH(NOLOCK) JOIN APP a WITH(NOLOCK) ON ac.APP_ID = a.APP_ID JOIN APP_ASSET aa WITH(NOLOCK) ON aa.APP_ID = ac.APP_ID JOIN REF_MASTER_LOS rml WITH(NOLOCK) ON rml.REF_MASTER_CODE = MR_CUST_MODEL_CODE WHERE APP_NO = '"+ appno +"' AND IS_CUSTOMER = '1' AND rml.IS_ACTIVE = '1'"), {  row ->

			ResultSetMetaData rsmd = row.getMetaData()
			colmcount = rsmd.getColumnCount()


			for(i = 0 ; i < colmcount ; i++){
				appdata = (row[i])
				listappdata.add(appdata)
			}
		})
		return listappdata
	}

	@Keyword
	public checkCustomerMainDataCompany (Sql instance, String appno){
		String appdata
		ArrayList <String> listappdata = new ArrayList<String>()
		instance.eachRow(("SELECT CUST_NAME, ritl.INDUSTRY_TYPE_NAME, NUM_OF_EMP, CASE WHEN IS_AFFILIATE_WITH_MF = 0 THEN '-' END, CASE WHEN IS_VIP = 0 THEN 'NO' END, MR_CUST_MODEL_CODE, MR_COMPANY_TYPE_CODE, TAX_ID_NO, FORMAT(ESTABLISHMENT_DT, 'dd-MMM-yyyy') FROM APP_CUST_COMPANY acc WITH(NOLOCK) JOIN APP_CUST ac WITH(NOLOCK) ON acc.APP_CUST_ID = ac.APP_CUST_ID JOIN APP a WITH(NOLOCK) ON ac.APP_ID = a.APP_ID JOIN REF_INDUSTRY_TYPE_LOS ritl WITH(NOLOCK) ON ritl.INDUSTRY_TYPE_CODE = acc.INDUSTRY_TYPE_CODE WHERE APP_NO = '"+ appno +"' AND IS_CUSTOMER = '1'"), {  row ->

			ResultSetMetaData rsmd = row.getMetaData()
			colmcount = rsmd.getColumnCount()


			for(i = 0 ; i < colmcount ; i++){
				appdata = (row[i])
				listappdata.add(appdata)
			}
		})
		return listappdata
	}

	@Keyword
	public checkCustomerMainDataPersonal (Sql instance, String appno){
		String appdata
		ArrayList <String> listappdata = new ArrayList<String>()
		instance.eachRow(("SELECT CUST_NAME, [SALUTATION], CASE WHEN [Custprefix] IS NULL THEN '-' ELSE [Custprefix] END, CASE WHEN [Custsuffix] IS NULL THEN '-' ELSE [Custsuffix] END, MR_GENDER_CODE, [ID_TYPE] + ' / ' + ID_NO, MOTHER_MAIDEN_NAME, CASE WHEN FORMAT(ID_EXPIRED_DT, 'dd-MMM-yyyy') IS NULL THEN '-' ELSE FORMAT(ID_EXPIRED_DT, 'dd-MMM-yyyy') END, MR_MARITAL_STAT_CODE, BIRTH_PLACE + ' / ' + FORMAT(BIRTH_DT, 'dd-MMM-yyyy'), [NATIONALITY], CASE WHEN TAX_ID_NO IS NULL THEN '-' ELSE TAX_ID_NO END, [Countryname], MOBILE_PHN_NO_1, [EDUCATION], EMAIL_1, [RELIGION], CASE WHEN CAST([IS_VIP] as nvarchar(25)) = '0' THEN 'NO' WHEN CAST([IS_VIP] as nvarchar(25)) = '1' THEN 'NO' ELSE CAST([IS_VIP] as nvarchar(25)) END  FROM (SELECT [Custprefix], [IS_VIP], [Custsuffix], [Countryname], CUST_NAME, MR_GENDER_CODE, ID_NO, MOTHER_MAIDEN_NAME, ID_EXPIRED_DT, MR_MARITAL_STAT_CODE, BIRTH_PLACE, BIRTH_DT, TAX_ID_NO, MOBILE_PHN_NO_1, EMAIL_1, mastername.Code, REF_MASTER_NAME FROM (select [Custprefix], [IS_VIP], [Custsuffix], [Countryname], CUST_NAME, MR_GENDER_CODE , ID_NO, MOTHER_MAIDEN_NAME, ID_EXPIRED_DT, MR_MARITAL_STAT_CODE, BIRTH_PLACE, BIRTH_DT, TAX_ID_NO, MOBILE_PHN_NO_1, EMAIL_1, [Code], value FROM (SELECT MR_EDUCATION_CODE AS 'EDUCATION', MR_NATIONALITY_CODE as 'NATIONALITY', MR_RELIGION_CODE as 'RELIGION', MR_SALUTATION_CODE as 'SALUTATION', acp.CUST_PREFIX_NAME as [Custprefix], ac.IS_VIP  as [IS_VIP], acp.CUST_SUFFIX_NAME as [Custsuffix], rc.COUNTRY_NAME as [Countryname], CUST_NAME, MR_GENDER_CODE, MR_ID_TYPE_CODE as 'ID_TYPE', ID_NO, MOTHER_MAIDEN_NAME, ID_EXPIRED_DT, MR_MARITAL_STAT_CODE, BIRTH_PLACE, BIRTH_DT, TAX_ID_NO, MOBILE_PHN_NO_1, EMAIL_1  FROM APP_CUST_PERSONAL acp WITH(NOLOCK) join app_cust ac WITH(NOLOCK) ON ac.app_cust_id = acp.APP_CUST_ID JOIN APP a WITH(NOLOCK) ON ac.APP_ID = a.APP_ID JOIN V_REF_COUNTRY rc WITH(NOLOCK) ON rc.COUNTRY_CODE = acp.NATIONALITY_COUNTRY_CODE WHERE ac.IS_CUSTOMER = '1' and app_no ='"+ appno +"') as Orig unpivot (value for [Code] in ([ID_TYPE],[EDUCATION],[NATIONALITY],[RELIGION],[SALUTATION]) )as unpiv) as mastername JOIN REF_MASTER_LOS rf WITH(NOLOCK) ON rf.REF_MASTER_Code = mastername.value WHERE rf.IS_ACTIVE = '1') AS ref PIVOT (MAX(ref.REF_MASTER_NAME) for [Code] in ([ID_TYPE],[EDUCATION],[NATIONALITY],[RELIGION],[SALUTATION])) as piv"), {  row ->

			ResultSetMetaData rsmd = row.getMetaData()
			colmcount = rsmd.getColumnCount()


			for(i = 0 ; i < colmcount ; i++){
				appdata = (row[i])
				listappdata.add(appdata)
			}
		})
		return listappdata
	}

	@Keyword
	public checkAddrData (Sql instance, String appno){
		String appdata
		ArrayList <String> listappdata = new ArrayList<String>()
		instance.eachRow(("SELECT [CUST_ADDR_TYPE], [ADDR], CASE WHEN [BUILDING_OWNERSHIP] IS NULL THEN '-' ELSE [BUILDING_OWNERSHIP] END,CASE WHEN PHN_EXT_1 IS NOT NULL THEN [PHN1] + ' -' + PHN_EXT_1 ELSE [PHN1] + ' -' END, CASE WHEN PHN_EXT_2 IS NOT NULL THEN [PHN2] + ' -' + PHN_EXT_2 ELSE [PHN2] + ' -' END  FROM (SELECT APP_CUST_ADDR_ID, [ADDR], [PHN1], [PHN2], PHN_EXT_1, PHN_EXT_2, mastername.Code, REF_MASTER_NAME FROM (select PHN_EXT_1, PHN_EXT_2, APP_CUST_ADDR_ID, [ADDR], [PHN1], [PHN2], [Code], value FROM (SELECT PHN_EXT_2, APP_CUST_ADDR_ID, MR_CUST_ADDR_TYPE_CODE AS 'CUST_ADDR_TYPE', CASE WHEN aca.ADDR + ' RT/RW ' + aca.AREA_CODE_4 + '/' + aca.AREA_CODE_3 + ' ' + aca.AREA_CODE_2 + ' ' + aca.AREA_CODE_1 + ' ' + aca.CITY + ' ' + aca.ZIPCODE IS NULL OR aca.ADDR NOT LIKE '%[a-zA-Z0-9]%' THEN 'RT/RW /' ELSE aca.ADDR + ' RT/RW ' + aca.AREA_CODE_4 + '/' + aca.AREA_CODE_3 + ' ' + aca.AREA_CODE_2 + ' ' + aca.AREA_CODE_1 + ' ' + aca.CITY + ' ' + aca.ZIPCODE END AS [ADDR], MR_HOUSE_OWNERSHIP_CODE  AS 'BUILDING_OWNERSHIP',  aca.PHN_EXT_1 , CASE WHEN aca.PHN_AREA_1 + ' - ' + aca.PHN_1 IS NULL OR aca.PHN_AREA_1 NOT LIKE '%[a-zA-Z0-9]%' OR aca.PHN_1 NOT LIKE '%[a-zA-Z0-9]%' THEN '-' ELSE aca.PHN_AREA_1 + ' - ' + aca.PHN_1 END AS [PHN1], CASE WHEN aca.PHN_AREA_2 + ' - ' + aca.PHN_2 IS NULL OR aca.PHN_AREA_2 NOT LIKE '%[a-zA-Z0-9]%' OR aca.PHN_2 NOT LIKE '%[a-zA-Z0-9]%' THEN '-' ELSE aca.PHN_AREA_2 + ' - ' + aca.PHN_2 END AS [PHN2] FROM APP_CUST_ADDR aca WITH(NOLOCK) JOIN APP_CUST ac WITH(NOLOCK) ON aca.APP_CUST_ID = ac.APP_CUST_ID JOIN APP a WITH(NOLOCK) ON a.APP_ID = ac.APP_ID JOIN REF_MASTER_LOS rml WITH(NOLOCK) ON rml.REF_MASTER_CODE = aca.MR_CUST_ADDR_TYPE_CODE WHERE a.APP_NO = '"+ appno +"'  AND IS_CUSTOMER = '1' ) as orig unpivot (value for [Code] in ([CUST_ADDR_TYPE],[BUILDING_OWNERSHIP]) )as unpiv) as mastername JOIN REF_MASTER_LOS rf WITH(NOLOCK) ON rf.REF_MASTER_Code = mastername.value WHERE rf.IS_ACTIVE = '1' ) AS ref PIVOT (MAX(ref.REF_MASTER_NAME) for [Code] in ([CUST_ADDR_TYPE],[BUILDING_OWNERSHIP])) as piv ORDER BY piv.APP_CUST_ADDR_ID"), {  row ->

			ResultSetMetaData rsmd = row.getMetaData()
			colmcount = rsmd.getColumnCount()


			for(i = 0 ; i < colmcount ; i++){
				appdata = (row[i])
				listappdata.add(appdata)
			}
		})
		return listappdata
	}

	@Keyword
	public checkMSData (Sql instance, String appno){
		String appdata
		ArrayList <String> listappdata = new ArrayList<String>()
		instance.eachRow(("SELECT CUST_NAME, MR_CUST_TYPE_CODE, accms.SHARE_PRCNT, CASE WHEN IS_ACTIVE = 1 THEN 'YES' ELSE 'NO' END, CASE WHEN IS_OWNER = 1 THEN 'YES' ELSE 'NO' END, CASE WHEN IS_SIGNER = 1 THEN 'YES' ELSE 'NO' END FROM APP_CUST ac WITH(NOLOCK) JOIN APP a WITH(NOLOCK) ON a.APP_ID = ac.APP_ID JOIN APP_CUST_COMPANY_MGMNT_SHRHOLDER accms WITH(NOLOCK) ON accms.APP_CUST_ID = ac.APP_CUST_ID WHERE a.APP_NO = '"+ appno +"' AND IS_SHAREHOLDER = '1'"), {  row ->

			ResultSetMetaData rsmd = row.getMetaData()
			colmcount = rsmd.getColumnCount()


			for(i = 0 ; i < colmcount ; i++){
				appdata = (row[i])
				listappdata.add(appdata)
			}
		})
		return listappdata
	}

	@Keyword
	public checkFamData (Sql instance, String appno){
		String appdata
		ArrayList <String> listappdata = new ArrayList<String>()
		instance.eachRow(("SELECT CUST_NAME, MR_CUST_RELATIONSHIP_CODE, BIRTH_PLACE, FORMAT(BIRTH_DT, 'dd-MMM-yyyy') FROM APP_CUST ac WITH(NOLOCK) JOIN APP a WITH(NOLOCK) ON a.APP_ID = ac.APP_ID JOIN APP_CUST_PERSONAL acp WITH(NOLOCK) ON acp.APP_CUST_ID = ac.APP_CUST_ID WHERE a.APP_NO = '"+ appno +"' AND IS_FAMILY = '1'"), {  row ->

			ResultSetMetaData rsmd = row.getMetaData()
			colmcount = rsmd.getColumnCount()


			for(i = 0 ; i < colmcount ; i++){
				appdata = (row[i])
				listappdata.add(appdata)
			}
		})
		return listappdata
	}

	@Keyword
	public checkContactPersonData (Sql instance, String appno){
		String appdata
		ArrayList <String> listappdata = new ArrayList<String>()
		instance.eachRow(("SELECT [ContactPersonName], [GENDER], [JobTitle], [JOB_POSITION], CASE WHEN [ID_TYPE] IS NULL THEN '-' ELSE [ID_TYPE] END, CASE WHEN [BirthPlace] NOT LIKE '%[a-zA-Z0-9]%' THEN '-' ELSE [BirthPlace] END, CASE WHEN [ContactIDNo] IS NULL THEN '-' ELSE [ContactIDNo] END, CASE WHEN [BirthDate] IS NULL THEN '-' ELSE [BirthDate] END, CASE WHEN [ExpiredDate] IS NULL THEN '-' ELSE [ExpiredDate] END, CASE WHEN [CUST_COMPANY_RELATIONSHIP] IS NULL THEN '-' ELSE [CUST_COMPANY_RELATIONSHIP] END, CASE WHEN [MobilePhn1] NOT LIKE '%[a-zA-Z0-9]%' THEN '-' ELSE [MobilePhn1] END, CASE WHEN [Mobilephn2] NOT LIKE '%[a-zA-Z0-9]%' THEN '-' ELSE [Mobilephn2] END, CASE WHEN [Email1] NOT LIKE '%[a-zA-Z0-9]%' THEN '-' ELSE [Email1] END, CASE WHEN [Email2] NOT LIKE '%[a-zA-Z0-9]%' THEN '-' ELSE [Email2] END FROM (SELECT [ContactPersonName], [JobTitle], [ContactIDNo], [ExpiredDate], [MobilePhn1], [Email1], [BirthPlace], [BirthDate], [Mobilephn2], [Email2], mastername.Code, REF_MASTER_NAME FROM (select [ContactPersonName], [JobTitle], [ContactIDNo], [ExpiredDate], [MobilePhn1], [Email1], [BirthPlace], [BirthDate], [Mobilephn2], [Email2], [Code], value FROM (SELECT acccp.CONTACT_PERSON_NAME as [ContactPersonName], acccp.JOB_TITLE_NAME as [JobTitle], acccp.MR_ID_TYPE_CODE as 'ID_TYPE', acccp.ID_NO as [ContactIDNo], FORMAT(acccp.ID_EXPIRED_DT, 'MM/dd/yyyy') as [ExpiredDate], acccp.MOBILE_PHN_NO_1 as [MobilePhn1], acccp.EMAIL_1 as [Email1], acccp.MR_JOB_POSITION_CODE as 'JOB_POSITION', acccp.BIRTH_PLACE as [BirthPlace], FORMAT(acccp.BIRTH_DT, 'MM/dd/yyyy') as [BirthDate], acccp.MR_CUST_RELATIONSHIP_CODE as 'CUST_COMPANY_RELATIONSHIP', acccp.MOBILE_PHN_NO_2 as [Mobilephn2], acccp.EMAIL_2 as [Email2], MR_GENDER_CODE as 'GENDER' FROM APP_CUST_COMPANY_CONTACT_PERSON acccp WITH(NOLOCK) JOIN APP_CUST_COMPANY acc WITH(NOLOCK) ON acc.APP_CUST_COMPANY_ID = acccp.APP_CUST_COMPANY_ID JOIN APP_CUST ac WITH(NOLOCK) ON ac.APP_CUST_ID = acc.APP_CUST_ID JOIN APP a WITH(NOLOCK) ON a.APP_ID = ac.APP_ID JOIN APP_CUST_ADDR aca ON aca.APP_CUST_ID = ac.APP_CUST_ID WHERE a.APP_NO = '"+ appno +"' AND IS_CUSTOMER = '1' AND aca.MR_CUST_ADDR_TYPE_CODE = 'COMPANY') as orig unpivot (value for [Code] in ([ID_TYPE],[CUST_COMPANY_RELATIONSHIP],[JOB_POSITION],[GENDER]) )as unpiv) as mastername JOIN REF_MASTER_LOS rf on rf.REF_MASTER_Code = mastername.value WHERE rf.IS_ACTIVE = '1') AS ref PIVOT (MAX(ref.REF_MASTER_NAME) for [Code] in ([ID_TYPE],[CUST_COMPANY_RELATIONSHIP],[JOB_POSITION],[GENDER])) as piv"), {  row ->

			ResultSetMetaData rsmd = row.getMetaData()
			colmcount = rsmd.getColumnCount()


			for(i = 0 ; i < colmcount ; i++){
				appdata = (row[i])
				listappdata.add(appdata)
			}
		})
		return listappdata
	}


	@Keyword
	public checkEmergencyContactData (Sql instance, String appno){
		String appdata
		ArrayList <String> listappdata = new ArrayList<String>()
		instance.eachRow(("SELECT ace.CONTACT_PERSON_NAME, ace.MR_GENDER_CODE, rml.REF_MASTER_NAME, CASE WHEN ace.BIRTH_PLACE NOT LIKE '%[a-zA-Z0-9]%' THEN '-' ELSE ace.BIRTH_PLACE END , ace.ID_NO,  CASE WHEN FORMAT(ace.BIRTH_DT, 'dd-MMM-yyyy') NOT LIKE '%[a-zA-Z0-9]%' OR FORMAT(ace.BIRTH_DT, 'dd-MMM-yyyy') IS NULL THEN '-' ELSE FORMAT(ace.BIRTH_DT, 'dd-MMM-yyyy') END, CASE WHEN FORMAT(ace.ID_EXPIRED_DT, 'dd-MMM-yyyy') IS NULL THEN '-' ELSE FORMAT(ace.ID_EXPIRED_DT, 'dd-MMM-yyyy') END, ace.MR_CUST_RELATIONSHIP_CODE, CASE WHEN ace.MOBILE_PHN_NO_1 IS NULL THEN '-' ELSE ace.MOBILE_PHN_NO_1 END, CASE WHEN ace.MOBILE_PHN_NO_2 IS NULL THEN '-' ELSE ace.MOBILE_PHN_NO_2 END, ace.EMAIL, CASE WHEN ace.PHN_1 IS NULL THEN '-' ELSE ace.PHN_1 END, CASE WHEN ace.PHN_2 IS NULL THEN '-' ELSE ace.PHN_2 END, CASE WHEN ace.PHN_3 IS NULL THEN '-' ELSE ace.PHN_3 END  FROM APP_CUST_PERSONAL acp JOIN APP_CUST ac WITH(NOLOCK) ON acp.APP_CUST_ID = ac.APP_CUST_ID JOIN APP a WITH(NOLOCK) ON a.APP_ID = ac.APP_ID JOIN APP_CUST_EMRGNC_CNTCT ace WITH(NOLOCK) ON ace.APP_CUST_ID = acp.APP_CUST_ID JOIN REF_MASTER_LOS rml WITH(NOLOCK) ON rml.REF_MASTER_CODE = ace.MR_ID_TYPE_CODE WHERE a.APP_NO = '"+ appno +"' AND IS_CUSTOMER = 1 AND rml.REF_MASTER_TYPE_CODE = 'ID_TYPE'"), {  row ->

			ResultSetMetaData rsmd = row.getMetaData()
			colmcount = rsmd.getColumnCount()


			for(i = 0 ; i < colmcount ; i++){
				appdata = (row[i])
				listappdata.add(appdata)
			}
		})
		return listappdata
	}

	@Keyword
	public checkFinancialData (Sql instance, String appno){
		String appdata
		ArrayList <String> listappdata = new ArrayList<String>()
		instance.eachRow(("SELECT FORMAT(DATE_AS_OF, 'dd-MMM-yyyy') FROM APP_CUST_COMPANY_FIN_DATA accfd WITH(NOLOCK) JOIN APP_CUST ac WITH(NOLOCK) ON accfd.APP_CUST_ID = ac.APP_CUST_ID JOIN APP a WITH(NOLOCK) ON a.APP_ID = ac.APP_ID WHERE a.APP_NO = '"+ appno +"' AND IS_CUSTOMER = '1'"), {  row ->

			ResultSetMetaData rsmd = row.getMetaData()
			colmcount = rsmd.getColumnCount()


			for(i = 0 ; i < colmcount ; i++){
				appdata = (row[i])
				listappdata.add(appdata)
			}
		})
		return listappdata
	}

	@Keyword
	public checkFinancialDataPersonal (Sql instance, String appno){
		String appdata
		ArrayList <String> listappdata = new ArrayList<String>()
		instance.eachRow(("SELECT FORMAT(DATE_AS_OF, 'dd-MMM-yyyy') FROM APP_CUST_PERSONAL_FIN_DATA acpfd WITH(NOLOCK) JOIN APP_CUST_PERSONAL acp WITH(NOLOCK) ON acp.APP_CUST_PERSONAL_ID = acpfd.APP_CUST_PERSONAL_ID JOIN APP_CUST ac WITH(NOLOCK) ON acp.APP_CUST_ID = ac.APP_CUST_ID JOIN APP a WITH(NOLOCK) ON a.APP_ID = ac.APP_ID WHERE a.APP_NO = '"+ appno +"' AND IS_CUSTOMER = '1'"), {  row ->

			ResultSetMetaData rsmd = row.getMetaData()
			colmcount = rsmd.getColumnCount()


			for(i = 0 ; i < colmcount ; i++){
				appdata = (row[i])
				listappdata.add(appdata)
			}
		})
		return listappdata
	}

	@Keyword
	public checkFinancialAttrData (Sql instance, String appno){
		String appdata
		ArrayList <String> listappdata = new ArrayList<String>()
		instance.eachRow(("SELECT ATTR_VALUE  FROM APP_CUST_FIN_DATA_ATTR_CONTENT acfdac WITH(NOLOCK) JOIN APP_CUST ac WITH(NOLOCK) ON ac.APP_CUST_ID = acfdac.APP_CUST_ID JOIN APP a WITH(NOLOCK) ON a.APP_ID = ac.APP_ID WHERE a.APP_NO = '"+ appno +"' AND IS_CUSTOMER = '1'"), {  row ->

			ResultSetMetaData rsmd = row.getMetaData()
			colmcount = rsmd.getColumnCount()


			for(i = 0 ; i < colmcount ; i++){
				appdata = (row[i])
				listappdata.add(appdata)
			}
		})
		return listappdata
	}

	@Keyword
	public checkBankAcc (Sql instance, String appno){
		String appdata
		ArrayList <String> listappdata = new ArrayList<String>()
		instance.eachRow(("SELECT rbl.BANK_NAME + ' - ' + acba.BANK_ACC_NO + ' - ' + acba.BANK_ACC_NAME, acba.BANK_BRANCH , FORMAT(acba.BEG_BALANCE_AMT, 'N0')  FROM APP_CUST_BANK_ACC acba WITH(NOLOCK) JOIN APP_CUST_COMPANY acp WITH(NOLOCK) ON acba.APP_CUST_ID = acp.APP_CUST_ID JOIN APP_CUST ac WITH(NOLOCK) ON ac.APP_CUST_ID = acba.APP_CUST_ID JOIN APP a WITH(NOLOCK) ON a.APP_ID = ac.APP_ID JOIN REF_BANK_LOS rbl WITH(NOLOCK) ON rbl.BANK_CODE = acba.BANK_CODE WHERE a.APP_NO = '"+ appno +"' AND IS_CUSTOMER = '1'"), {  row ->

			ResultSetMetaData rsmd = row.getMetaData()
			colmcount = rsmd.getColumnCount()


			for(i = 0 ; i < colmcount ; i++){
				appdata = (row[i])
				listappdata.add(appdata)
			}
		})
		return listappdata
	}


	@Keyword
	public checkFinancialCheckBankStatStoreData (Sql instance, String appno, String name, String accno){
		String bankstat
		instance.eachRow(("SELECT acba.IS_BANK_STMNT  FROM APP_CUST_BANK_ACC acba WITH(NOLOCK) JOIN APP_CUST_PERSONAL acp WITH(NOLOCK) ON acba.APP_CUST_ID = acp.APP_CUST_ID JOIN APP_CUST ac WITH(NOLOCK) ON ac.APP_CUST_ID = acba.APP_CUST_ID JOIN APP a WITH(NOLOCK) ON a.APP_ID = ac.APP_ID JOIN APP_CUST_BANK_STMNT acbs WITH(NOLOCK) ON acba.APP_CUST_BANK_ACC_ID = acbs.APP_CUST_BANK_ACC_ID WHERE a.APP_NO = '"+ appno +"' AND ac.CUST_NAME = '"+ name +"' AND acba.BANK_ACC_NO = '"+ accno +"' GROUP BY acba.IS_BANK_STMNT"), {  row ->

			bankstat = (row[0])
		})
		return bankstat
	}

	@Keyword
	public checkBankStatData (Sql instance, String appno, String accno){
		String appdata
		ArrayList <String> listappdata = new ArrayList<String>()
		instance.eachRow(("SELECT acbs.MONTH  ,acbs.YEAR , acbs.DEBIT_TRX_COUNT , FORMAT(acbs.DEBIT_AMT, 'N0') , acbs.CREDIT_TRX_COUNT , FORMAT(acbs.CREDIT_AMT, 'N0'), acbs.BALANCE_AMT FROM APP_CUST_BANK_ACC acba WITH(NOLOCK) JOIN APP_CUST_COMPANY acp WITH(NOLOCK) ON acba.APP_CUST_ID = acp.APP_CUST_ID JOIN APP_CUST ac ON ac.APP_CUST_ID = acba.APP_CUST_ID JOIN APP a WITH(NOLOCK) ON a.APP_ID = ac.APP_ID JOIN APP_CUST_BANK_STMNT acbs WITH(NOLOCK) ON acba.APP_CUST_BANK_ACC_ID = acbs.APP_CUST_BANK_ACC_ID WHERE a.APP_NO = '"+ appno +"' AND acba.BANK_ACC_NO = '"+ accno +"' AND IS_CUSTOMER = '1' "), {  row ->

			ResultSetMetaData rsmd = row.getMetaData()
			colmcount = rsmd.getColumnCount()


			for(i = 0 ; i < colmcount ; i++){
				appdata = (row[i])
				listappdata.add(appdata)
			}
		})
		return listappdata
	}

	@Keyword
	public checkLegalDocData (Sql instance, String appno){
		String appdata
		ArrayList <String> listappdata = new ArrayList<String>()
		instance.eachRow(("SELECT MR_LEGAL_DOC_TYPE_CODE , DOC_NO , FORMAT(accld.DOC_DT, 'dd-MMMM-yyyy') , FORMAT(DOC_EXPIRED_DT, 'dd-MMMM-yyyy') , NOTARY_NAME FROM APP_CUST_COMPANY_LEGAL_DOC accld WITH(NOLOCK) JOIN APP_CUST_COMPANY acc WITH(NOLOCK) ON acc.APP_CUST_COMPANY_ID = accld.APP_CUST_COMPANY_ID JOIN APP_CUST ac WITH(NOLOCK) ON ac.APP_CUST_ID = acc.APP_CUST_ID JOIN APP a WITH(NOLOCK) ON a.APP_ID = ac.APP_ID WHERE a.APP_NO = '"+ appno +"' AND IS_CUSTOMER = '1'"), {  row ->

			ResultSetMetaData rsmd = row.getMetaData()
			colmcount = rsmd.getColumnCount()


			for(i = 0 ; i < colmcount ; i++){
				appdata = (row[i])
				listappdata.add(appdata)
			}
		})
		return listappdata
	}

	@Keyword
	public checkCustGroupData (Sql instance, String appno){
		String appdata
		ArrayList <String> listappdata = new ArrayList<String>()
		instance.eachRow(("SELECT CUST_NAME FROM APP_CUST ac WITH(NOLOCK), (SELECT acg.CUST_NO FROM APP_CUST_GRP acg WITH(NOLOCK) JOIN APP_CUST ac WITH(NOLOCK) ON ac.APP_CUST_ID = acg.APP_CUST_ID JOIN APP a WITH(NOLOCK) ON a.APP_ID = ac.APP_ID WHERE APP_NO = '"+ appno +"' AND IS_CUSTOMER = '1') as SUBQ  WHERE ac.CUST_NO = SUBQ.CUST_NO GROUP BY CUST_NAME"), {  row ->

			ResultSetMetaData rsmd = row.getMetaData()
			colmcount = rsmd.getColumnCount()


			for(i = 0 ; i < colmcount ; i++){
				appdata = (row[i])
				listappdata.add(appdata)
			}
		})
		return listappdata
	}

	@Keyword
	public checkOtherInfoData (Sql instance, String appno){
		String appdata
		ArrayList <String> listappdata = new ArrayList<String>()
		instance.eachRow(("SELECT oth.LBPPMS_DEBT_GRP_LBPP_DESCR , oth.LBPPMS_BIZ_SCL_LBPP_DESCR , oth.LBPPMS_CNTRPRT_LBPP_DESCR , oth.LBPPMS_BIZ_SUSTAIN_LBPP_DESCR  FROM APP_CUST_OTHER_INFO oth WITH(NOLOCK) JOIN APP_CUST ac WITH(NOLOCK) ON ac.APP_CUST_ID = oth.APP_CUST_ID JOIN APP a WITH(NOLOCK) ON a.APP_ID = ac.APP_ID WHERE a.APP_NO = '"+ appno +"' AND IS_CUSTOMER = '1'"), {  row ->

			ResultSetMetaData rsmd = row.getMetaData()
			colmcount = rsmd.getColumnCount()


			for(i = 0 ; i < colmcount ; i++){
				appdata = (row[i])
				listappdata.add(appdata)
			}
		})
		return listappdata
	}

	public checkOtherAttrData (Sql instance, String appno){
		String appdata
		ArrayList <String> listappdata = new ArrayList<String>()
		instance.eachRow(("SELECT CASE WHEN ref_master_name is null then acac.attr_value else ref_master_name end FROM APP_CUST_ATTR_CONTENT acac WITH (NOLOCK) JOIN APP_CUST ac WITH (NOLOCK) ON ac.APP_CUST_ID = acac.APP_CUST_ID JOIN APP a WITH (NOLOCK) ON a.APP_ID = ac.APP_ID join ref_attr_los ral WITH (NOLOCK) on ral.ATTR_CODE = acac.ref_attr_code left join ref_master_los rf with(nolock) on rf.ref_master_code = acac.attr_value and ref_master_type_code = ral.attr_value WHERE a.APP_NO = '"+ appno +"' AND IS_CUSTOMER = '1' group by acac.REF_ATTR_CODE, REF_MASTER_NAME,acac.attr_value,APP_CUST_ATTR_CONTENT_ID order by APP_CUST_ATTR_CONTENT_ID"), {  row ->

			ResultSetMetaData rsmd = row.getMetaData()
			colmcount = rsmd.getColumnCount()


			for(i = 0 ; i < colmcount ; i++){
				appdata = (row[i])
				listappdata.add(appdata)
			}
		})
		return listappdata
	}


	public checkGuarantor (Sql instance, String appno){
		String appdata
		ArrayList <String> listappdata = new ArrayList<String>()
		instance.eachRow(("SELECT CUST_NAME, MR_CUST_TYPE_CODE, REF_MASTER_NAME, CASE WHEN acccp.MOBILE_PHN_NO_1 IS NULL THEN '' ELSE acccp.MOBILE_PHN_NO_1 END FROM APP_CUST ac WITH(NOLOCK) JOIN APP a WITH(NOLOCK) ON ac.APP_ID = a.APP_ID JOIN REF_MASTER_LOS rml WITH(NOLOCK) ON rml.REF_MASTER_CODE = MR_CUST_RELATIONSHIP_CODE JOIN APP_CUST_COMPANY acc WITH(NOLOCK) ON acc.APP_CUST_ID = ac.APP_CUST_ID LEFT JOIN APP_CUST_COMPANY_CONTACT_PERSON acccp WITH(NOLOCK) ON acccp.APP_CUST_COMPANY_ID = acc.APP_CUST_COMPANY_ID WHERE APP_NO = '"+ appno +"' AND IS_GUARANTOR = '1' AND REF_MASTER_TYPE_CODE = 'CUST_COMPANY_RELATIONSHIP' UNION SELECT CUST_NAME, MR_CUST_TYPE_CODE, REF_MASTER_NAME, MOBILE_PHN_NO_1 FROM APP_CUST ac WITH(NOLOCK) JOIN APP a WITH(NOLOCK) ON ac.APP_ID = a.APP_ID JOIN APP_CUST_PERSONAL acp WITH(NOLOCK) ON acp.APP_CUST_ID = ac.APP_CUST_ID JOIN REF_MASTER_LOS rml WITH(NOLOCK) ON rml.REF_MASTER_CODE = MR_CUST_RELATIONSHIP_CODE WHERE APP_NO = '"+ appno +"' AND IS_GUARANTOR = '1' AND REF_MASTER_TYPE_CODE = 'CUST_RELATIONSHIP' order by MR_CUST_TYPE_CODE"), {  row ->

			ResultSetMetaData rsmd = row.getMetaData()
			colmcount = rsmd.getColumnCount()


			for(i = 0 ; i < colmcount ; i++){
				appdata = (row[i])
				listappdata.add(appdata)
			}
		})
		return listappdata
	}

	public checkAssetSupplierInfo (Sql instance, String appno){
		String appdata
		ArrayList <String> listappdata = new ArrayList<String>()
		instance.eachRow(("SELECT SUPPL_NAME , SUBQ.SUPPL_EMP_NAME , se.SUPPL_EMP_NAME FROM APP_ASSET_SUPPL_EMP se WITH(NOLOCK) JOIN APP_ASSET aaw WITH(NOLOCK) ON aaw.APP_ASSET_ID = se.APP_ASSET_ID JOIN APP a WITH(NOLOCK) ON a.APP_ID = aaw.APP_ID, (SELECT a.APP_NO, SUPPL_EMP_NAME FROM APP_ASSET_SUPPL_EMP se WITH(NOLOCK) JOIN APP_ASSET aaw WITH(NOLOCK) ON aaw.APP_ASSET_ID = se.APP_ASSET_ID JOIN APP a WITH(NOLOCK) ON a.APP_ID = aaw.APP_ID WHERE MR_SUPPL_EMP_POSITION_CODE = 'SALES_PERSON') as SUBQ WHERE SUBQ.APP_NO = a.APP_NO AND a.APP_NO = '"+ appno +"' AND se.MR_SUPPL_EMP_POSITION_CODE = 'ADMIN_HEAD'"), {  row ->

			ResultSetMetaData rsmd = row.getMetaData()
			colmcount = rsmd.getColumnCount()


			for(i = 0 ; i < colmcount ; i++){
				appdata = (row[i])
				listappdata.add(appdata)
			}
		})
		return listappdata
	}

	public checkAssetInformation (Sql instance, String appno){
		String appdata
		ArrayList <String> listappdata = new ArrayList<String>()
		instance.eachRow(("SELECT [ASSETNAME], ASSET_CATEGORY_NAME, [ASSETPRICE], CAST([DPPERCENT] as varchar(25)) + ' % | ' + CAST([DPAMOUNT] as varchar(25)), [ASSETCONDITION], [ASSET_USAGE], [MANYEAR], CASE WHEN [SERIALNO1] IS NULL THEN '' ELSE [SERIALNO1] END, CASE WHEN [SERIALNO2] IS NULL THEN '' ELSE [SERIALNO2] END, CASE WHEN [SERIALNO3] IS NULL THEN '' ELSE [SERIALNO3] END, CASE WHEN [SERIALNO4] IS NULL THEN '' ELSE [SERIALNO4] END,[ASSETCOLOR] , [BPKBISSUEDATE], [BPKB], [ASSETNOTES] FROM (SELECT [ASSETNAME], [ASSETCONDITION], [ASSETPRICE], [ASSETCOLOR], [BPKB], [BPKBISSUEDATE], [ASSETNOTES], [MANYEAR], [DPPERCENT], [DPAMOUNT], [SERIALNO1], [SERIALNO2], [SERIALNO3], [SERIALNO4], ASSET_CATEGORY_NAME, mastername.Code, REF_MASTER_NAME FROM ( select [ASSETNAME], [ASSETCONDITION], [ASSETPRICE], [ASSETCOLOR], [BPKB], [BPKBISSUEDATE], [ASSETNOTES], [MANYEAR], [DPPERCENT], [DPAMOUNT], [SERIALNO1], [SERIALNO2], [SERIALNO3], [SERIALNO4], ASSET_CATEGORY_NAME, [Code], value FROM ( SELECT  aa.FULL_ASSET_NAME AS [ASSETNAME], ASSET_CATEGORY_NAME, MR_ASSET_CONDITION_CODE AS [ASSETCONDITION], ASSET_PRICE_AMT AS [ASSETPRICE], aa.MR_ASSET_USAGE_CODE AS 'ASSET_USAGE', aa.COLOR AS [ASSETCOLOR], aa.TAX_CITY_ISSUER AS [BPKB], FORMAT(aa.TAX_ISSUE_DT, 'yyyy-MM-dd') AS [BPKBISSUEDATE], aa.ASSET_NOTES AS [ASSETNOTES], aa.MANUFACTURING_YEAR AS [MANYEAR], DOWN_PAYMENT_PRCNT AS [DPPERCENT], DOWN_PAYMENT_AMT AS [DPAMOUNT], aa.SERIAL_NO_1 AS [SERIALNO1], aa.SERIAL_NO_2 AS [SERIALNO2], aa.SERIAL_NO_3 AS [SERIALNO3], aa.SERIAL_NO_4 AS [SERIALNO4] FROM APP_ASSET aa WITH(NOLOCK) JOIN APP a WITH(NOLOCK) ON aa.APP_ID = a.APP_ID JOIN V_ASSET_CATEGORY vac WITH(NOLOCK) ON vac.ASSET_CATEGORY_CODE = aa.ASSET_CATEGORY_CODE WHERE APP_NO = '"+ appno +"') as Orig unpivot (value for [Code] in ([ASSET_USAGE]) )as unpiv) as mastername JOIN REF_MASTER_LOS rf WITH(NOLOCK) ON rf.REF_MASTER_Code = mastername.value WHERE rf.IS_ACTIVE = '1') AS ref  PIVOT (MAX(ref.REF_MASTER_NAME) for [Code] in ([ASSET_USAGE],[CUST_PERSONAL_RELATIONSHIP],[ID_TYPE])) as piv"), {  row ->

			ResultSetMetaData rsmd = row.getMetaData()
			colmcount = rsmd.getColumnCount()


			for(i = 0 ; i < colmcount ; i++){
				appdata = (row[i])
				listappdata.add(appdata)
			}
		})
		return listappdata
	}

	public checkAssetAttr (Sql instance, String appno){
		String appdata
		ArrayList <String> listappdata = new ArrayList<String>()
		instance.eachRow(("SELECT ATTR_VALUE FROM APP_ASSET_ATTR aaa WITH(NOLOCK) JOIN APP_ASSET aa WITH(NOLOCK) ON aaa.APP_ASSET_ID = aa.APP_ASSET_ID JOIN APP a WITH(NOLOCK) ON a.APP_ID = aa.APP_ID WHERE APP_NO = '"+ appno +"'"), {  row ->

			ResultSetMetaData rsmd = row.getMetaData()
			colmcount = rsmd.getColumnCount()


			for(i = 0 ; i < colmcount ; i++){
				appdata = (row[i])
				listappdata.add(appdata)
			}
		})
		return listappdata
	}

	public checkAssetAccessories (Sql instance, String appno){
		String appdata
		ArrayList <String> listappdata = new ArrayList<String>()
		instance.eachRow(("SELECT aaa.SUPPL_NAME, ASSET_ACCESSORY_NAME, aaa.ACCESSORY_PRICE_AMT, aaa.DOWN_PAYMENT_PRCNT, aaa.DOWN_PAYMENT_AMT, aaa.ACCESSORY_NOTES FROM APP_ASSET_ACCESSORY aaa WITH(NOLOCK) JOIN APP_ASSET aa WITH(NOLOCK) ON aaa.APP_ASSET_ID = aa.APP_ASSET_ID JOIN APP a WITH(NOLOCK) ON a.APP_ID = aa.APP_ID WHERE APP_NO = '"+ appno +"'"), {  row ->

			ResultSetMetaData rsmd = row.getMetaData()
			colmcount = rsmd.getColumnCount()


			for(i = 0 ; i < colmcount ; i++){
				appdata = (row[i])
				listappdata.add(appdata)
			}
		})
		return listappdata
	}

	public checkAssetUser (Sql instance, String appno){
		String appdata
		ArrayList <String> listappdata = new ArrayList<String>()
		instance.eachRow(("SELECT USER_NAME, REF_MASTER_NAME FROM APP_COLLATERAL_REGISTRATION acr WITH(NOLOCK) JOIN APP_COLLATERAL ac WITH(NOLOCK) ON ac.APP_COLLATERAL_ID = acr.APP_COLLATERAL_ID JOIN APP a WITH(NOLOCK) ON a.APP_ID = ac.APP_ID JOIN REF_MASTER_LOS rml WITH(NOLOCK) ON rml.REF_MASTER_CODE = MR_USER_RELATIONSHIP_CODE WHERE APP_NO = '"+ appno +"' AND REF_MASTER_TYPE_CODE = 'CUST_PERSONAL_RELATIONSHIP'"), {  row ->

			ResultSetMetaData rsmd = row.getMetaData()
			colmcount = rsmd.getColumnCount()


			for(i = 0 ; i < colmcount ; i++){
				appdata = (row[i])
				listappdata.add(appdata)
			}
		})
		return listappdata
	}

	public checkAssetOwner (Sql instance, String appno){
		String appdata
		ArrayList <String> listappdata = new ArrayList<String>()
		instance.eachRow(("SELECT OWNER_NAME, OWNER_ADDR, REF_MASTER_NAME, OWNER_AREA_CODE_4 + ' / ' + OWNER_AREA_CODE_3, MR_ID_TYPE_CODE, OWNER_AREA_CODE_2, OWNER_ID_NO, OWNER_AREA_CODE_1, OWNER_MOBILE_PHN_NO, OWNER_CITY, OWNER_PROFESSION_CODE, OWNER_ZIPCODE FROM APP_COLLATERAL_REGISTRATION acr WITH(NOLOCK) JOIN APP_COLLATERAL ac WITH(NOLOCK) ON ac.APP_COLLATERAL_ID = acr.APP_COLLATERAL_ID JOIN APP a WITH(NOLOCK) ON a.APP_ID = ac.APP_ID JOIN REF_MASTER_LOS rml WITH(NOLOCK) ON rml.REF_MASTER_CODE = MR_OWNER_RELATIONSHIP_CODE WHERE APP_NO = '"+appno+"' GROUP BY OWNER_NAME, OWNER_ADDR, REF_MASTER_NAME, OWNER_AREA_CODE_4 + ' / ' + OWNER_AREA_CODE_3, MR_ID_TYPE_CODE, OWNER_AREA_CODE_2, OWNER_ID_NO, OWNER_AREA_CODE_1, OWNER_MOBILE_PHN_NO, OWNER_CITY, OWNER_PROFESSION_CODE, OWNER_ZIPCODE"), {  row ->

			ResultSetMetaData rsmd = row.getMetaData()
			colmcount = rsmd.getColumnCount()


			for(i = 0 ; i < colmcount ; i++){
				appdata = (row[i])
				listappdata.add(appdata)
			}
		})
		return listappdata
	}

	public checkAssetLocation (Sql instance, String appno){
		String appdata
		ArrayList <String> listappdata = new ArrayList<String>()
		instance.eachRow(("SELECT TOP 1 LOCATION_ADDR, LOCATION_AREA_CODE_4 + ' / ' + LOCATION_AREA_CODE_3, LOCATION_AREA_CODE_2, LOCATION_AREA_CODE_1, LOCATION_CITY, LOCATION_ZIPCODE FROM APP_COLLATERAL_REGISTRATION acr WITH(NOLOCK) JOIN APP_COLLATERAL ac WITH(NOLOCK) ON ac.APP_COLLATERAL_ID = acr.APP_COLLATERAL_ID JOIN APP a WITH(NOLOCK) ON a.APP_ID = ac.APP_ID JOIN REF_MASTER_LOS rml WITH(NOLOCK) ON rml.REF_MASTER_CODE = MR_USER_RELATIONSHIP_CODE WHERE APP_NO = '"+ appno +"'"), {  row ->

			ResultSetMetaData rsmd = row.getMetaData()
			colmcount = rsmd.getColumnCount()


			for(i = 0 ; i < colmcount ; i++){
				appdata = (row[i])
				listappdata.add(appdata)
			}
		})
		return listappdata
	}


	public checkAssetCollateral (Sql instance, String appno){
		String appdata
		ArrayList <String> listappdata = new ArrayList<String>()
		instance.eachRow(("SELECT CASE WHEN ac.APP_COLLATERAL_NO IS NULL THEN '' ELSE ac.APP_COLLATERAL_NO END, ac.FULL_ASSET_NAME, vat.ASSET_TYPE_NAME, aa.ASSET_PRICE_AMT, CASE WHEN ac.SERIAL_NO_1 IS NULL THEN '' ELSE ac.SERIAL_NO_1 END, CASE WHEN ac.SERIAL_NO_2 IS NULL THEN '' ELSE ac.SERIAL_NO_2 END,  CASE WHEN ac.SERIAL_NO_3 IS NULL THEN '' ELSE ac.SERIAL_NO_3 END, OWNER_NAME FROM APP_COLLATERAL_REGISTRATION acr WITH(NOLOCK) JOIN APP_COLLATERAL ac WITH(NOLOCK) ON ac.APP_COLLATERAL_ID = acr.APP_COLLATERAL_ID JOIN APP a WITH(NOLOCK) ON a.APP_ID = ac.APP_ID JOIN REF_MASTER_LOS rml WITH(NOLOCK) ON rml.REF_MASTER_CODE = MR_USER_RELATIONSHIP_CODE JOIN APP_ASSET aa WITH(NOLOCK) ON aa.APP_ASSET_ID = ac.APP_ASSET_ID JOIN V_ASSET_TYPE vat WITH(NOLOCK) ON vat.ASSET_TYPE_CODE = ac.ASSET_TYPE_CODE WHERE APP_NO = '"+appno+"' GROUP BY ac.APP_COLLATERAL_NO, ac.FULL_ASSET_NAME, vat.ASSET_TYPE_NAME, aa.ASSET_PRICE_AMT, ac.SERIAL_NO_1, ac.SERIAL_NO_2, ac.SERIAL_NO_3, OWNER_NAME"), {  row ->

			ResultSetMetaData rsmd = row.getMetaData()
			colmcount = rsmd.getColumnCount()


			for(i = 0 ; i < colmcount ; i++){
				appdata = (row[i])
				listappdata.add(appdata)
			}
		})
		return listappdata
	}

	public checkTermandCondition (Sql instance, String appno){
		String appdata
		ArrayList <String> listappdata = new ArrayList<String>()
		instance.eachRow(("SELECT rtl.TC_NAME, PRIOR_TO, CASE WHEN CAST(IS_CHECKED as varchar(25)) = '0' THEN 'NO' WHEN CAST(IS_CHECKED as varchar(25)) = '1' THEN 'YES' ELSE CAST(IS_CHECKED as varchar(25)) END, CASE WHEN CAST(IS_WAIVED as varchar(25)) = '0' THEN 'NO' WHEN CAST(IS_WAIVED as varchar(25)) = '1' THEN 'YES' ELSE CAST(IS_WAIVED as varchar(25)) END, CASE WHEN FORMAT(PROMISED_DT, 'dd-MMM-yyyy') IS NULL THEN '-' ELSE FORMAT(PROMISED_DT, 'dd-MMM-yyyy') END, CASE WHEN FORMAT(EXPIRED_DT, 'dd-MMM-yyyy') IS NULL THEN '-' ELSE FORMAT(EXPIRED_DT, 'dd-MMM-yyyy') END, CASE WHEN at.NOTES IS NULL THEN '' ELSE at.NOTES END FROM APP_TC at WITH(NOLOCK)JOIN APP a WITH(NOLOCK) ON a.APP_ID = at.APP_ID RIGHT JOIN REF_TC_LOS rtl WITH(NOLOCK) ON rtl.TC_CODE = at.TC_CODE WHERE APP_NO = '"+ appno +"'"), {  row ->

			ResultSetMetaData rsmd = row.getMetaData()
			colmcount = rsmd.getColumnCount()


			for(i = 0 ; i < colmcount ; i++){
				appdata = (row[i])
				listappdata.add(appdata)
			}
		})
		return listappdata
	}

	public checkReferantor (Sql instance, String appno){
		String refdata
		ArrayList <String> listrefdata = new ArrayList<String>()
		instance.eachRow(("SELECT CASE WHEN MR_REFERANTOR_TYPE = 'AGENCY_PERSONAL' OR MR_REFERANTOR_TYPE = 'AGENCY_COMPANY' THEN 'AGENCY' WHEN MR_REFERANTOR_TYPE = 'CUSTOMER_PERSONAL' OR MR_REFERANTOR_TYPE = 'CUSTOMER_COMPANY' THEN 'CUSTOMER' ELSE 'MULTIFINANCE EMPLOYEE' END AS [REFERANTOR_CATEGORY], RTRIM(ar.REFERANTOR_NAME) as [REFERANTOR_NAME], ref_master_name as [MR_REFERANTOR_TYPE], d.BANK_NAME + ' - ' + ar.BANK_ACC_NAME + ' - ' + ar.BANK_ACC_NO AS 'BANK_ACCOUNT', ar.MR_TAX_CALC_METHOD, CASE WHEN IS_USE_VAT = '0' THEN 'NO' WHEN IS_USE_VAT = '1' THEN 'YES' END AS [IS_USE_VAT] FROM APP a WITH(NOLOCK) JOIN APP_REFERANTOR ar WITH(NOLOCK) ON a.APP_ID = ar.APP_ID JOIN REF_BANK_LOS d WITH(NOLOCK) ON ar.REF_BANK_CODE = d.BANK_CODE JOIN REF_MASTER_LOS rf WITH(NOLOCK) ON rf.ref_master_code = mr_referantor_type WHERE APP_NO = '"+appno+"' AND REF_MASTER_TYPE_CODE = 'REFERANTOR_TYPE' ORDER BY APP_REFERANTOR_ID"), {  row ->

			ResultSetMetaData rsmd = row.getMetaData()
			colmcount = rsmd.getColumnCount()


			for(i = 0 ; i < colmcount ; i++){
				refdata = (row[i])
				listrefdata.add(refdata)
			}
		})
		return listrefdata
	}

	public checkMOInfo (Sql instance, String appno){
		String modata
		ArrayList <String> listmodata = new ArrayList<String>()
		instance.eachRow(("SELECT CASE WHEN [SPV] IS NULL THEN '-' ELSE [SPV] END,[OFFICER] , CASE WHEN SALES_NOTES IS NULL THEN '-' ELSE SALES_NOTES END, CASE WHEN ref_master_name IS NULL THEN '-' ELSE ref_master_name END as [SALES_RECOM] FROM (SELECT sales_notes, MR_SALES_RECOMMEND_CODE, mastername.Code, rel.emp_name FROM ( select sales_notes, MR_SALES_RECOMMEND_CODE, [Code], value FROM ( select sales_head_no as [SPV], sales_officer_no as [OFFICER], sales_notes, MR_SALES_RECOMMEND_CODE from app a WHERE APP_NO = '"+appno+"') as Orig unpivot (value for [Code] in ([SPV],[OFFICER]) )as unpiv) as mastername left JOIN REF_USER_LOS rul WITH(NOLOCK) ON rul.emp_no = mastername.value left join ref_emp_los rel with(nolock) on rel.emp_no = rul.emp_no) AS ref PIVOT(MAX(ref.emp_name) for [Code] in ([SPV],[OFFICER])) as piv left join ref_master_los rm with(nolock) on piv.MR_SALES_RECOMMEND_CODE = rm.REF_MASTER_CODE and rm.ref_master_type_code = 'SLS_RECOM'"), {  row ->

			ResultSetMetaData rsmd = row.getMetaData()
			colmcount = rsmd.getColumnCount()


			for(i = 0 ; i < colmcount ; i++){
				modata = (row[i])
				listmodata.add(modata)
			}
		})
		return listmodata
	}

	public checkAppInfoAndRestructuringData(Sql instance, String appno){
		String appdata
		ArrayList <String> listappdata = new ArrayList<String>()
		instance.eachRow(("SELECT [APPSRCNAME] , [FIRST_INST_TYPE] , [PAYMENTFREQ] , [TENOR] ,[NUM_OF_INST], [INTRSTTYPE] , CASE WHEN [FLOATINGPERIOD] ='' THEN '-' ELSE [FLOATINGPERIOD] END , [INST_SCHM] , [WOP] , [CUST_NOTIF_OPT] , [CHARACTERISTIC_OF_CREDIT] , CASE WHEN [PREVAGGRMT] IS NULL THEN '-' ELSE [PREVAGGRMT] END , CASE WHEN [WAY_OF_RESTRUCTURE] IS NULL THEN '-' ELSE [WAY_OF_RESTRUCTURE] END , [SLIKECO], CASE WHEN [MOU_NO] IS NULL THEN '-' ELSE [MOU_NO] END FROM (SELECT [APPSRCNAME], [PAYMENTFREQ], [TENOR],[NUM_OF_INST],[FLOATINGPERIOD], [PREVAGGRMT], [MOU_NO], mastername.Code, REF_MASTER_NAME FROM ( select [APPSRCNAME], [PAYMENTFREQ], [TENOR],[NUM_OF_INST], [FLOATINGPERIOD], [PREVAGGRMT], [MOU_NO], [Code], value FROM ( SELECT ras.APP_SRC_NAME AS [APPSRCNAME], MR_FIRST_INST_TYPE_CODE AS 'FIRST_INST_TYPE', PAY_FREQ_CODE AS [PAYMENTFREQ], TENOR AS [TENOR],NUM_OF_INST AS [NUM_OF_INST], CAST(acpfd.INTEREST_TYPE as nvarchar(50)) AS 'INTRSTTYPE', FLOATING_PERIOD_CODE AS [FLOATINGPERIOD], MR_INST_SCHEME_CODE AS 'INST_SCHM', MR_WOP_CODE AS 'WOP', MR_CUST_NOTIFY_OPT_CODE AS 'CUST_NOTIF_OPT', MR_CHARACTERISTIC_OF_CREDIT_CODE AS 'CHARACTERISTIC_OF_CREDIT', agr.PREV_AGRMNT_NO AS [PREVAGGRMT], MR_WAY_OF_RESTRUCTURE_CODE AS 'WAY_OF_RESTRUCTURE', agr.MR_SLIK_SEC_ECO_CODE AS [SLIKECO], mou_cust_no as [MOU_NO] FROM APP a WITH(NOLOCK) JOIN APP_CUST ac WITH(NOLOCK) ON a.APP_ID = ac.APP_ID JOIN APP_GOVERMENT_REGULATION agr WITH(NOLOCK) ON agr.APP_ID = a.APP_ID JOIN APP_FIN_DATA acpfd WITH(NOLOCK) ON acpfd.APP_ID = ac.APP_ID JOIN REF_APP_SRC ras WITH(NOLOCK) ON ras.APP_SRC_CODE = a.MR_APP_SOURCE_CODE left JOIN MOU_CUST mc with(nolock) on mc.mou_cust_id = a.mou_cust_id WHERE APP_NO = '"+appno+"') as Orig unpivot (value for [Code] in ([FIRST_INST_TYPE],[WOP],[INTRSTTYPE],[INST_SCHM],[CUST_NOTIF_OPT],[CHARACTERISTIC_OF_CREDIT],[WAY_OF_RESTRUCTURE],[SLIKECO]) )as unpiv) as mastername JOIN REF_MASTER_LOS rf WITH(NOLOCK) ON rf.REF_MASTER_Code = mastername.value WHERE rf.IS_ACTIVE = '1' AND rf.REF_MASTER_TYPE_CODE IN ('FIRST_INST_TYPE','WOP','INTRSTTYPE','INST_SCHM','CUST_NOTIF_OPT','CHARACTERISTIC_OF_CREDIT','WAY_OF_RESTRUCTURE','SLIK_SEC_ECO')) AS ref PIVOT(MAX(ref.REF_MASTER_NAME) for [Code] in ([FIRST_INST_TYPE],[WOP],[INTRSTTYPE],[INST_SCHM],[CUST_NOTIF_OPT],[CHARACTERISTIC_OF_CREDIT],[WAY_OF_RESTRUCTURE],[SLIKECO])) as piv"), {  row ->

			ResultSetMetaData rsmd = row.getMetaData()
			colmcount = rsmd.getColumnCount()


			for(i = 0 ; i < colmcount ; i++){
				appdata = (row[i])
				listappdata.add(appdata)
			}
		})
		return listappdata
	}

	public checkApplicationAttribute(Sql instance, String appno){
		String attrdata
		ArrayList <String> listattrdata = new ArrayList<String>()
		instance.eachRow(("SELECT ATTR_VALUE FROM APP_ATTR_CONTENT aac WITH(NOLOCK) JOIN APP a WITH(NOLOCK) ON a.APP_ID = aac.APP_ID WHERE APP_NO = '"+appno+"'"), {  row ->

			ResultSetMetaData rsmd = row.getMetaData()
			colmcount = rsmd.getColumnCount()


			for(i = 0 ; i < colmcount ; i++){
				attrdata = (row[i])
				listattrdata.add(attrdata)
			}
		})
		return listattrdata
	}
}


