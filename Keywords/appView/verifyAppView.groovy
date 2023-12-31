package appView

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

public class verifyAppView {

	int colmcount
	int i

	//keyword check main info app view
	@Keyword
	public checkAppViewData (Sql instance, String appno){
		String appdata
		ArrayList <String> listappdata = new ArrayList<String>()
		instance.eachRow(("SELECT ISNULL(APP_NO,'-') AS APP_NO, ISNULL(ORI_OFFICE_NAME,'-') AS ORI_OFFICE_NAME, ISNULL(CUST_NO,'-') AS CUST_NO, ISNULL(CUST_NAME,'-') AS CUST_NAME, ISNULL(PROD_OFFERING_NAME,'-') AS PROD_OFFERING_NAME, ISNULL(TENOR,'-') AS TENOR, ISNULL(FULL_ASSET_NAME,'-') AS FULL_ASSET_NAME, ISNULL(rml.DESCR,'-') AS DESCR FROM APP_CUST ac WITH(NOLOCK) JOIN APP a WITH(NOLOCK) ON ac.APP_ID = a.APP_ID JOIN APP_ASSET aa WITH(NOLOCK) ON aa.APP_ID = ac.APP_ID JOIN FOUNDATION.dbo.REF_MASTER rml WITH(NOLOCK) ON rml.MASTER_CODE = MR_CUST_MODEL_CODE WHERE APP_NO = '"+ appno +"' AND IS_CUSTOMER = '1' AND rml.IS_ACTIVE = '1'"), {  row ->

			ResultSetMetaData rsmd = row.getMetaData()
			colmcount = rsmd.getColumnCount()


			for(i = 0 ; i < colmcount ; i++){
				appdata = (row[i])
				listappdata.add(appdata)
			}
		})
		return listappdata
	}

	//keyword check app view customer main company
	@Keyword
	public checkCustomerMainDataCompany (Sql instance, String appno, String name){
		String appdata
		ArrayList <String> listappdata = new ArrayList<String>()
		instance.eachRow(("SELECT CUST_NAME, ritl.INDUSTRY_TYPE_NAME, NUM_OF_EMP, CASE WHEN IS_AFFILIATE_WITH_MF = 0 THEN '-' END, CASE WHEN IS_VIP = 0 THEN 'NO' END, MR_CUST_MODEL_CODE, MR_COMPANY_TYPE_CODE, TAX_ID_NO, FORMAT(ESTABLISHMENT_DT, 'dd-MMM-yyyy') FROM APP_CUST_COMPANY acc WITH(NOLOCK) JOIN APP_CUST ac WITH(NOLOCK) ON acc.APP_CUST_ID = ac.APP_CUST_ID JOIN APP a WITH(NOLOCK) ON ac.APP_ID = a.APP_ID JOIN REF_INDUSTRY_TYPE_LOS ritl WITH(NOLOCK) ON ritl.INDUSTRY_TYPE_CODE = acc.INDUSTRY_TYPE_CODE WHERE APP_NO = '"+ appno +"' AND CUST_NAME = '"+ name +"'"), {  row ->

			ResultSetMetaData rsmd = row.getMetaData()
			colmcount = rsmd.getColumnCount()


			for(i = 0 ; i < colmcount ; i++){
				appdata = (row[i])
				listappdata.add(appdata)
			}
		})
		return listappdata
	}

	//keyword check app view customer main personal
	@Keyword
	public checkCustomerMainDataPersonal (Sql instance, String appno, String name){
		String appdata
		ArrayList <String> listappdata = new ArrayList<String>()
		instance.eachRow(("SELECT CUST_NAME, [SALUTATION], ISNULL([Custprefix],'-') AS [Cust_prefix], ISNULL([Custsuffix],'-') AS [Cust_suffix], MR_GENDER_CODE, [ID_TYPE] + ' / ' + ID_NO, MOTHER_MAIDEN_NAME, ISNULL(FORMAT(ID_EXPIRED_DT, 'dd-MMM-yyyy'),'-') AS [ID_EXPIRED_DATE], MR_MARITAL_STAT_CODE, BIRTH_PLACE + ' / ' + FORMAT(BIRTH_DT, 'dd-MMM-yyyy'), [NATIONALITY], CASE WHEN TAX_ID_NO IS NULL OR TAX_ID_NO = ' ' THEN '-' ELSE TAX_ID_NO END AS [TAX_ID_NO], [Countryname], MOBILE_PHN_NO_1, [EDUCATION], EMAIL_1, [RELIGION], CASE WHEN CAST([IS_VIP] as nvarchar(25)) = '0' THEN 'NO' WHEN CAST([IS_VIP] as nvarchar(25)) = '1' THEN 'YES' ELSE CAST([IS_VIP] as nvarchar(25)) END  FROM (SELECT [Custprefix], [IS_VIP], [Custsuffix], [Countryname], CUST_NAME, MR_GENDER_CODE, ID_NO, MOTHER_MAIDEN_NAME, ID_EXPIRED_DT, MR_MARITAL_STAT_CODE, BIRTH_PLACE, BIRTH_DT, TAX_ID_NO, MOBILE_PHN_NO_1, EMAIL_1, mastername.Code, DESCR FROM (select [Custprefix], [IS_VIP], [Custsuffix], [Countryname], CUST_NAME, MR_GENDER_CODE , ID_NO, MOTHER_MAIDEN_NAME, ID_EXPIRED_DT, MR_MARITAL_STAT_CODE, BIRTH_PLACE, BIRTH_DT, TAX_ID_NO, MOBILE_PHN_NO_1, EMAIL_1, [Code], value FROM (SELECT MR_EDUCATION_CODE AS 'EDUCATION', MR_NATIONALITY_CODE as 'NATIONALITY', MR_RELIGION_CODE as 'RELIGION', MR_SALUTATION_CODE as 'SALUTATION', acp.CUST_PREFIX_NAME as [Custprefix], ac.IS_VIP  as [IS_VIP], acp.CUST_SUFFIX_NAME as [Custsuffix], rc.COUNTRY_NAME as [Countryname], CUST_NAME, MR_GENDER_CODE, MR_ID_TYPE_CODE as 'ID_TYPE', ID_NO, MOTHER_MAIDEN_NAME, ID_EXPIRED_DT, MR_MARITAL_STAT_CODE, BIRTH_PLACE, BIRTH_DT, TAX_ID_NO, MOBILE_PHN_NO_1, EMAIL_1  FROM APP_CUST_PERSONAL acp WITH(NOLOCK) join app_cust ac WITH(NOLOCK) ON ac.app_cust_id = acp.APP_CUST_ID JOIN APP a WITH(NOLOCK) ON ac.APP_ID = a.APP_ID JOIN V_REF_COUNTRY rc WITH(NOLOCK) ON rc.COUNTRY_CODE = acp.NATIONALITY_COUNTRY_CODE WHERE ac.CUST_NAME = '"+ name +"' and app_no ='"+ appno +"') as Orig unpivot (value for [Code] in ([ID_TYPE],[EDUCATION],[NATIONALITY],[RELIGION],[SALUTATION]) )as unpiv) as mastername JOIN FOUNDATION.dbo.REF_MASTER rf WITH(NOLOCK) ON rf.MASTER_CODE = mastername.value WHERE rf.IS_ACTIVE = '1') AS ref PIVOT (MAX(ref.DESCR) for [Code] in ([ID_TYPE],[EDUCATION],[NATIONALITY],[RELIGION],[SALUTATION])) as piv"), {  row ->

			ResultSetMetaData rsmd = row.getMetaData()
			colmcount = rsmd.getColumnCount()

			for(i = 0 ; i < colmcount ; i++){
				appdata = (row[i])
				listappdata.add(appdata)
			}
		})
		return listappdata
	}

	//keyword check app view check addr
	@Keyword
	public checkAddrData (Sql instance, String appno, String name){
		String appdata
		ArrayList <String> listappdata = new ArrayList<String>()
		instance.eachRow(("SELECT [CUST_ADDR_TYPE], [ADDR], ISNULL([BUILDING_OWNERSHIP],'-') AS [OWNERSHIP], CASE WHEN PHN_EXT_1 LIKE '%[a-zA-Z0-9]%' THEN [PHN1] + ' - ' + PHN_EXT_1 ELSE [PHN1] + ' -' END, CASE WHEN PHN_EXT_2 LIKE '%[a-zA-Z0-9]%' THEN [PHN2] + ' - ' + PHN_EXT_2 ELSE [PHN2] + ' -' END  FROM (SELECT APP_CUST_ADDR_ID, [ADDR], [PHN1], [PHN2], PHN_EXT_1, PHN_EXT_2, mastername.Code, DESCR FROM (select PHN_EXT_1, PHN_EXT_2, APP_CUST_ADDR_ID, [ADDR], [PHN1], [PHN2], [Code], value FROM (SELECT PHN_EXT_2, APP_CUST_ADDR_ID, MR_CUST_ADDR_TYPE_CODE AS 'CUST_ADDR_TYPE', CASE WHEN aca.ADDR + ' RT/RW ' + aca.AREA_CODE_4 + '/' + aca.AREA_CODE_3 + ' ' + aca.AREA_CODE_2 + ' ' + aca.AREA_CODE_1 + ' ' + aca.CITY + ' ' + aca.ZIPCODE IS NULL OR aca.ADDR NOT LIKE '%[a-zA-Z0-9]%' THEN 'RT/RW /' ELSE aca.ADDR + ' RT/RW ' + aca.AREA_CODE_4 + '/' + aca.AREA_CODE_3 + ' ' + aca.AREA_CODE_2 + ' ' + aca.AREA_CODE_1 + ' ' + aca.CITY + ' ' + aca.ZIPCODE END AS [ADDR], MR_HOUSE_OWNERSHIP_CODE  AS 'BUILDING_OWNERSHIP',  aca.PHN_EXT_1 , CASE WHEN aca.PHN_AREA_1 + ' - ' + aca.PHN_1 IS NULL OR aca.PHN_AREA_1 NOT LIKE '%[a-zA-Z0-9]%' OR aca.PHN_1 NOT LIKE '%[a-zA-Z0-9]%' THEN '-' ELSE aca.PHN_AREA_1 + ' - ' + aca.PHN_1 END AS [PHN1], CASE WHEN aca.PHN_AREA_2 + ' - ' + aca.PHN_2 IS NULL OR aca.PHN_AREA_2 NOT LIKE '%[a-zA-Z0-9]%' OR aca.PHN_2 NOT LIKE '%[a-zA-Z0-9]%' THEN '-' ELSE aca.PHN_AREA_2 + ' - ' + aca.PHN_2 END AS [PHN2] FROM APP_CUST_ADDR aca WITH(NOLOCK) JOIN APP_CUST ac WITH(NOLOCK) ON aca.APP_CUST_ID = ac.APP_CUST_ID JOIN APP a WITH(NOLOCK) ON a.APP_ID = ac.APP_ID JOIN FOUNDATION.dbo.REF_MASTER rml WITH(NOLOCK) ON rml.MASTER_CODE = aca.MR_CUST_ADDR_TYPE_CODE WHERE a.APP_NO = '"+ appno +"' AND CUST_NAME = '"+ name +"') as orig unpivot (value for [Code] in ([CUST_ADDR_TYPE],[BUILDING_OWNERSHIP]) )as unpiv) as mastername JOIN FOUNDATION.dbo.REF_MASTER rf WITH(NOLOCK) ON rf.MASTER_CODE = mastername.value WHERE rf.IS_ACTIVE = '1' ) AS ref PIVOT (MAX(ref.DESCR) for [Code] in ([CUST_ADDR_TYPE],[BUILDING_OWNERSHIP])) as piv ORDER BY piv.APP_CUST_ADDR_ID"), {  row ->

			ResultSetMetaData rsmd = row.getMetaData()
			colmcount = rsmd.getColumnCount()

			for(i = 0 ; i < colmcount ; i++){
				appdata = (row[i])
				listappdata.add(appdata)
			}
		})
		return listappdata
	}

	//keyword check app view MS data
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

	//keyword check app view family data
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

	//keyword check app view contact person data
	@Keyword
	public checkContactPersonData (Sql instance, String appno, String name){
		String appdata
		ArrayList <String> listappdata = new ArrayList<String>()
		instance.eachRow(("SELECT [ContactPersonName], [GENDER], [JobTitle], [JOB_POSITION], ISNULL([ID_TYPE],'-') AS [ID_TYPE], CASE WHEN [BirthPlace] NOT LIKE '%[a-zA-Z0-9]%' THEN '-' ELSE [BirthPlace] END, CASE WHEN [ContactIDNo] NOT LIKE '%[a-zA-Z0-9]%' OR [ContactIDNo] IS NULL THEN '-' ELSE [ContactIDNo] END AS [CONTACT_ID_NO], ISNULL([BirthDate],'-') AS [BIRTH_DATE], ISNULL([ExpiredDate],'-') AS [EXPIRED_DATE], ISNULL([CUST_COMPANY_RELATIONSHIP],'-') AS [RELATIONSHIP_COMPANY], CASE WHEN [MobilePhn1] IS NULL OR [MobilePhn1] NOT LIKE '%[a-zA-Z0-9]%' THEN '-' ELSE [MobilePhn1] END, CASE WHEN [Mobilephn2] IS NULL OR [MobilePhn2] NOT LIKE '%[a-zA-Z0-9]%' THEN '-' ELSE [Mobilephn2] END, CASE WHEN [Email1] IS NULL OR [Email1] NOT LIKE '%[a-zA-Z0-9]%' THEN '-' ELSE [Email1] END, CASE WHEN [Email2] IS NULL OR [Email2] NOT LIKE '%[a-zA-Z0-9]%' THEN '-' ELSE [Email2] END FROM (SELECT [ContactPersonName], [JobTitle], [ContactIDNo], [ExpiredDate], [MobilePhn1], [Email1], [BirthPlace], [BirthDate], [Mobilephn2], [Email2], mastername.Code, DESCR FROM (select [ContactPersonName], [JobTitle], [ContactIDNo], [ExpiredDate], [MobilePhn1], [Email1], [BirthPlace], [BirthDate], [Mobilephn2], [Email2], [Code], value FROM (SELECT acccp.CONTACT_PERSON_NAME as [ContactPersonName], acccp.JOB_TITLE_NAME as [JobTitle], acccp.MR_ID_TYPE_CODE as 'ID_TYPE', acccp.ID_NO as [ContactIDNo], FORMAT(acccp.ID_EXPIRED_DT, 'MM/dd/yyyy') as [ExpiredDate], acccp.MOBILE_PHN_NO_1 as [MobilePhn1], acccp.EMAIL_1 as [Email1], acccp.MR_JOB_POSITION_CODE as 'JOB_POSITION', acccp.BIRTH_PLACE as [BirthPlace], FORMAT(acccp.BIRTH_DT, 'yyyy-MM-dd') as [BirthDate], acccp.MR_CUST_RELATIONSHIP_CODE as 'CUST_COMPANY_RELATIONSHIP', acccp.MOBILE_PHN_NO_2 as [Mobilephn2], acccp.EMAIL_2 as [Email2], MR_GENDER_CODE as 'GENDER' FROM APP_CUST_COMPANY_CONTACT_PERSON acccp WITH(NOLOCK) JOIN APP_CUST_COMPANY acc WITH(NOLOCK) ON acc.APP_CUST_COMPANY_ID = acccp.APP_CUST_COMPANY_ID JOIN APP_CUST ac WITH(NOLOCK) ON ac.APP_CUST_ID = acc.APP_CUST_ID JOIN APP a WITH(NOLOCK) ON a.APP_ID = ac.APP_ID JOIN APP_CUST_ADDR aca ON aca.APP_CUST_ID = ac.APP_CUST_ID WHERE a.APP_NO = '"+ appno +"' AND CUST_NAME = '"+ name +"' AND aca.MR_CUST_ADDR_TYPE_CODE = 'COMPANY') as orig unpivot (value for [Code] in ([ID_TYPE],[CUST_COMPANY_RELATIONSHIP],[JOB_POSITION],[GENDER]) )as unpiv) as mastername JOIN FOUNDATION.dbo.REF_MASTER rf on rf.MASTER_CODE = mastername.value WHERE rf.IS_ACTIVE = '1') AS ref PIVOT (MAX(ref.DESCR) for [Code] in ([ID_TYPE],[CUST_COMPANY_RELATIONSHIP],[JOB_POSITION],[GENDER])) as piv"), {  row ->

			ResultSetMetaData rsmd = row.getMetaData()
			colmcount = rsmd.getColumnCount()


			for(i = 0 ; i < colmcount ; i++){
				appdata = (row[i])
				listappdata.add(appdata)
			}
		})
		return listappdata
	}

	//keyword check app emergency contact data
	@Keyword
	public checkEmergencyContactData (Sql instance, String appno, String name){
		String appdata
		ArrayList <String> listappdata = new ArrayList<String>()
		instance.eachRow(("SELECT ace.CONTACT_PERSON_NAME, ace.MR_GENDER_CODE, rml.DESCR, CASE WHEN ace.BIRTH_PLACE NOT LIKE '%[a-zA-Z0-9]%' THEN '-' ELSE ace.BIRTH_PLACE END , ace.ID_NO,  CASE WHEN FORMAT(ace.BIRTH_DT, 'dd-MMM-yyyy') NOT LIKE '%[a-zA-Z0-9]%' OR FORMAT(ace.BIRTH_DT, 'dd-MMM-yyyy') IS NULL THEN '-' ELSE FORMAT(ace.BIRTH_DT, 'dd-MMM-yyyy') END, ISNULL(FORMAT(ace.ID_EXPIRED_DT, 'dd-MMM-yyyy'),'-') AS [ID_EXP_DATE], ace.MR_CUST_RELATIONSHIP_CODE, CASE WHEN ace.MOBILE_PHN_NO_1 IS NULL OR ace.MOBILE_PHN_NO_1 NOT LIKE '%[a-zA-Z0-9]%' THEN '-' ELSE ace.MOBILE_PHN_NO_1 END AS [MOBILE_PHONE1], CASE WHEN ace.MOBILE_PHN_NO_2 IS NULL OR ace.MOBILE_PHN_NO_2 NOT LIKE '%[a-zA-Z0-9]%' THEN '-' ELSE ace.MOBILE_PHN_NO_2 END AS [MOBILE_PHONE2], ace.EMAIL, CASE WHEN ace.PHN_1 IS NULL OR ace.PHN_1 NOT LIKE '%[a-zA-Z0-9]%' THEN '-' ELSE ace.PHN_1 END AS [PHONE1], CASE WHEN ace.PHN_2 IS NULL OR ace.PHN_2 NOT LIKE '%[a-zA-Z0-9]%' THEN '-' ELSE ace.PHN_2 END AS [PHONE2], CASE WHEN ace.PHN_3 IS NULL OR ace.PHN_3 NOT LIKE '%[a-zA-Z0-9]%' THEN '-' ELSE ace.PHN_3 END AS [PHONE3] FROM APP_CUST_PERSONAL acp JOIN APP_CUST ac WITH(NOLOCK) ON acp.APP_CUST_ID = ac.APP_CUST_ID JOIN APP a WITH(NOLOCK) ON a.APP_ID = ac.APP_ID JOIN APP_CUST_EMRGNC_CNTCT ace WITH(NOLOCK) ON ace.APP_CUST_ID = acp.APP_CUST_ID JOIN FOUNDATION.dbo.REF_MASTER rml WITH(NOLOCK) ON rml.MASTER_CODE = ace.MR_ID_TYPE_CODE WHERE a.APP_NO = '"+ appno +"' AND CUST_NAME = '"+ name +"' AND rml.REF_MASTER_TYPE_CODE = 'ID_TYPE'"), {  row ->

			ResultSetMetaData rsmd = row.getMetaData()
			colmcount = rsmd.getColumnCount()

			for(i = 0 ; i < colmcount ; i++){
				appdata = (row[i])
				listappdata.add(appdata)
			}
		})
		return listappdata
	}

	//keyword check app view financial data
	@Keyword
	public checkFinancialData (Sql instance, String appno, String name){
		String appdata
		ArrayList <String> listappdata = new ArrayList<String>()
		instance.eachRow(("SELECT FORMAT(DATE_AS_OF, 'dd-MMM-yyyy') FROM APP_CUST_COMPANY_FIN_DATA accfd WITH(NOLOCK) JOIN APP_CUST ac WITH(NOLOCK) ON accfd.APP_CUST_ID = ac.APP_CUST_ID JOIN APP a WITH(NOLOCK) ON a.APP_ID = ac.APP_ID WHERE a.APP_NO = '"+ appno +"' AND CUST_NAME = '"+ name +"' ORDER BY DATE_AS_OF DESC"), {  row ->

			ResultSetMetaData rsmd = row.getMetaData()
			colmcount = rsmd.getColumnCount()


			for(i = 0 ; i < colmcount ; i++){
				appdata = (row[i])
				listappdata.add(appdata)
			}
		})
		return listappdata
	}

	//keyword check app view financial data personal
	@Keyword
	public checkFinancialDataPersonal (Sql instance, String appno, String name){
		String appdata
		ArrayList <String> listappdata = new ArrayList<String>()
		instance.eachRow(("SELECT FORMAT(DATE_AS_OF, 'dd-MMM-yyyy') FROM APP_CUST_PERSONAL_FIN_DATA acpfd WITH(NOLOCK) JOIN APP_CUST_PERSONAL acp WITH(NOLOCK) ON acp.APP_CUST_PERSONAL_ID = acpfd.APP_CUST_PERSONAL_ID JOIN APP_CUST ac WITH(NOLOCK) ON acp.APP_CUST_ID = ac.APP_CUST_ID JOIN APP a WITH(NOLOCK) ON a.APP_ID = ac.APP_ID WHERE a.APP_NO = '"+ appno +"' AND CUST_NAME = '"+ name +"'"), {  row ->

			ResultSetMetaData rsmd = row.getMetaData()
			colmcount = rsmd.getColumnCount()


			for(i = 0 ; i < colmcount ; i++){
				appdata = (row[i])
				listappdata.add(appdata)
			}
		})
		return listappdata
	}

	//keyword check app view financial attr data
	@Keyword
	public checkFinancialAttrData (Sql instance, String appno, String name){
		String appdata
		ArrayList <String> listappdata = new ArrayList<String>()
		instance.eachRow(("SELECT ATTR_VALUE  FROM APP_CUST_FIN_DATA_ATTR_CONTENT acfdac WITH(NOLOCK) JOIN APP_CUST ac WITH(NOLOCK) ON ac.APP_CUST_ID = acfdac.APP_CUST_ID JOIN APP a WITH(NOLOCK) ON a.APP_ID = ac.APP_ID WHERE a.APP_NO = '"+ appno +"' AND CUST_NAME = '"+ name +"'"), {  row ->

			ResultSetMetaData rsmd = row.getMetaData()
			colmcount = rsmd.getColumnCount()


			for(i = 0 ; i < colmcount ; i++){
				appdata = (row[i])
				listappdata.add(appdata)
			}
		})
		return listappdata
	}


	//keyword check app view bank acc
	@Keyword
	public checkBankAcc (Sql instance, String appno, String name){
		String appdata
		ArrayList <String> listappdata = new ArrayList<String>()
		instance.eachRow(("SELECT rbl.BANK_NAME + ' - ' + acba.BANK_ACC_NO + ' - ' + acba.BANK_ACC_NAME, acba.BANK_BRANCH , FORMAT(acba.BEG_BALANCE_AMT, 'N0')  FROM APP_CUST_BANK_ACC acba WITH(NOLOCK) JOIN APP_CUST ac WITH(NOLOCK) ON ac.APP_CUST_ID = acba.APP_CUST_ID JOIN APP a WITH(NOLOCK) ON a.APP_ID = ac.APP_ID JOIN REF_BANK_LOS rbl WITH(NOLOCK) ON rbl.BANK_CODE = acba.BANK_CODE WHERE a.APP_NO = '"+ appno +"' AND CUST_NAME = '"+ name +"'"), {  row ->

			ResultSetMetaData rsmd = row.getMetaData()
			colmcount = rsmd.getColumnCount()


			for(i = 0 ; i < colmcount ; i++){
				appdata = (row[i])
				listappdata.add(appdata)
			}
		})
		return listappdata
	}


	//keyword check app view bank statement ada / tidak
	@Keyword
	public checkFinancialCheckBankStatStoreData (Sql instance, String appno, String name, String accno){
		String bankstat
		instance.eachRow(("SELECT acba.IS_BANK_STMNT  FROM APP_CUST_BANK_ACC acba WITH(NOLOCK) JOIN APP_CUST_PERSONAL acp WITH(NOLOCK) ON acba.APP_CUST_ID = acp.APP_CUST_ID JOIN APP_CUST ac WITH(NOLOCK) ON ac.APP_CUST_ID = acba.APP_CUST_ID JOIN APP a WITH(NOLOCK) ON a.APP_ID = ac.APP_ID JOIN APP_CUST_BANK_STMNT acbs WITH(NOLOCK) ON acba.APP_CUST_BANK_ACC_ID = acbs.APP_CUST_BANK_ACC_ID WHERE a.APP_NO = '"+ appno +"' AND ac.CUST_NAME = '"+ name +"' AND acba.BANK_ACC_NO = '"+ accno +"' GROUP BY acba.IS_BANK_STMNT"), {  row ->

			bankstat = (row[0])
		})
		return bankstat
	}

	//keyword check app view bank satement
	@Keyword
	public checkBankStatData (Sql instance, String appno, String accno, String name){
		String appdata
		ArrayList <String> listappdata = new ArrayList<String>()
		instance.eachRow(("SELECT acbs.MONTH  ,acbs.YEAR , acbs.DEBIT_TRX_COUNT , FORMAT(acbs.DEBIT_AMT, 'N0') , acbs.CREDIT_TRX_COUNT , FORMAT(acbs.CREDIT_AMT, 'N0'), acbs.BALANCE_AMT FROM APP_CUST_BANK_ACC acba WITH(NOLOCK) JOIN APP_CUST ac ON ac.APP_CUST_ID = acba.APP_CUST_ID JOIN APP a WITH(NOLOCK) ON a.APP_ID = ac.APP_ID JOIN APP_CUST_BANK_STMNT acbs WITH(NOLOCK) ON acba.APP_CUST_BANK_ACC_ID = acbs.APP_CUST_BANK_ACC_ID WHERE a.APP_NO = '"+ appno +"' AND acba.BANK_ACC_NO = '"+ accno +"' AND CUST_NAME = '"+ name +"' "), {  row ->

			ResultSetMetaData rsmd = row.getMetaData()
			colmcount = rsmd.getColumnCount()


			for(i = 0 ; i < colmcount ; i++){
				appdata = (row[i])
				listappdata.add(appdata)
			}
		})
		return listappdata
	}

	//keyword check app view legal doc data
	@Keyword
	public checkLegalDocData (Sql instance, String appno, String name){
		String appdata
		ArrayList <String> listappdata = new ArrayList<String>()
		instance.eachRow(("SELECT MR_LEGAL_DOC_TYPE_CODE , DOC_NO , FORMAT(accld.DOC_DT, 'dd-MMMM-yyyy') , FORMAT(DOC_EXPIRED_DT, 'dd-MMMM-yyyy') , CASE WHEN NOTARY_NAME IS NULL OR NOTARY_NAME NOT LIKE '%[a-zA-Z0-9]%' THEN '' ELSE NOTARY_NAME END FROM APP_CUST_COMPANY_LEGAL_DOC accld WITH(NOLOCK) JOIN APP_CUST_COMPANY acc WITH(NOLOCK) ON acc.APP_CUST_COMPANY_ID = accld.APP_CUST_COMPANY_ID JOIN APP_CUST ac WITH(NOLOCK) ON ac.APP_CUST_ID = acc.APP_CUST_ID JOIN APP a WITH(NOLOCK) ON a.APP_ID = ac.APP_ID WHERE a.APP_NO = '"+ appno +"' AND CUST_NAME = '"+ name +"' ORDER BY accld.APP_CUST_COMPANY_LEGAL_DOC_ID"), {  row ->

			ResultSetMetaData rsmd = row.getMetaData()
			colmcount = rsmd.getColumnCount()


			for(i = 0 ; i < colmcount ; i++){
				appdata = (row[i])
				listappdata.add(appdata)
			}
		})
		return listappdata
	}

	//keyword check app view cust group data
	@Keyword
	public checkCustGroupData (Sql instance, String appno, String name){
		String appdata
		ArrayList <String> listappdata = new ArrayList<String>()
		instance.eachRow(("SELECT TOP 1 ac.CUST_NAME FROM APP_CUST ac WITH(NOLOCK), (SELECT acg.CUST_NO FROM APP_CUST_GRP acg WITH(NOLOCK) JOIN APP_CUST ac WITH(NOLOCK) ON ac.APP_CUST_ID = acg.APP_CUST_ID JOIN APP a WITH(NOLOCK) ON a.APP_ID = ac.APP_ID WHERE APP_NO = '"+ appno +"' AND CUST_NAME = '"+ name +"') as SUBQ  WHERE ac.CUST_NO = SUBQ.CUST_NO GROUP BY CUST_NAME"), {  row ->

			ResultSetMetaData rsmd = row.getMetaData()
			colmcount = rsmd.getColumnCount()


			for(i = 0 ; i < colmcount ; i++){
				appdata = (row[i])
				listappdata.add(appdata)
			}
		})
		return listappdata
	}

	//keyword check app view other information data
	@Keyword
	public checkOtherInfoData (Sql instance, String appno, String name){
		String appdata
		ArrayList <String> listappdata = new ArrayList<String>()
		instance.eachRow(("SELECT TOP 1 oth.LBPPMS_DEBT_GRP_LBPP_DESCR , oth.LBPPMS_BIZ_SCL_LBPP_DESCR , oth.LBPPMS_CNTRPRT_LBPP_DESCR , oth.LBPPMS_BIZ_SUSTAIN_LBPP_DESCR  FROM APP_CUST_OTHER_INFO oth WITH(NOLOCK) JOIN APP_CUST ac WITH(NOLOCK) ON ac.APP_CUST_ID = oth.APP_CUST_ID JOIN APP a WITH(NOLOCK) ON a.APP_ID = ac.APP_ID WHERE a.APP_NO = '"+ appno +"' AND CUST_NAME = '"+ name +"'"), {  row ->

			ResultSetMetaData rsmd = row.getMetaData()
			colmcount = rsmd.getColumnCount()


			for(i = 0 ; i < colmcount ; i++){
				appdata = (row[i])
				listappdata.add(appdata)
			}
		})
		return listappdata
	}

	//keyword check app view other attr data
	@Keyword
	public checkOtherAttrData (Sql instance, String appno, String name){
		String appdata
		ArrayList <String> listappdata = new ArrayList<String>()
		instance.eachRow(("SELECT ISNULL(DESCR,acac.attr_value) AS [OTHER_ATTR] FROM APP_CUST_ATTR_CONTENT acac WITH (NOLOCK) JOIN APP_CUST ac WITH (NOLOCK) ON ac.APP_CUST_ID = acac.APP_CUST_ID JOIN APP a WITH (NOLOCK) ON a.APP_ID = ac.APP_ID join ref_attr_los ral WITH (NOLOCK) on ral.ATTR_CODE = acac.ref_attr_code left join FOUNDATION.dbo.REF_MASTER rf with(nolock) on rf.MASTER_CODE = acac.attr_value and ref_master_type_code = ral.attr_value WHERE a.APP_NO = '"+ appno +"' AND CUST_NAME = '"+ name +"' group by acac.REF_ATTR_CODE, DESCR,acac.attr_value,APP_CUST_ATTR_CONTENT_ID order by APP_CUST_ATTR_CONTENT_ID"), {  row ->

			ResultSetMetaData rsmd = row.getMetaData()
			colmcount = rsmd.getColumnCount()


			for(i = 0 ; i < colmcount ; i++){
				appdata = (row[i])
				listappdata.add(appdata)
			}
		})
		return listappdata
	}

	//keyword check app view tab guarator
	@Keyword
	public checkGuarantor (Sql instance, String appno){
		String appdata
		ArrayList <String> listappdata = new ArrayList<String>()
		instance.eachRow(("SELECT CUST_NAME, MR_CUST_TYPE_CODE, DESCR, ISNULL(acccp.MOBILE_PHN_NO_1,'') AS [MOBILE_PHONE1] FROM APP_CUST ac WITH(NOLOCK) JOIN APP a WITH(NOLOCK) ON ac.APP_ID = a.APP_ID JOIN FOUNDATION.dbo.REF_MASTER rml WITH(NOLOCK) ON rml.MASTER_CODE = MR_CUST_RELATIONSHIP_CODE JOIN APP_CUST_COMPANY acc WITH(NOLOCK) ON acc.APP_CUST_ID = ac.APP_CUST_ID LEFT JOIN APP_CUST_COMPANY_CONTACT_PERSON acccp WITH(NOLOCK) ON acccp.APP_CUST_COMPANY_ID = acc.APP_CUST_COMPANY_ID WHERE APP_NO = '"+ appno +"' AND IS_GUARANTOR = '1' AND REF_MASTER_TYPE_CODE = 'CUST_COMPANY_RELATIONSHIP' UNION SELECT CUST_NAME, MR_CUST_TYPE_CODE, DESCR, MOBILE_PHN_NO_1 FROM APP_CUST ac WITH(NOLOCK) JOIN APP a WITH(NOLOCK) ON ac.APP_ID = a.APP_ID JOIN APP_CUST_PERSONAL acp WITH(NOLOCK) ON acp.APP_CUST_ID = ac.APP_CUST_ID JOIN FOUNDATION.dbo.REF_MASTER rml WITH(NOLOCK) ON rml.MASTER_CODE = MR_CUST_RELATIONSHIP_CODE WHERE APP_NO = '"+ appno +"' AND IS_GUARANTOR = '1' AND REF_MASTER_TYPE_CODE = 'CUST_RELATIONSHIP' order by MR_CUST_TYPE_CODE"), {  row ->

			ResultSetMetaData rsmd = row.getMetaData()
			colmcount = rsmd.getColumnCount()


			for(i = 0 ; i < colmcount ; i++){
				appdata = (row[i])
				listappdata.add(appdata)
			}
		})
		return listappdata
	}

	//keyword check app view asset supplier
	@Keyword
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

	//keyword check app view asset information
	@Keyword
	public checkAssetInformation (Sql instance, String appno){
		String appdata
		ArrayList <String> listappdata = new ArrayList<String>()
		instance.eachRow(("SELECT [ASSETNAME], ASSET_CATEGORY_NAME, [ASSETPRICE], CAST([DPPERCENT] as varchar(25)) + ' % | ' + CAST([DPAMOUNT] as varchar(25)), [ASSETCONDITION], [ASSET_USAGE], [MANYEAR], ISNULL([SERIALNO1],'') AS [SERIAL1], ISNULL([SERIALNO2],'') AS [SERIAL2], ISNULL([SERIALNO3],'') AS [SERIAL3], ISNULL([SERIALNO4],'') AS [SERIAL4],[ASSETCOLOR] , [BPKBISSUEDATE], [BPKB], [ASSETNOTES] FROM (SELECT [ASSETNAME], [ASSETCONDITION], [ASSETPRICE], [ASSETCOLOR], [BPKB], [BPKBISSUEDATE], [ASSETNOTES], [MANYEAR], [DPPERCENT], [DPAMOUNT], [SERIALNO1], [SERIALNO2], [SERIALNO3], [SERIALNO4], ASSET_CATEGORY_NAME, mastername.Code, DESCR FROM ( select [ASSETNAME], [ASSETCONDITION], [ASSETPRICE], [ASSETCOLOR], [BPKB], [BPKBISSUEDATE], [ASSETNOTES], [MANYEAR], [DPPERCENT], [DPAMOUNT], [SERIALNO1], [SERIALNO2], [SERIALNO3], [SERIALNO4], ASSET_CATEGORY_NAME, [Code], value FROM ( SELECT  aa.FULL_ASSET_NAME AS [ASSETNAME], ASSET_CATEGORY_NAME, MR_ASSET_CONDITION_CODE AS [ASSETCONDITION], ASSET_PRICE_AMT AS [ASSETPRICE], aa.MR_ASSET_USAGE_CODE AS 'ASSET_USAGE', aa.COLOR AS [ASSETCOLOR], aa.TAX_CITY_ISSUER AS [BPKB], FORMAT(aa.TAX_ISSUE_DT, 'yyyy-MM-dd') AS [BPKBISSUEDATE], aa.ASSET_NOTES AS [ASSETNOTES], aa.MANUFACTURING_YEAR AS [MANYEAR], DOWN_PAYMENT_PRCNT AS [DPPERCENT], DOWN_PAYMENT_AMT AS [DPAMOUNT], aa.SERIAL_NO_1 AS [SERIALNO1], aa.SERIAL_NO_2 AS [SERIALNO2], aa.SERIAL_NO_3 AS [SERIALNO3], aa.SERIAL_NO_4 AS [SERIALNO4] FROM APP_ASSET aa WITH(NOLOCK) JOIN APP a WITH(NOLOCK) ON aa.APP_ID = a.APP_ID JOIN V_ASSET_CATEGORY vac WITH(NOLOCK) ON vac.ASSET_CATEGORY_CODE = aa.ASSET_CATEGORY_CODE WHERE APP_NO = '"+ appno +"') as Orig unpivot (value for [Code] in ([ASSET_USAGE]) )as unpiv) as mastername JOIN FOUNDATION.dbo.REF_MASTER rf WITH(NOLOCK) ON rf.MASTER_CODE = mastername.value WHERE rf.IS_ACTIVE = '1') AS ref  PIVOT (MAX(ref.DESCR) for [Code] in ([ASSET_USAGE],[CUST_PERSONAL_RELATIONSHIP],[ID_TYPE])) as piv"), {  row ->

			ResultSetMetaData rsmd = row.getMetaData()
			colmcount = rsmd.getColumnCount()


			for(i = 0 ; i < colmcount ; i++){
				appdata = (row[i])
				listappdata.add(appdata)
			}
		})
		return listappdata
	}

	//keyword check app view asset attr
	@Keyword
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

	//keyword check app view asset accessories
	@Keyword
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

	//keyword check app view asset user
	@Keyword
	public checkAssetUser (Sql instance, String appno){
		String appdata
		ArrayList <String> listappdata = new ArrayList<String>()
		instance.eachRow(("SELECT USER_NAME, DESCR FROM APP_COLLATERAL_REGISTRATION acr WITH(NOLOCK) JOIN APP_COLLATERAL ac WITH(NOLOCK) ON ac.APP_COLLATERAL_ID = acr.APP_COLLATERAL_ID JOIN APP a WITH(NOLOCK) ON a.APP_ID = ac.APP_ID JOIN FOUNDATION.dbo.REF_MASTER rml WITH(NOLOCK) ON rml.MASTER_CODE = MR_USER_RELATIONSHIP_CODE WHERE APP_NO = '"+ appno +"' AND REF_MASTER_TYPE_CODE = 'CUST_PERSONAL_RELATIONSHIP'"), {  row ->

			ResultSetMetaData rsmd = row.getMetaData()
			colmcount = rsmd.getColumnCount()


			for(i = 0 ; i < colmcount ; i++){
				appdata = (row[i])
				listappdata.add(appdata)
			}
		})
		return listappdata
	}

	//keyword check app view asset owner
	@Keyword
	public checkAssetOwner (Sql instance, String appno){
		String appdata
		ArrayList <String> listappdata = new ArrayList<String>()
		instance.eachRow(("SELECT OWNER_NAME, OWNER_ADDR, DESCR, OWNER_AREA_CODE_4 + ' / ' + OWNER_AREA_CODE_3, MR_ID_TYPE_CODE, OWNER_AREA_CODE_2, OWNER_ID_NO, OWNER_AREA_CODE_1, OWNER_MOBILE_PHN_NO, OWNER_CITY, OWNER_PROFESSION_CODE, OWNER_ZIPCODE FROM APP_COLLATERAL_REGISTRATION acr WITH(NOLOCK) JOIN APP_COLLATERAL ac WITH(NOLOCK) ON ac.APP_COLLATERAL_ID = acr.APP_COLLATERAL_ID JOIN APP a WITH(NOLOCK) ON a.APP_ID = ac.APP_ID JOIN FOUNDATION.dbo.REF_MASTER rml WITH(NOLOCK) ON rml.MASTER_CODE = MR_OWNER_RELATIONSHIP_CODE WHERE APP_NO = '"+appno+"' GROUP BY OWNER_NAME, OWNER_ADDR, DESCR, OWNER_AREA_CODE_4 + ' / ' + OWNER_AREA_CODE_3, MR_ID_TYPE_CODE, OWNER_AREA_CODE_2, OWNER_ID_NO, OWNER_AREA_CODE_1, OWNER_MOBILE_PHN_NO, OWNER_CITY, OWNER_PROFESSION_CODE, OWNER_ZIPCODE"), {  row ->

			ResultSetMetaData rsmd = row.getMetaData()
			colmcount = rsmd.getColumnCount()


			for(i = 0 ; i < colmcount ; i++){
				appdata = (row[i])
				listappdata.add(appdata)
			}
		})
		return listappdata
	}

	//keyword check app view asset location
	@Keyword
	public checkAssetLocation (Sql instance, String appno){
		String appdata
		ArrayList <String> listappdata = new ArrayList<String>()
		instance.eachRow(("SELECT TOP 1 LOCATION_ADDR, LOCATION_AREA_CODE_4 + ' / ' + LOCATION_AREA_CODE_3, LOCATION_AREA_CODE_2, LOCATION_AREA_CODE_1, LOCATION_CITY, LOCATION_ZIPCODE FROM APP_COLLATERAL_REGISTRATION acr WITH(NOLOCK) JOIN APP_COLLATERAL ac WITH(NOLOCK) ON ac.APP_COLLATERAL_ID = acr.APP_COLLATERAL_ID JOIN APP a WITH(NOLOCK) ON a.APP_ID = ac.APP_ID JOIN FOUNDATION.dbo.REF_MASTER rml WITH(NOLOCK) ON rml.MASTER_CODE = MR_USER_RELATIONSHIP_CODE WHERE APP_NO = '"+ appno +"'"), {  row ->

			ResultSetMetaData rsmd = row.getMetaData()
			colmcount = rsmd.getColumnCount()


			for(i = 0 ; i < colmcount ; i++){
				appdata = (row[i])
				listappdata.add(appdata)
			}
		})
		return listappdata
	}

	//keyword check app view asset collateral
	@Keyword
	public checkAssetCollateral (Sql instance, String appno){
		String appdata
		ArrayList <String> listappdata = new ArrayList<String>()
		instance.eachRow(("SELECT ISNULL(ac.APP_COLLATERAL_NO,'') AS [APP_COLLATERAL_NO], ac.FULL_ASSET_NAME, vat.ASSET_TYPE_NAME, aa.ASSET_PRICE_AMT, ISNULL(ac.SERIAL_NO_1,'') AS [SERIAL1], ISNULL(ac.SERIAL_NO_2,'') AS [SERIAL2], ISNULL(ac.SERIAL_NO_3,'') AS [SERIAL3], OWNER_NAME FROM APP_COLLATERAL_REGISTRATION acr WITH(NOLOCK) JOIN APP_COLLATERAL ac WITH(NOLOCK) ON ac.APP_COLLATERAL_ID = acr.APP_COLLATERAL_ID JOIN APP a WITH(NOLOCK) ON a.APP_ID = ac.APP_ID JOIN FOUNDATION.dbo.REF_MASTER rml WITH(NOLOCK) ON rml.MASTER_CODE = MR_USER_RELATIONSHIP_CODE JOIN APP_ASSET aa WITH(NOLOCK) ON aa.APP_ASSET_ID = ac.APP_ASSET_ID JOIN V_ASSET_TYPE vat WITH(NOLOCK) ON vat.ASSET_TYPE_CODE = ac.ASSET_TYPE_CODE WHERE APP_NO = '"+appno+"' GROUP BY ac.APP_COLLATERAL_NO, ac.FULL_ASSET_NAME, vat.ASSET_TYPE_NAME, aa.ASSET_PRICE_AMT, ac.SERIAL_NO_1, ac.SERIAL_NO_2, ac.SERIAL_NO_3, OWNER_NAME"), {  row ->

			ResultSetMetaData rsmd = row.getMetaData()
			colmcount = rsmd.getColumnCount()


			for(i = 0 ; i < colmcount ; i++){
				appdata = (row[i])
				listappdata.add(appdata)
			}
		})
		return listappdata
	}

	//Keyword check app view asset collateral detail
	@Keyword
	public checkAssetCollateralDetail(Sql instance, String appno){
		HashMap<String, ArrayList<String>> collateralDetail = new HashMap<String, ArrayList<String>>()
		String appdata, appcollateralid
		ArrayList <String> collateralinfo = new ArrayList<String>()
		ArrayList <String> collateraldoc = new ArrayList<String>()
		ArrayList <String> collateralattr = new ArrayList<String>()
		ArrayList <String> collateralacc = new ArrayList<String>()
		ArrayList <String> collateraluserownerloc = new ArrayList<String>()
		instance.eachRow(("SELECT app_collateral_id, ISNULL(app_collateral_no,'-') AS app_collateral_no, asset_type_name , full_asset_name, collateral_value_amt, manufacturing_year, MR_COLLATERAL_CONDITION_CODE, CASE WHEN SERIAL_NO_1 = '' THEN '-' ELSE SERIAL_NO_1 END AS SERIAL_NO_1, DESCR, CASE WHEN SERIAL_NO_2 = '' THEN '-' ELSE SERIAL_NO_2 END AS SERIAL_NO_2, FORMAT(ASSET_TAX_DT,'dd-MMM-yyyy') AS ASSET_TAX_DT, CASE WHEN SERIAL_NO_3 = '' THEN '-' ELSE SERIAL_NO_3 END AS SERIAL_NO_3, CASE WHEN CAST(COLLATERAL_PORTION_PRCNT AS VARCHAR)= '0.000000' THEN '-' ELSE CAST(COLLATERAL_PORTION_PRCNT AS VARCHAR) END AS COLLATERAL_PORTION_PRCNT, CASE WHEN COLLATERAL_NOTES = '' THEN '-' ELSE COLLATERAL_NOTES END AS COLLATERAL_NOTES FROM APP_COLLATERAL ac with(nolock) join app a with(nolock) on ac.app_id = a.app_id join v_asset_type at with(nolock) on at.ASSET_TYPE_CODE = ac.ASSET_TYPE_CODE join FOUNDATION.dbo.REF_MASTER rml with(nolock) on rml.MASTER_CODE = ac.MR_COLLATERAL_USAGE_CODE where app_no = '"+appno+"'"), {  row ->

			ResultSetMetaData rsmd = row.getMetaData()
			colmcount = rsmd.getColumnCount()


			for(i = 0 ; i < colmcount ; i++){
				if(i==0){
					appcollateralid=row[i]
				}
				else{
					appdata = (row[i])
					collateralinfo.add(appdata)
				}
			}
		})
		instance.eachRow(("select doc_name, CASE WHEN is_received = 1 THEN 'YES' WHEN IS_RECEIVED = 0 THEN 'NO' END AS IS_RECEIVED, CASE WHEN doc_no = '' THEN '-' ELSE doc_no end as doc_no, ISNULL(FORMAT(EXPIRED_DT,'yyyy-MM-dd'),'-') AS EXPIRED_DT, CASE WHEN doc_notes = '' THEN '-' ELSE DOC_NOTES END AS DOC_NOTES from app_collateral_doc acd with(nolock) where app_collateral_id = '"+appcollateralid+"'"), {  row ->

			ResultSetMetaData rsmd = row.getMetaData()
			colmcount = rsmd.getColumnCount()
			for(i = 0 ; i < colmcount ; i++){
				appdata = (row[i])
				collateraldoc.add(appdata)
			}
		})
		instance.eachRow(("select COLLATERAL_ACCESSORY_NAME, ACCESSORY_PRICE_AMT, DOWN_PAYMENT_PRCNT, DOWN_PAYMENT_AMT, ACCESSORY_NOTES from APP_COLLATERAL_ACCESSORY aca with(nolock) where app_collateral_id = '"+appcollateralid+"'"), {  row ->

			ResultSetMetaData rsmd = row.getMetaData()
			colmcount = rsmd.getColumnCount()


			for(i = 0 ; i < colmcount ; i++){
				appdata = (row[i])
				collateralacc.add(appdata)
			}
		})
		instance.eachRow(("select collateral_attr_name, CASE WHEN attr_value = '' THEN '-' ELSE attr_value end as attr_value from app_collateral_attr acat with(nolock) where app_collateral_id = '"+appcollateralid+"'"), {  row ->

			ResultSetMetaData rsmd = row.getMetaData()
			colmcount = rsmd.getColumnCount()


			for(i = 0 ; i < colmcount ; i++){
				appdata = (row[i])
				collateralattr.add(appdata)
			}
		})
		instance.eachRow(("select user_name, MR_USER_RELATIONSHIP_CODE AS [USER_RELATIONSHIP_NAME], owner_name, OWNER_ADDR, MR_OWNER_RELATIONSHIP_CODE AS [ONWER_RELATIONSHIP_NAME], OWNER_AREA_CODE_4 +' / '+owner_area_code_3 AS [RT / RW], mr_id_type_code AS [ID_TYPE], owner_area_code_2, OWNER_ID_NO, owner_area_code_1,CASE WHEN OWNER_MOBILE_PHN_NO = '' THEN '-' ELSE OWNER_MOBILE_PHN_NO END AS OWNER_MOBILE_PHN_NO, owner_city,CASE WHEN OWNER_PROFESSION_CODE = '' THEN '-' ELSE OWNER_PROFESSION_CODE END AS OWNER_PROFESSION_CODE,OWNER_ZIPCODE,location_addr,LOCATION_AREA_CODE_4+' / '+LOCATION_AREA_CODE_3 as [RT / RW], LOCATION_AREA_CODE_2,LOCATION_AREA_CODE_1, location_city, location_zipcode FROM( SELECT user_name, owner_name, OWNER_ID_NO, OWNER_MOBILE_PHN_NO, OWNER_PROFESSION_CODE,OWNER_ADDR, OWNER_AREA_CODE_4, owner_area_code_3, owner_area_code_2, owner_area_code_1,owner_city,OWNER_ZIPCODE,location_addr,LOCATION_AREA_CODE_4,LOCATION_AREA_CODE_3,LOCATION_AREA_CODE_2,LOCATION_AREA_CODE_1,location_city,location_zipcode,mastername.Code,rml.DESCR as [REFMASTER] FROM ( select user_name, owner_name, OWNER_ID_NO, OWNER_MOBILE_PHN_NO, OWNER_PROFESSION_CODE,OWNER_ADDR,OWNER_AREA_CODE_4,owner_area_code_3,owner_area_code_2,owner_area_code_1,owner_city,OWNER_ZIPCODE,location_addr,LOCATION_AREA_CODE_4,LOCATION_AREA_CODE_3,LOCATION_AREA_CODE_2,LOCATION_AREA_CODE_1,location_city,location_zipcode,[Code],value FROM(select user_name, MR_USER_RELATIONSHIP_CODE, owner_name, MR_OWNER_RELATIONSHIP_CODE, mr_id_type_code,OWNER_ID_NO,OWNER_MOBILE_PHN_NO, OWNER_PROFESSION_CODE,OWNER_ADDR, OWNER_AREA_CODE_4,owner_area_code_3, owner_area_code_2,owner_area_code_1,owner_city,OWNER_ZIPCODE,location_addr,LOCATION_AREA_CODE_4, LOCATION_AREA_CODE_3,LOCATION_AREA_CODE_2,LOCATION_AREA_CODE_1,location_city,location_zipcode from APP_COLLATERAL_REGISTRATION acr with(nolock) where app_collateral_id = '"+appcollateralid+"') as Orig unpivot (value for [Code] in (MR_USER_RELATIONSHIP_CODE,MR_OWNER_RELATIONSHIP_CODE,mr_id_type_code))as unpiv )as mastername left JOIN FOUNDATION.dbo.REF_MASTER rml WITH(NOLOCK) ON rml.MASTER_CODE = mastername.value )AS ref PIVOT(MAX([REFMASTER]) for [Code] in (MR_USER_RELATIONSHIP_CODE, MR_OWNER_RELATIONSHIP_CODE, mr_id_type_code)) as piv "), {  row ->

			ResultSetMetaData rsmd = row.getMetaData()
			colmcount = rsmd.getColumnCount()


			for(i = 0 ; i < colmcount ; i++){
				appdata = (row[i])
				collateraluserownerloc.add(appdata)
			}
		})


		collateralDetail.put("ColInfo", collateralinfo)
		collateralDetail.put("ColDoc", collateraldoc)
		collateralDetail.put("ColAcc", collateralacc)
		collateralDetail.put("ColAttr", collateralattr)
		collateralDetail.put("ColUserOwnerLoc",collateraluserownerloc)

		return collateralDetail
	}

	//keyword check app view term and condition
	@Keyword
	public checkTermandCondition (Sql instance, String appno){
		String appdata
		ArrayList <String> listappdata = new ArrayList<String>()
		instance.eachRow(("SELECT rtl.TC_NAME, PRIOR_TO, CASE WHEN CAST(at.IS_MANDATORY as varchar(25)) = '0' THEN 'NO' WHEN CAST(at.IS_MANDATORY as varchar(25)) = '1' THEN 'YES' ELSE CAST(at.IS_MANDATORY as varchar(25)) END, CASE WHEN CAST(IS_CHECKED as varchar(25)) = '0' THEN 'NO' WHEN CAST(IS_CHECKED as varchar(25)) = '1' THEN 'YES' ELSE CAST(IS_CHECKED as varchar(25)) END, CASE WHEN CAST(IS_WAIVED as varchar(25)) = '0' THEN 'NO' WHEN CAST(IS_WAIVED as varchar(25)) = '1' THEN 'YES' ELSE CAST(IS_WAIVED as varchar(25)) END, ISNULL(FORMAT(PROMISED_DT, 'dd-MMM-yyyy'),'-') AS [PROMISED_DATE], ISNULL(FORMAT(EXPIRED_DT, 'dd-MMM-yyyy'),'-') AS [EXPIRED_DATE], ISNULL(at.NOTES,'') AS [NOTES] FROM APP_TC at WITH(NOLOCK)JOIN APP a WITH(NOLOCK) ON a.APP_ID = at.APP_ID RIGHT JOIN REF_TC_LOS rtl WITH(NOLOCK) ON rtl.TC_CODE = at.TC_CODE WHERE APP_NO = '"+ appno +"'"), {  row ->

			ResultSetMetaData rsmd = row.getMetaData()
			colmcount = rsmd.getColumnCount()


			for(i = 0 ; i < colmcount ; i++){
				appdata = (row[i])
				listappdata.add(appdata)
			}
		})
		return listappdata
	}

	//keyword check app view referantor
	@Keyword
	public checkReferantor (Sql instance, String appno){
		String refdata
		ArrayList <String> listrefdata = new ArrayList<String>()
		instance.eachRow(("SELECT CASE WHEN MR_REFERANTOR_TYPE = 'AGENCY_PERSONAL' OR MR_REFERANTOR_TYPE = 'AGENCY_COMPANY' THEN 'AGENCY' WHEN MR_REFERANTOR_TYPE = 'CUSTOMER_PERSONAL' OR MR_REFERANTOR_TYPE = 'CUSTOMER_COMPANY' THEN 'CUSTOMER' ELSE 'MULTIFINANCE EMPLOYEE' END AS [REFERANTOR_CATEGORY], RTRIM(ar.REFERANTOR_NAME) as [REFERANTOR_NAME], DESCR as [MR_REFERANTOR_TYPE], d.BANK_NAME + ' - ' + ar.BANK_ACC_NAME + ' - ' + ar.BANK_ACC_NO AS 'BANK_ACCOUNT', ar.MR_TAX_CALC_METHOD, CASE WHEN IS_USE_VAT = '0' THEN 'NO' WHEN IS_USE_VAT = '1' THEN 'YES' END AS [IS_USE_VAT] FROM APP a WITH(NOLOCK) JOIN APP_REFERANTOR ar WITH(NOLOCK) ON a.APP_ID = ar.APP_ID JOIN REF_BANK_LOS d WITH(NOLOCK) ON ar.REF_BANK_CODE = d.BANK_CODE JOIN FOUNDATION.dbo.REF_MASTER rf WITH(NOLOCK) ON rf.MASTER_CODE = mr_referantor_type WHERE APP_NO = '"+appno+"' AND REF_MASTER_TYPE_CODE = 'REFERANTOR_TYPE' ORDER BY APP_REFERANTOR_ID"), {  row ->

			ResultSetMetaData rsmd = row.getMetaData()
			colmcount = rsmd.getColumnCount()


			for(i = 0 ; i < colmcount ; i++){
				refdata = (row[i])
				listrefdata.add(refdata)
			}
		})
		return listrefdata
	}

	//keyword check app view MOI information
	@Keyword
	public checkMOInfo (Sql instance, String appno){
		String modata
		ArrayList <String> listmodata = new ArrayList<String>()
		instance.eachRow(("SELECT ISNULL([SPV],'-') AS [SPV],[OFFICER] , ISNULL(SALES_NOTES,'-') AS [SALES_NOTES], ISNULL(DESCR,'-') as [SALES_RECOM] FROM (SELECT sales_notes, MR_SALES_RECOMMEND_CODE, mastername.Code, rel.emp_name FROM ( select sales_notes, MR_SALES_RECOMMEND_CODE, [Code], value FROM ( select sales_head_no as [SPV], sales_officer_no as [OFFICER], sales_notes, MR_SALES_RECOMMEND_CODE from app a WHERE APP_NO = '"+appno+"') as Orig unpivot (value for [Code] in ([SPV],[OFFICER]) )as unpiv) as mastername left JOIN REF_USER_LOS rul WITH(NOLOCK) ON rul.emp_no = mastername.value left join ref_emp_los rel with(nolock) on rel.emp_no = rul.emp_no) AS ref PIVOT(MAX(ref.emp_name) for [Code] in ([SPV],[OFFICER])) as piv left join FOUNDATION.dbo.REF_MASTER rm with(nolock) on piv.MR_SALES_RECOMMEND_CODE = rm.MASTER_CODE and rm.ref_master_type_code = 'SLS_RECOM'"), {  row ->

			ResultSetMetaData rsmd = row.getMetaData()
			colmcount = rsmd.getColumnCount()


			for(i = 0 ; i < colmcount ; i++){
				modata = (row[i])
				listmodata.add(modata)
			}
		})
		return listmodata
	}

	//keyword check app view app info and restruring
	@Keyword
	public checkAppInfoAndRestructuringData(Sql instance, String appno){
		String appdata
		ArrayList <String> listappdata = new ArrayList<String>()
		instance.eachRow(("SELECT [APPSRCNAME] , [FIRST_INST_TYPE] , [PAYMENTFREQ] , [TENOR] ,[NUM_OF_INST], [INTRSTTYPE] , CASE WHEN [FLOATINGPERIOD] ='' THEN '-' ELSE [FLOATINGPERIOD] END , [INST_SCHM] , [WOP] , [CUST_NOTIF_OPT] , [CHARACTERISTIC_OF_CREDIT] , ISNULL([PREVAGGRMT],'-') AS [PREV_AGRMNT_NO], ISNULL([WAY_OF_RESTRUCTURE],'-') AS [WAY_OF_RESTRUCTURE], [SLIKECO], ISNULL([MOU_NO],'-') AS [MOU_NO] FROM (SELECT [APPSRCNAME], [PAYMENTFREQ], [TENOR],[NUM_OF_INST],[FLOATINGPERIOD], [PREVAGGRMT], [MOU_NO], mastername.Code, DESCR FROM ( select [APPSRCNAME], [PAYMENTFREQ], [TENOR],[NUM_OF_INST], [FLOATINGPERIOD], [PREVAGGRMT], [MOU_NO], [Code], value FROM ( SELECT ras.APP_SRC_NAME AS [APPSRCNAME], MR_FIRST_INST_TYPE_CODE AS 'FIRST_INST_TYPE', rpf.DESCR AS [PAYMENTFREQ], TENOR AS [TENOR],NUM_OF_INST AS [NUM_OF_INST], CAST(acpfd.INTEREST_TYPE as nvarchar(50)) AS 'INTRSTTYPE', FLOATING_PERIOD_CODE AS [FLOATINGPERIOD], MR_INST_SCHEME_CODE AS 'INST_SCHM', MR_WOP_CODE AS 'WOP', MR_CUST_NOTIFY_OPT_CODE AS 'CUST_NOTIF_OPT', MR_CHARACTERISTIC_OF_CREDIT_CODE AS 'CHARACTERISTIC_OF_CREDIT', agr.PREV_AGRMNT_NO AS [PREVAGGRMT], MR_WAY_OF_RESTRUCTURE_CODE AS 'WAY_OF_RESTRUCTURE', agr.MR_SLIK_SEC_ECO_CODE AS [SLIKECO], mou_cust_no as [MOU_NO] FROM APP a WITH(NOLOCK) JOIN APP_CUST ac WITH(NOLOCK) ON a.APP_ID = ac.APP_ID JOIN APP_GOVERMENT_REGULATION agr WITH(NOLOCK) ON agr.APP_ID = a.APP_ID JOIN APP_FIN_DATA acpfd WITH(NOLOCK) ON acpfd.APP_ID = ac.APP_ID JOIN REF_APP_SRC ras WITH(NOLOCK) ON ras.APP_SRC_CODE = a.MR_APP_SOURCE_CODE left JOIN MOU_CUST mc with(nolock) on mc.mou_cust_id = a.mou_cust_id JOIN FOUNDATION.dbo.REF_PAY_FREQ rpf with(nolock) on rpf.PAY_FREQ_CODE = a.PAY_FREQ_CODE WHERE APP_NO = '"+appno+"') as Orig unpivot (value for [Code] in ([FIRST_INST_TYPE],[WOP],[INTRSTTYPE],[INST_SCHM],[CUST_NOTIF_OPT],[CHARACTERISTIC_OF_CREDIT],[WAY_OF_RESTRUCTURE],[SLIKECO]) )as unpiv) as mastername JOIN FOUNDATION.dbo.REF_MASTER rf WITH(NOLOCK) ON rf.MASTER_CODE = mastername.value WHERE rf.IS_ACTIVE = '1' AND rf.REF_MASTER_TYPE_CODE IN ('FIRST_INST_TYPE','WOP','INTRSTTYPE','INST_SCHM','CUST_NOTIF_OPT','CHARACTERISTIC_OF_CREDIT','WAY_OF_RESTRUCTURE','SLIK_SEC_ECO')) AS ref PIVOT(MAX(ref.DESCR) for [Code] in ([FIRST_INST_TYPE],[WOP],[INTRSTTYPE],[INST_SCHM],[CUST_NOTIF_OPT],[CHARACTERISTIC_OF_CREDIT],[WAY_OF_RESTRUCTURE],[SLIKECO])) as piv"), {  row ->

			ResultSetMetaData rsmd = row.getMetaData()
			colmcount = rsmd.getColumnCount()


			for(i = 0 ; i < colmcount ; i++){
				appdata = (row[i])
				listappdata.add(appdata)
			}
		})
		return listappdata
	}

	//keyword check app view application attr
	@Keyword
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

	//keyword check app view life insurance
	@Keyword
	public checkLifeInsurance(Sql instance, String appno){
		String lifeinsdata
		ArrayList <String> listlifeinsdata = new ArrayList<String>()
		instance.eachRow(("SELECT ISNULL(alih.LIFE_INSCO_BRANCH_NAME,'-') AS [LIFEINSCONAME], CASE WHEN CONVERT(NVARCHAR,total_premi_to_cust) IS NULL OR CONVERT(NVARCHAR,total_premi_to_cust) = '0.00' THEN '-' ELSE CONVERT(NVARCHAR,total_premi_to_cust) END AS [CUSTPREMI], CASE WHEN CONVERT(NVARCHAR,total_premi_from_insco) IS NULL OR CONVERT(NVARCHAR,total_premi_from_insco) = '0.00' THEN '-' ELSE CONVERT(NVARCHAR,total_premi_from_insco) END AS [INSCOPREMI] , CASE WHEN CONVERT(NVARCHAR, CUST_ADMIN_FEE_AMT) IS NULL OR CONVERT(NVARCHAR, CUST_ADMIN_FEE_AMT) = '0.00' THEN '-' ELSE CONVERT(NVARCHAR, CUST_ADMIN_FEE_AMT) END AS [CUSTADMFEE], CASE WHEN CONVERT(NVARCHAR, INSCO_ADMIN_FEE_AMT) IS NULL OR CONVERT(NVARCHAR, INSCO_ADMIN_FEE_AMT) = '0.00' THEN '-' ELSE CONVERT(NVARCHAR, INSCO_ADMIN_FEE_AMT) END AS [INSCOADMFEE], CASE WHEN CONVERT(NVARCHAR, TOTAL_LIFE_INS_CPTLZ_AMT) IS NULL OR CONVERT(NVARCHAR, TOTAL_LIFE_INS_CPTLZ_AMT) = '0.00' THEN '-' ELSE CONVERT(NVARCHAR, TOTAL_LIFE_INS_CPTLZ_AMT) END AS [CPTLZPREMI] FROM APP_LIFE_INS_H alih WITH(NOLOCK) right JOIN APP a WITH(NOLOCK) ON alih.APP_ID = a.APP_ID WHERE APP_NO = '"+appno+"'"), { row ->

			ResultSetMetaData rsmd = row.getMetaData()
			colmcount = rsmd.getColumnCount()


			for(i = 0 ; i < colmcount ; i++){
				lifeinsdata = (row[i])
				listlifeinsdata.add(lifeinsdata)
			}
		})
		return listlifeinsdata
	}

	//keyword check app view life insurance object
	@Keyword
	public checkLifeInsuranceObject(Sql instance, String appno){
		String lifeinsdata
		ArrayList <String> listlifeinsdata = new ArrayList<String>()
		instance.eachRow(("select insured_name, age, mr_cust_type_code from APP_LIFE_INS_D alid with(nolock) join app_life_ins_h alih with(nolock) on alih.APP_LIFE_INS_H_ID = alid.APP_LIFE_INS_H_ID join app a with(nolock) on a.app_id = alih.APP_ID where app_no = '"+appno+"'"), { row ->

			ResultSetMetaData rsmd = row.getMetaData()
			colmcount = rsmd.getColumnCount()


			for(i = 0 ; i < colmcount ; i++){
				lifeinsdata = (row[i])
				listlifeinsdata.add(lifeinsdata)
			}
		})
		return listlifeinsdata
	}

	//keyword check app view insuredby
	@Keyword
	public checkInsuredBy(Sql instance, String appno){
		String insuredby
		instance.eachRow(("SELECT rml.DESCR as 'INSURED_BY' FROM APP_INS ai WITH(NOLOCK) JOIN APP a WITH(NOLOCK) ON ai.APP_ID = a.APP_ID JOIN APP_INS_OBJ aio WITH(NOLOCK) ON a.APP_ID = aio.APP_ID JOIN FOUNDATION.dbo.REF_MASTER rml WITH(NOLOCK) ON rml.MASTER_CODE = INS_ASSET_COVERED_BY WHERE a.APP_NO = '"+ appno +"' AND rml.REF_MASTER_TYPE_CODE = 'INSURED_BY'"), {  row ->


			insuredby = (row[0])
		})
		return insuredby
	}

	//keyword check app view insurance customer
	@Keyword
	public checkInsuranceCustomer(Sql instance, String appno){
		String insurancedata
		ArrayList <String> listinsurance = new ArrayList<String>()
		instance.eachRow(("SELECT rml.DESCR as 'INSURED_BY', INS_POLICY_NO, CUST_INSCO_BRANCH_NAME, INS_POLICY_NAME, CUST_CVG_AMT, CONVERT(varchar(50),CUST_COVER_START_DT, 127), CUST_NOTES, CONVERT(varchar(50),END_DT, 127) FROM APP_INS ai WITH(NOLOCK) JOIN APP a WITH(NOLOCK) ON ai.APP_ID = a.APP_ID JOIN APP_INS_OBJ aio WITH(NOLOCK) ON a.APP_ID = aio.APP_ID JOIN FOUNDATION.dbo.REF_MASTER rml WITH(NOLOCK) ON rml.MASTER_CODE = INS_ASSET_COVERED_BY WHERE a.APP_NO = '"+ appno +"' AND rml.REF_MASTER_TYPE_CODE = 'INSURED_BY'"), {  row ->

			ResultSetMetaData rsmd = row.getMetaData()
			colmcount = rsmd.getColumnCount()


			for(i = 0 ; i < colmcount ; i++){
				insurancedata = (row[i])
				listinsurance.add(insurancedata)
			}
		})
		return listinsurance
	}

	//keyword check app view insurance MF
	@Keyword
	public checkInsuranceMultifinance(Sql instance, String appno){
		String insurancedata
		ArrayList <String> listinsurance = new ArrayList<String>()
		instance.eachRow((" SELECT [INSURED_BY], [INS_PAID_BY], [ASSETREGION], [INSCOBRANCHNAME], [PAY_PERIOD_TO_INSCO], [INS_COVER_PERIOD], [cvgAMT], [INSLENGTH], [INSCOADMINFEE],[INSCOSTAMPDUTYFEE], [NOTES] FROM (SELECT [INSCOBRANCHNAME], [cvgAMT], [INSLENGTH], [INSCOADMINFEE],[INSCOSTAMPDUTYFEE], [NOTES], [ASSETREGION], mastername.Code, DESCR FROM (select [INSCOBRANCHNAME], [cvgAMT], [INSLENGTH], [INSCOADMINFEE], [INSCOSTAMPDUTYFEE], [NOTES], [ASSETREGION], [Code], value FROM (SELECT INS_ASSET_COVERED_BY as 'INSURED_BY', INS_ASSET_PAID_BY as 'INS_PAID_BY', aio.INSCO_BRANCH_NAME as [INSCOBRANCHNAME], INS_ASSET_COVER_PERIOD as 'INS_COVER_PERIOD', aio.PAY_PERIOD_TO_INSCO as 'PAY_PERIOD_TO_INSCO', aio.CVG_AMT as [cvgAMT], INS_LENGTH as [INSLENGTH], INSCO_ADMIN_FEE_AMT  as [INSCOADMINFEE], INSCO_STAMP_DUTY_FEE AS [INSCOSTAMPDUTYFEE], aio.NOTES as [NOTES], aio.INS_ASSET_REGION as [ASSETREGION] FROM APP_INS ai WITH(NOLOCK) JOIN APP a WITH(NOLOCK) ON ai.APP_ID = a.APP_ID JOIN APP_INS_OBJ aio WITH(NOLOCK) ON a.APP_ID = aio.APP_ID WHERE a.APP_NO = '"+appno+"') as orig unpivot (value for [Code] in ([INSURED_BY],[INS_PAID_BY],[INS_COVER_PERIOD],[PAY_PERIOD_TO_INSCO]) )as unpiv) as mastername JOIN FOUNDATION.dbo.REF_MASTER rf on rf.MASTER_CODE = mastername.value WHERE rf.IS_ACTIVE = '1') AS ref PIVOT (MAX(ref.DESCR) for [Code] in ([INSURED_BY],[INS_PAID_BY],[INS_COVER_PERIOD],[PAY_PERIOD_TO_INSCO])) as piv"), {  row ->

			ResultSetMetaData rsmd = row.getMetaData()
			colmcount = rsmd.getColumnCount()


			for(i = 0 ; i < colmcount ; i++){
				insurancedata = (row[i])
				listinsurance.add(insurancedata)
			}
		})
		return listinsurance
	}

	//keyword check app view insurance cust-MF
	@Keyword
	public checkInsuranceCustMf(Sql instance, String appno){
		String insurancedata
		ArrayList <String> listinsurance = new ArrayList<String>()
		instance.eachRow(("SELECT [INSURED_BY], [POLICYNO], [BRANCHNAME], [POLICYNAME], [CUSTCVGAMT], [STARTDATE], [NOTE], [ENDDATE], [INS_PAID_BY], [ASSETREGION], [INSCOBRANCHNAME], [INS_COVER_PERIOD], [cvgAMT], [INSLENGTH], [INSCOADMINFEE],[INSCOSTAMPDUTYFEE], [NOTES] FROM (SELECT [INSCOBRANCHNAME], [POLICYNO], [BRANCHNAME], [POLICYNAME], [CUSTCVGAMT], [STARTDATE], [NOTE], [ENDDATE], [cvgAMT], [INSLENGTH], [NOTES], [ASSETREGION],[INSCOADMINFEE],[INSCOSTAMPDUTYFEE], mastername.Code, DESCR FROM (select [INSCOBRANCHNAME], [POLICYNO], [BRANCHNAME], [POLICYNAME], [CUSTCVGAMT], [STARTDATE], [NOTE], [ENDDATE], [cvgAMT], [INSLENGTH], [NOTES], [ASSETREGION],[INSCOADMINFEE],[INSCOSTAMPDUTYFEE], [Code], value FROM (SELECT INS_ASSET_COVERED_BY as 'INSURED_BY', INS_ASSET_PAID_BY as 'INS_PAID_BY', aio.INSCO_BRANCH_NAME as [INSCOBRANCHNAME], INS_ASSET_COVER_PERIOD as 'INS_COVER_PERIOD', aio.PAY_PERIOD_TO_INSCO as 'PAY_PERIOD_TO_INSCO', aio.CVG_AMT as [cvgAMT], INS_LENGTH as [INSLENGTH], INSCO_ADMIN_FEE_AMT as [INSCOADMINFEE], INSCO_STAMP_DUTY_FEE AS [INSCOSTAMPDUTYFEE], aio.NOTES as [NOTES], aio.INS_ASSET_REGION as [ASSETREGION], INS_POLICY_NO as [POLICYNO], CUST_INSCO_BRANCH_NAME as [BRANCHNAME], INS_POLICY_NAME as [POLICYNAME], CUST_CVG_AMT as [CUSTCVGAMT], CONVERT(varchar(50), START_DT, 127) as [STARTDATE], CUST_NOTES as [NOTE], CONVERT(varchar(50), END_DT, 127) as [ENDDATE] FROM APP_INS ai WITH(NOLOCK) JOIN APP a WITH(NOLOCK) ON ai.APP_ID = a.APP_ID JOIN APP_INS_OBJ aio WITH(NOLOCK) ON a.APP_ID = aio.APP_ID WHERE a.APP_NO = '"+appno+"') as orig unpivot (value for [Code] in ([INSURED_BY],[INS_PAID_BY],[INS_COVER_PERIOD],[PAY_PERIOD_TO_INSCO]) )as unpiv) as mastername JOIN FOUNDATION.dbo.REF_MASTER rf on rf.MASTER_CODE = mastername.value WHERE rf.IS_ACTIVE = '1') AS ref PIVOT (MAX(ref.DESCR) for [Code] in ([INSURED_BY],[INS_PAID_BY],[INS_COVER_PERIOD],[PAY_PERIOD_TO_INSCO])) as piv"), {  row ->

			ResultSetMetaData rsmd = row.getMetaData()
			colmcount = rsmd.getColumnCount()


			for(i = 0 ; i < colmcount ; i++){
				insurancedata = (row[i])
				listinsurance.add(insurancedata)
			}
		})
		return listinsurance
	}

	//keyword check app view app Main CVG
	@Keyword
	public checkInsuranceMainCoverage(Sql instance, String appno){
		String insurancedata
		ArrayList <String> listinsurance = new ArrayList<String>()
		instance.eachRow(("SELECT mr_main_cvg_type_code, CUST_MAIN_PREMI_AMT, aimc.TOTAL_CUST_ADD_PREMI_AMT, INSCO_MAIN_PREMI_AMT, aimc.TOTAL_INSCO_ADD_PREMI_AMT, SUBQ.DESCR, CASE WHEN CAST(IS_CAPITALIZED as varchar(25)) = '0' THEN 'NO' WHEN CAST(IS_CAPITALIZED as varchar(25)) = '1' THEN 'YES' ELSE CAST(IS_CAPITALIZED as varchar(25)) END FROM APP_INS_MAIN_CVG aimc JOIN APP_INS_OBJ aio WITH(NOLOCK) ON aio.APP_INS_OBJ_ID = aimc.APP_INS_OBJ_ID JOIN APP a WITH(NOLOCK) ON a.APP_ID = aio.APP_ID JOIN FOUNDATION.dbo.REF_MASTER rml WITH(NOLOCK) ON rml.MASTER_CODE = aimc.MR_MAIN_CVG_TYPE_CODE, (SELECT rml.DESCR, a.APP_NO FROM APP_INS_OBJ aio WITH(NOLOCK) JOIN APP a WITH(NOLOCK) ON a.APP_ID = aio.APP_ID JOIN FOUNDATION.dbo.REF_MASTER rml WITH(NOLOCK) ON rml.MASTER_CODE = aio.INS_ASSET_PAID_BY WHERE rml.REF_MASTER_TYPE_CODE = 'INS_PAID_BY') as SUBQ WHERE a.APP_NO = '"+ appno +"' AND SUBQ.APP_NO = a.APP_NO"), {  row ->

			ResultSetMetaData rsmd = row.getMetaData()
			colmcount = rsmd.getColumnCount()


			for(i = 0 ; i < colmcount ; i++){
				insurancedata = (row[i])
				listinsurance.add(insurancedata)
			}
		})
		return listinsurance
	}

	public checkInsuranceMainCoverageMultifinance(Sql instance, String appno){
		String insurancedata
		ArrayList <String> listinsurance = new ArrayList<String>()
		instance.eachRow(("SELECT mr_main_cvg_type_code, aimc.CUST_MAIN_PREMI_AMT, FORMAT(ROUND(cust_main_premi_rate,3),'N3') , aimc.TOTAL_CUST_ADD_PREMI_AMT, aimc.INSCO_MAIN_PREMI_AMT, FORMAT(ROUND(insco_main_premi_rate,3),'N3'), aimc.TOTAL_INSCO_ADD_PREMI_AMT, SUBQ.DESCR, CASE WHEN CAST(IS_CAPITALIZED as varchar(25)) = '0' THEN 'NO' WHEN CAST(IS_CAPITALIZED as varchar(25)) = '1' THEN 'YES' ELSE CAST(IS_CAPITALIZED as varchar(25)) END FROM APP_INS_MAIN_CVG aimc JOIN APP_INS_OBJ aio WITH(NOLOCK) ON aio.APP_INS_OBJ_ID = aimc.APP_INS_OBJ_ID JOIN APP a WITH(NOLOCK) ON a.APP_ID = aio.APP_ID JOIN FOUNDATION.dbo.REF_MASTER rml WITH(NOLOCK) ON rml.MASTER_CODE = aimc.MR_MAIN_CVG_TYPE_CODE, (SELECT rml.DESCR, a.APP_NO FROM APP_INS_OBJ aio WITH(NOLOCK) JOIN APP a WITH(NOLOCK) ON a.APP_ID = aio.APP_ID JOIN FOUNDATION.dbo.REF_MASTER rml WITH(NOLOCK) ON rml.MASTER_CODE = aio.INS_ASSET_PAID_BY WHERE rml.REF_MASTER_TYPE_CODE = 'INS_PAID_BY') as SUBQ WHERE a.APP_NO = '"+appno+"' AND SUBQ.APP_NO = a.APP_NO"), {  row ->

			ResultSetMetaData rsmd = row.getMetaData()
			colmcount = rsmd.getColumnCount()


			for(i = 0 ; i < colmcount ; i++){
				insurancedata = (row[i])
				listinsurance.add(insurancedata)
			}
		})
		return listinsurance
	}

	//keyword check app view app Add CVG
	@Keyword
	public checkAdditionalCoverage(Sql instance, String appno, int yearno){
		String insurancedata
		ArrayList <String> listinsurance = new ArrayList<String>()
		instance.eachRow(("SELECT STRING_AGG(mr_add_cvg_type_code, ', '), STRING_AGG(FORMAT(ROUND(cust_add_premi_rate,3),'N3'), ', '), STRING_AGG(FORMAT(ROUND(INSCO_add_premi_rate,3),'N3'), ', ') FROM APP_INS_MAIN_CVG aimc JOIN APP_INS_OBJ aio WITH(NOLOCK) ON aio.APP_INS_OBJ_ID = aimc.APP_INS_OBJ_ID JOIN APP a WITH(NOLOCK) ON a.APP_ID = aio.APP_ID JOIN APP_INS_ADD_CVG aiac WITH(NOLOCK) ON aiac.APP_INS_MAIN_CVG_ID = aimc.APP_INS_MAIN_CVG_ID WHERE APP_NO = '"+appno+"' AND YEAR_NO = '"+yearno+"'"), {  row ->

			ResultSetMetaData rsmd = row.getMetaData()
			colmcount = rsmd.getColumnCount()


			for(i = 0 ; i < colmcount ; i++){
				insurancedata = (row[i])
				listinsurance.add(insurancedata)
			}
		})
		return listinsurance
	}

	//keyword check app view app insurance summary
	@Keyword
	public checkInsuranceSummary(Sql instance, String appno){
		String insurancedata
		ArrayList <String> listinsurance = new ArrayList<String>()
		instance.eachRow(("select total_cust_main_premi_amt, total_insco_main_premi_amt, total_cust_add_premi_amt, total_insco_add_premi_amt, total_cust_fee_amt, total_insco_fee_amt, total_cust_premi_amt, total_insco_premi_amt, total_cust_disc_amt, total_cust_premi_amt-total_cust_disc_amt as [total_premi_to_cust_aft_disc], total_ins_cptlz_amt from app_ins ai join app a on a.app_id = ai.app_id where app_no = '"+appno+"'"), {  row ->

			ResultSetMetaData rsmd = row.getMetaData()
			colmcount = rsmd.getColumnCount()


			for(i = 0 ; i < colmcount ; i++){
				insurancedata = (row[i])
				listinsurance.add(insurancedata)
			}
		})
		return listinsurance
	}

	@Keyword
	public checkNewInsuranceSummary(Sql instance, String appno){
		String insurancedata
		ArrayList <String> listinsurance = new ArrayList<String>()
		instance.eachRow(("select total_cust_premi_amt, total_premi_paid_by_cust_amt, total_ins_cptlz_amt from app_ins ai join app a on ai.app_id = a.app_id where app_no = '"+appno+"'"), {  row ->

			ResultSetMetaData rsmd = row.getMetaData()
			colmcount = rsmd.getColumnCount()


			for(i = 0 ; i < colmcount ; i++){
				insurancedata = (row[i])
				listinsurance.add(insurancedata)
			}
		})
		return listinsurance
	}

	@Keyword
	public checkNewInsurance(Sql instance, String appno){
		String insurancedata
		ArrayList <String> listinsurance = new ArrayList<String>()
		instance.eachRow(("SELECT [FULL_ASSET_NAME], [INSURED_BY], [INS_PAID_BY], [INSCOBRANCHNAME], ISNULL([PAY_PERIOD_TO_INSCO],''), ISNULL([INS_COVER_PERIOD],''), [INSLENGTH], total_cust_main_premi_amt, total_cust_add_premi_amt, TOTAL_CUST_FEE_AMT FROM (SELECT [FULL_ASSET_NAME], [INSCOBRANCHNAME], [INSLENGTH], mastername.Code, DESCR, total_cust_main_premi_amt, total_cust_add_premi_amt, TOTAL_CUST_FEE_AMT FROM (select [FULL_ASSET_NAME], [INSCOBRANCHNAME], [INSLENGTH], [Code], value ,total_cust_main_premi_amt, total_cust_add_premi_amt, TOTAL_CUST_FEE_AMT FROM (SELECT [FULL_ASSET_NAME], INS_ASSET_COVERED_BY as 'INSURED_BY', INS_ASSET_PAID_BY as 'INS_PAID_BY', aio.INSCO_BRANCH_NAME as [INSCOBRANCHNAME], INS_ASSET_COVER_PERIOD as 'INS_COVER_PERIOD', aio.PAY_PERIOD_TO_INSCO as 'PAY_PERIOD_TO_INSCO', INS_LENGTH as [INSLENGTH], aio.total_cust_main_premi_amt, aio.total_cust_add_premi_amt, TOTAL_CUST_FEE_AMT FROM APP_INS ai WITH(NOLOCK) JOIN APP a WITH(NOLOCK) ON ai.APP_ID = a.APP_ID JOIN APP_INS_OBJ aio WITH(NOLOCK) ON a.APP_ID = aio.APP_ID join app_asset aa on aio.app_asset_id = aa.app_asset_id WHERE a.APP_NO = '"+appno+"') as orig unpivot (value for [Code] in ([INSURED_BY],[INS_PAID_BY],[INS_COVER_PERIOD],[PAY_PERIOD_TO_INSCO]))as unpiv) as mastername JOIN FOUNDATION.dbo.REF_MASTER rf on rf.MASTER_CODE = mastername.value WHERE rf.IS_ACTIVE = '1') AS ref PIVOT (MAX(ref.DESCR) for [Code] in ([INSURED_BY],[INS_PAID_BY],[INS_COVER_PERIOD],[PAY_PERIOD_TO_INSCO])) as piv"), {  row ->

			ResultSetMetaData rsmd = row.getMetaData()
			colmcount = rsmd.getColumnCount()


			for(i = 0 ; i < colmcount ; i++){
				insurancedata = (row[i])
				listinsurance.add(insurancedata)
			}
		})
		return listinsurance
	}

	//keyword check app view app reserved fund
	@Keyword
	public checkReservedFund(Sql instance, String appno){
		ArrayList<String> totalrsv = new ArrayList<String>()
		String rsvdata
		HashMap <String,ArrayList> result = new HashMap<>()
		ArrayList <String> listrsv = new ArrayList<String>()
		instance.eachRow(("select MR_RESERVED_FUND_SOURCE_CODE, MR_RESERVED_FUND_CODE, RESERVED_FUND_AMT from APP_RESERVED_FUND arf with(nolock) join app a with(nolock) on arf.app_id = a.app_id where app_no = '"+appno+"' "), { row ->

			ResultSetMetaData rsmd = row.getMetaData()
			colmcount = rsmd.getColumnCount()


			for(i = 0 ; i < colmcount ; i++){
				rsvdata = (row[i])
				listrsv.add(rsvdata)
			}
		})

		instance.eachRow(("select reserved_fund_allocated_amt from app_fin_data afd with(nolock) join app a with(nolock) on afd.app_id = a.app_id where app_no = '"+appno+"'"), { row ->
			totalrsv.add(row[0])
		})

		result.put("RSVList",listrsv)
		result.put("TotalRSVAmt",totalrsv)

		return result
	}

	//keyword check app view app financial
	@Keyword
	public checkFinancial(Sql instance, String appno){
		String findt
		ArrayList<String> listSubsidy = new ArrayList<String>()
		ArrayList<String> listFee = new ArrayList<String>()
		ArrayList<String> listFinancialData = new ArrayList<String>()
		ArrayList<String> installmentTable = new ArrayList<String>()

		HashMap <String,ArrayList> result = new HashMap<>()
		instance.eachRow(("select MR_SUBSIDY_FROM_TYPE_NAME, ISNULL(SUBSIDY_FROM_VALUE_NAME,'') AS [SUBSIDY_FROM_VALUE_NAME], MR_SUBSIDY_ALLOC_NAME, MR_SUBSIDY_SOURCE_NAME, MR_SUBSIDY_VALUE_TYPE_NAME, CASE WHEN MR_SUBSIDY_VALUE_TYPE_NAME = 'Amount' THEN SUBSIDY_AMT ELSE SUBSIDY_PRCNT END AS [SUBSIDY_VALUE] from APP_SUBSIDY asub with(nolock) JOIN APP a with(nolock) ON a.APP_ID = asub.APP_ID WHERE APP_NO = '"+appno+"'"), { row ->

			ResultSetMetaData rsmd = row.getMetaData()
			colmcount = rsmd.getColumnCount()


			for(i = 0 ; i < colmcount ; i++){
				findt = (row[i])
				listSubsidy.add(findt)
			}
		})

		instance.eachRow(("select app_fee_amt, CPTLZ_AMT from app_fee af with(nolock) join app a with(nolock) on af.app_id = a.app_id where app_no = '"+appno+"'"), { row ->

			ResultSetMetaData rsmd = row.getMetaData()
			colmcount = rsmd.getColumnCount()


			for(i = 0 ; i < colmcount ; i++){
				findt = (row[i])
				listFee.add(findt)
			}
		})

		instance.eachRow(("SELECT TOTAL_ASSET_PRICE_AMT, TOTAL_FEE_AMT, TOTAL_FEE_CPTLZ_AMT, TOTAL_INS_CUST_AMT, INS_CPTLZ_AMT, TOTAL_LIFE_INS_CUST_AMT, LIFE_INS_CPTLZ_AMT, DOWN_PAYMENT_GROSS_AMT, DOWN_PAYMENT_NETT_AMT, TOTAL_DOWN_PAYMENT_NETT_AMT, TDP_PAID_COY_AMT, NTF_AMT, effective_rate_prcnt, STD_EFFECTIVE_RATE_PRCNT, FLAT_RATE_PRCNT, INST_AMT, CASE WHEN GRACE_PERIOD = 0 OR GRACE_PERIOD_CODE IS NULL THEN '-' ELSE GRACE_PERIOD_CODE +' / '+CONVERT(VARCHAR,GRACE_PERIOD)+' INSTALLMENT' END AS [GRACE_PERIOD], ROUNDING_AMT, TOTAL_INTEREST_AMT, DIFF_RATE_AMT, TOTAL_AR, GROSS_YIELD_PRCNT, LTV, NUM_OF_INST, MR_FIRST_INST_TYPE_CODE FROM (SELECT TOTAL_ASSET_PRICE_AMT, TOTAL_FEE_AMT, TOTAL_FEE_CPTLZ_AMT, TOTAL_INS_CUST_AMT, INS_CPTLZ_AMT, TOTAL_LIFE_INS_CUST_AMT, LIFE_INS_CPTLZ_AMT, DOWN_PAYMENT_GROSS_AMT, DOWN_PAYMENT_NETT_AMT, TOTAL_DOWN_PAYMENT_NETT_AMT, TDP_PAID_COY_AMT, NTF_AMT, effective_rate_prcnt, STD_EFFECTIVE_RATE_PRCNT, FLAT_RATE_PRCNT, INST_AMT, ROUNDING_AMT, TOTAL_INTEREST_AMT, DIFF_RATE_AMT, TOTAL_AR, GROSS_YIELD_PRCNT, LTV, NUM_OF_INST,GRACE_PERIOD, mastername.Code, DESCR FROM ( select TOTAL_ASSET_PRICE_AMT, TOTAL_FEE_AMT, TOTAL_FEE_CPTLZ_AMT, TOTAL_INS_CUST_AMT, INS_CPTLZ_AMT, TOTAL_LIFE_INS_CUST_AMT, LIFE_INS_CPTLZ_AMT, DOWN_PAYMENT_GROSS_AMT, DOWN_PAYMENT_NETT_AMT, TOTAL_DOWN_PAYMENT_NETT_AMT, TDP_PAID_COY_AMT, NTF_AMT, effective_rate_prcnt, STD_EFFECTIVE_RATE_PRCNT, FLAT_RATE_PRCNT, INST_AMT, ROUNDING_AMT, TOTAL_INTEREST_AMT, DIFF_RATE_AMT, TOTAL_AR, GROSS_YIELD_PRCNT, LTV, NUM_OF_INST,GRACE_PERIOD, [Code], value FROM ( select TOTAL_ASSET_PRICE_AMT, TOTAL_FEE_AMT, TOTAL_FEE_CPTLZ_AMT, TOTAL_INS_CUST_AMT, INS_CPTLZ_AMT, TOTAL_LIFE_INS_CUST_AMT, LIFE_INS_CPTLZ_AMT, DOWN_PAYMENT_GROSS_AMT, DOWN_PAYMENT_NETT_AMT, TOTAL_DOWN_PAYMENT_NETT_AMT, TDP_PAID_COY_AMT, NTF_AMT, effective_rate_prcnt, STD_EFFECTIVE_RATE_PRCNT, FLAT_RATE_PRCNT, INST_AMT, GRACE_PERIOD, MR_GRACE_PERIOD_TYPE_CODE AS [GRACE_PERIOD_CODE], ROUNDING_AMT, TOTAL_INTEREST_AMT, DIFF_RATE_AMT, TOTAL_AR, GROSS_YIELD_PRCNT, LTV, MR_FIRST_INST_TYPE_CODE, NUM_OF_INST from app_fin_data afd with(nolock) join app a with(nolock) on a.APP_ID = afd.APP_ID where app_no = '"+appno+"') as Orig unpivot (value for [Code] in ([MR_FIRST_INST_TYPE_CODE],[GRACE_PERIOD_CODE]) )as unpiv) as mastername LEFT JOIN FOUNDATION.dbo.REF_MASTER rf WITH(NOLOCK) ON rf.MASTER_CODE = mastername.value AND rf.REF_MASTER_TYPE_CODE IN ('FIRST_INST_TYPE','GRACE_PERIOD_TYPE')) AS ref PIVOT(MAX(ref.DESCR) for [Code] in ([MR_FIRST_INST_TYPE_CODE],[GRACE_PERIOD_CODE])) as piv"), { row ->

			ResultSetMetaData rsmd = row.getMetaData()
			colmcount = rsmd.getColumnCount()


			for(i = 0 ; i < colmcount ; i++){
				findt = (row[i])
				listFinancialData.add(findt)
			}
		})

		instance.eachRow(("select inst_seq_no, inst_amt,PRINCIPAL_AMT,INTEREST_AMT,OS_PRINCIPAL_AMT,OS_INTEREST_AMT from APP_INST_SCHDL ais with(nolock) join app a with(nolock) on ais.app_id = a.app_id where app_no = '"+appno+"'"), { row ->

			ResultSetMetaData rsmd = row.getMetaData()
			colmcount = rsmd.getColumnCount()


			for(i = 0 ; i < colmcount ; i++){
				findt = (row[i])
				installmentTable.add(findt)
			}
		})


		result.put("Subsidy",listSubsidy)
		result.put("Fee",listFee)
		result.put("FinData",listFinancialData)
		result.put("InstTable",installmentTable)
		return result
	}

	//keyword check app view app commission
	@Keyword
	public checkCommissionData(Sql instance, String appno, Sql instanceFOU){


		String comdt
		ArrayList<String> listComSupp = new ArrayList<String>()
		ArrayList<String> listComSuppEmp = new ArrayList<String>()
		ArrayList<String> listComRef = new ArrayList<String>()
		//		ArrayList<String> AllocComSupp = new ArrayList<String>()
		//		ArrayList<String> AllocComSuppEmp = new ArrayList<String>()
		//		ArrayList<String> AllocComRef = new ArrayList<String>()

		HashMap <String,ArrayList> result = new HashMap<>()
		//Check Supplier
		instance.eachRow(("SELECT APP_COMMISSION_H_ID,vendor_name, TOTAL_COMMISSION_AMT,TOTAL_COMMISSION_AFTER_TAX_AMT, TOTAL_DISBURSE_AMT, TOTAL_EXPENSE_AMT, TAX_AMT+PENALTY_AMT, VAT_AMT, MR_TAX_CALC_METHOD_CODE, BANK_CODE+' - '+BANK_ACC_NO+' - '+BANK_ACC_NAME AS [BANK_ACCOUNT] FROM app_COMMISSION_h ach WITH(NOLOCK) join vendor_los v with(nolock) on v.VENDOR_CODE = ach.COMMISSION_RECIPIENT_REF_NO join app a with(nolock) on a.app_id = ach.app_id where app_no = '"+appno+"' and MR_COMMISSION_RECIPIENT_TYPE_CODE = 'SUPPLIER'"), { row ->

			ResultSetMetaData rsmd = row.getMetaData()
			colmcount = rsmd.getColumnCount()


			for(i = 0 ; i < colmcount ; i++){
				//				if(i==0){
				//					AllocComSupp.add(checkCommissionAllocation(instanceFOU, row[0]))
				//				}
				comdt = (row[i])
				listComSupp.add(comdt)
			}
		})
		//Check Supplier Employee
		instance.eachRow(("SELECT APP_COMMISSION_H_ID,vendor_emp_name, vendor_name, RESERVED_FIELD_2, TOTAL_COMMISSION_AMT,TOTAL_COMMISSION_AFTER_TAX_AMT, TOTAL_DISBURSE_AMT, TOTAL_EXPENSE_AMT, TAX_AMT+PENALTY_AMT, VAT_AMT, MR_TAX_CALC_METHOD_CODE, BANK_CODE+' - '+BANK_ACC_NO+' - '+BANK_ACC_NAME AS [BANK_ACCOUNT] FROM app_COMMISSION_h ach with(nolock) join vendor_emp_los ve with(nolock) on ve.vendor_emp_no = ach.COMMISSION_RECIPIENT_REF_NO join vendor_los v with(nolock) on v.vendor_code = ve.vendor_code join app a with(nolock) on a.app_id = ach.app_id where app_no = '"+appno+"' and MR_COMMISSION_RECIPIENT_TYPE_CODE = 'SUPPLIER_EMP'"), { row ->

			ResultSetMetaData rsmd = row.getMetaData()
			colmcount = rsmd.getColumnCount()


			for(i = 0 ; i < colmcount ; i++){
				//				if(i==0){
				//					AllocComSuppEmp.add(checkCommissionAllocation(instanceFOU, row[0]))
				//				}
				comdt = (row[i])
				listComSuppEmp.add(comdt)
			}
		})

		//Check Referantor
		instance.eachRow(("SELECT APP_COMMISSION_H_ID,COMMISSION_RECIPIENT_REF_NO, TOTAL_COMMISSION_AMT,TOTAL_COMMISSION_AFTER_TAX_AMT, TOTAL_DISBURSE_AMT, TOTAL_EXPENSE_AMT, TAX_AMT+PENALTY_AMT, VAT_AMT, MR_TAX_CALC_METHOD_CODE, BANK_CODE+' - '+BANK_ACC_NO+' - '+BANK_ACC_NAME AS [BANK_ACCOUNT] FROM app_COMMISSION_h ach with(nolock) join app a with(nolock) on a.APP_ID = ach.APP_ID where app_no = '"+appno+"' and MR_COMMISSION_RECIPIENT_TYPE_CODE = 'REFERANTOR'"), { row ->
			String Newcomdt
			ResultSetMetaData rsmd = row.getMetaData()
			colmcount = rsmd.getColumnCount()


			for(i = 0 ; i < colmcount ; i++){
				comdt = (row[i])
				//				if(i==0){
				//					AllocComRef.add(checkCommissionAllocation(instanceFOU, row[0]))
				//				}
				if(i==1){
					Newcomdt = new commissionReserveFundData.checkCommissionCode().checkReferantorName(instanceFOU, comdt)
					listComRef.add(Newcomdt)
				}
				else{
					listComRef.add(comdt)
				}


			}
		})



		result.put("ComSupp",listComSupp)
		result.put("ComSuppEmp",listComSuppEmp)
		result.put("ComRef",listComRef)
		//		result.put("AllocSupp",AllocComSupp)
		//		result.put("AllocSuppEmp",AllocComSuppEmp)
		//		result.put("AllocRef",AllocComRef)
		return result






	}

	//keyword check app view app commision allocation
	@Keyword
	public checkCommissionAllocation(Sql instance, String comid){
		//Check Referantor
		ArrayList<String> resultAlloc = new ArrayList<>()
		String comAlloc
		instance.eachRow(("select COMMISSION_AMT from APP_COMMISSION_D where app_commission_h_id = '"+comid+"'"), { row ->
			comAlloc = row[0]
			resultAlloc.add(comAlloc)

		})
		return resultAlloc

	}

	//keyword check app view app info summary commission
	@Keyword
	public checkSummaryCommission(Sql instance, String appno){
		String comdt
		ArrayList<String> resultSum = new ArrayList<>()
		instance.eachRow(("select SUM(TOTAL_COMMISSION_AMT) as [TOTAL_COMMISSION_AMT],SUM(TOTAL_COMMISSION_AFTER_TAX_AMT) as [TOTAL_TAX_BASE_AMT], SUM(TOTAL_DISBURSE_AMT) as [TOTAL_DISBURSE_AMT], SUM(TOTAL_EXPENSE_AMT) as [TOTAL_EXPENSE_AMT], SUM(TAX_AMT)+SUM(PENALTY_AMT) as [TOTAL_TAX_AMT], SUM(VAT_AMT) as [TOTAL_VAT_AMT] from app_COMMISSION_h ach with(nolock) join app a with(nolock) on a.app_id = ach.app_id where app_no = '"+appno+"'"), { row ->

			ResultSetMetaData rsmd = row.getMetaData()
			colmcount = rsmd.getColumnCount()


			for(i = 0 ; i < colmcount ; i++){
				comdt = (row[i])
				resultSum.add(comdt)
			}
		})

		return resultSum
	}

	//keyword check app view financial Detail Personal
	@Keyword
	public checkFinancialDetailPersonal (Sql instance, String appno, String name, String date){
		String appdata
		ArrayList <String> listappdata = new ArrayList<String>()
		instance.eachRow(("SELECT CASE WHEN CAST(MONTHLY_INCOME_AMT as VARCHAR(25)) = '0.00' OR CAST(MONTHLY_INCOME_AMT as VARCHAR(25)) IS NULL THEN '-' ELSE CAST(MONTHLY_INCOME_AMT as VARCHAR(25)) END AS MONINCOME, CASE WHEN CAST(MONTHLY_EXPENSE_AMT AS VARCHAR(25)) = '0.00' OR CAST(MONTHLY_EXPENSE_AMT AS VARCHAR(25)) IS NULL THEN '-' ELSE CAST(MONTHLY_EXPENSE_AMT AS VARCHAR(25)) END AS MONEXPN, ISNULL(rml.DESCR,'') AS SOURCEINCOME, CASE WHEN CAST(MONTHLY_INSTALLMENT_AMT AS VARCHAR(25)) = '0.00' OR CAST(MONTHLY_INSTALLMENT_AMT AS VARCHAR(25)) IS NULL THEN '-' ELSE CAST(MONTHLY_INSTALLMENT_AMT AS VARCHAR(25)) END AS MONTHINST, CASE WHEN CAST(SPOUSE_MONTHLY_INCOME_AMT AS VARCHAR(25)) = '0.00' OR CAST(SPOUSE_MONTHLY_INCOME_AMT AS VARCHAR(25)) IS NULL THEN '-' ELSE CAST(SPOUSE_MONTHLY_INCOME_AMT AS VARCHAR(25)) END AS SPOUINC, CASE WHEN CAST(IS_JOIN_INCOME as VARCHAR(25)) = '1' THEN 'YES' WHEN CAST(IS_JOIN_INCOME as VARCHAR(25)) = '0' THEN 'NO' ELSE CAST(IS_JOIN_INCOME as VARCHAR(25)) END AS JOININC, CASE WHEN CAST(OTHER_INCOME_AMT as VARCHAR(25)) = '0.00' OR CAST(OTHER_INCOME_AMT as VARCHAR(25)) IS NULL THEN '-' ELSE CAST(OTHER_INCOME_AMT as VARCHAR(25)) END AS OTHINCOME, CASE WHEN CAST(OTHER_MONTHLY_INSTALLMENT_AMT AS VARCHAR(25)) = '0.00' OR CAST(OTHER_MONTHLY_INSTALLMENT_AMT AS VARCHAR(25)) IS NULL THEN '-' ELSE CAST(OTHER_MONTHLY_INSTALLMENT_AMT AS VARCHAR(25)) END AS OTHMONINS, CASE WHEN CAST(TOTAL_INCOME_AMT as VARCHAR(25)) = '0.00' OR CAST(TOTAL_INCOME_AMT as VARCHAR(25)) IS NULL THEN '-' ELSE CAST(TOTAL_INCOME_AMT as VARCHAR(25)) END AS TOTALMONTHINC, CASE WHEN CAST(MONTHLY_EXPENSE_AMT + MONTHLY_INSTALLMENT_AMT + OTHER_MONTHLY_INSTALLMENT_AMT AS VARCHAR(25)) = '0.00' OR CAST(MONTHLY_EXPENSE_AMT + MONTHLY_INSTALLMENT_AMT + OTHER_MONTHLY_INSTALLMENT_AMT AS VARCHAR(25)) IS NULL THEN '-' ELSE CAST(MONTHLY_EXPENSE_AMT + MONTHLY_INSTALLMENT_AMT + OTHER_MONTHLY_INSTALLMENT_AMT AS VARCHAR(25)) END AS TOTALEXP, CASE WHEN CAST(NETT_INCOME_AMT AS VARCHAR(25)) = '0.00' OR CAST(NETT_INCOME_AMT AS VARCHAR(25)) IS NULL THEN '-' ELSE CAST(NETT_INCOME_AMT AS VARCHAR(25)) END AS NETTINC FROM APP_CUST_PERSONAL_FIN_DATA acpfd WITH(NOLOCK) JOIN APP_CUST_PERSONAL acp WITH(NOLOCK) ON acp.APP_CUST_PERSONAL_ID = acpfd.APP_CUST_PERSONAL_ID JOIN APP_CUST ac WITH(NOLOCK) ON ac.APP_CUST_ID = acp.APP_CUST_ID JOIN APP a WITH(NOLOCK) ON a.APP_ID = ac.APP_ID JOIN FOUNDATION.dbo.REF_MASTER rml WITH(NOLOCK) ON rml.MASTER_CODE = acpfd.MR_SOURCE_OF_INCOME_TYPE_CODE WHERE a.APP_NO = '"+ appno +"' AND ac.CUST_NAME = '"+ name +"' AND FORMAT(acpfd.DATE_AS_OF , 'dd-MMM-yyyy') = '"+ date +"'"), {  row ->

			ResultSetMetaData rsmd = row.getMetaData()
			colmcount = rsmd.getColumnCount()


			for(i = 0 ; i < colmcount ; i++){
				appdata = (row[i])
				listappdata.add(appdata)
			}
		})
		return listappdata
	}

	//keyword check app view financial Detail Company
	@Keyword
	public checkFinancialDetailCompany (Sql instance, String appno, String name, String date){
		String appdata
		ArrayList <String> listappdata = new ArrayList<String>()
		instance.eachRow(("SELECT CASE WHEN CAST(GROSS_MONTHLY_INCOME_AMT AS VARCHAR(10)) = '0.00' OR CAST(GROSS_MONTHLY_INCOME_AMT AS VARCHAR(10)) IS NULL THEN '-' ELSE CAST(GROSS_MONTHLY_INCOME_AMT AS VARCHAR(10)) END AS GROSSMONINCOME, CASE WHEN CAST(GROSS_MONTHLY_EXPENSE_AMT AS VARCHAR(10)) = '0.00' OR CAST(GROSS_MONTHLY_EXPENSE_AMT AS VARCHAR(10)) IS NULL THEN '-' ELSE CAST(GROSS_MONTHLY_EXPENSE_AMT AS VARCHAR(10)) END AS GROSSMONEXPENSE, ISNULL(RETURN_OF_INVESTMENT_PRCNT,'') AS RETURNOFINV, ISNULL(RETURN_OF_EQUITY_PRCNT,'') AS RETURNOFEQ, ISNULL(RETURN_OF_ASSET_PRCNT,'') AS RETURNOFASSET, ISNULL(PROFIT_MARGIN_PRCNT,'') AS PROFITMARGIN, ISNULL(CURRENT_RATIO_PRCNT,'') AS CURR_RATIO, ISNULL(DEBT_EQUITY_RATIO_PRCNT,'') AS DEBTEQRAT, ISNULL(INV_TURN_OVER_PRCNT,'') AS INVTURNOVER, ISNULL(AR_TURN_OVER_PRCNT,'') AS ARTURNOVER, ISNULL(GROWTH_PRCNT,'') AS GROWTH, CASE WHEN CAST(WORKING_CAPITAL_AMT AS VARCHAR(10)) = '0.00' OR  CAST(WORKING_CAPITAL_AMT AS VARCHAR(10)) IS NULL THEN '-' ELSE CAST(WORKING_CAPITAL_AMT AS VARCHAR(10)) END AS WORK_CPTL, CASE WHEN CAST(OTH_MONTHLY_INST_AMT AS VARCHAR(10)) = '0.00' OR CAST(OTH_MONTHLY_INST_AMT AS VARCHAR(10)) IS NULL THEN '-'  ELSE CAST(OTH_MONTHLY_INST_AMT AS VARCHAR(10)) END AS OTHMONINST, ISNULL(FORMAT(DATE_AS_OF, 'dd-MMM-yyyy'),'') AS DATEASOF, CASE WHEN CAST(REVENUE AS VARCHAR(10)) = '0.00' OR CAST(REVENUE AS VARCHAR(10)) IS NULL THEN '-' ELSE CAST(REVENUE AS VARCHAR(10)) END AS REV, CASE WHEN CAST(OPR_COST AS VARCHAR(10)) = '0.00' OR CAST(OPR_COST AS VARCHAR(10)) IS NULL THEN '-' ELSE CAST(OPR_COST AS VARCHAR(10)) END AS OPRCOST, CASE WHEN CAST(PROFIT_BEFORE_TAX AS VARCHAR(10)) = '0.00' OR CAST(PROFIT_BEFORE_TAX AS VARCHAR(10)) IS NULL THEN '-' ELSE CAST(PROFIT_BEFORE_TAX AS VARCHAR(10)) END AS PROFBFRTAX,  CASE WHEN CAST(CURR_ASSET AS VARCHAR(10)) = '0.00' OR CAST(CURR_ASSET AS VARCHAR(10)) IS NULL THEN '-' ELSE CAST(CURR_ASSET AS VARCHAR(10)) END AS CURRASSET, CASE WHEN CAST(NET_FIXED_ASSET AS VARCHAR(10)) = '0.00' OR CAST(NET_FIXED_ASSET AS VARCHAR(10)) IS NULL THEN '-' ELSE CAST(NET_FIXED_ASSET AS VARCHAR(10)) END AS NETFIXEDASSET, CASE WHEN CAST(TOTAL_ASSET AS VARCHAR(10)) = '0.00' OR  CAST(TOTAL_ASSET AS VARCHAR(10)) IS NULL THEN '-' ELSE CAST(TOTAL_ASSET AS VARCHAR(10)) END AS TOTALASSET, CASE WHEN CAST(CURR_LIABLTS AS VARCHAR(10)) = '0.00' OR CAST(CURR_LIABLTS AS VARCHAR(10)) IS NULL THEN '-' ELSE CAST(CURR_LIABLTS AS VARCHAR(10)) END AS CURRLIAB, CASE WHEN CAST(LONG_TERM_LIABLTS AS VARCHAR(10)) = '0.00' OR CAST(LONG_TERM_LIABLTS AS VARCHAR(10)) IS NULL THEN '' ELSE CAST(LONG_TERM_LIABLTS AS VARCHAR(10)) END AS LONGTERMLIAB, CASE WHEN CAST(SHAREHOLDER_EQUITY AS VARCHAR(10)) = '0.00' OR  CAST(SHAREHOLDER_EQUITY AS VARCHAR(10)) IS NULL THEN '-' ELSE CAST(SHAREHOLDER_EQUITY AS VARCHAR(10)) END AS SHRHLDREQ , CASE WHEN CAST(CURR_RATIO AS VARCHAR(10)) = '0.00' OR CAST(CURR_RATIO AS VARCHAR(10)) IS NULL THEN '-' ELSE CAST(CURR_RATIO AS VARCHAR(10)) END AS CURRRATIO FROM APP_CUST_COMPANY_FIN_DATA accfd WITH(NOLOCK) JOIN APP_CUST ac WITH(NOLOCK) ON ac.APP_CUST_ID = accfd.APP_CUST_ID JOIN APP a WITH(NOLOCK) ON a.APP_ID = ac.APP_ID WHERE a.APP_NO = '"+ appno +"' AND ac.CUST_NAME = '"+ name +"' AND FORMAT(DATE_AS_OF , 'dd-MMM-yyyy') = '"+ date +"'"), {  row ->

			ResultSetMetaData rsmd = row.getMetaData()
			colmcount = rsmd.getColumnCount()


			for(i = 0 ; i < colmcount ; i++){
				appdata = (row[i])
				listappdata.add(appdata)
			}
		})
		return listappdata
	}
}


