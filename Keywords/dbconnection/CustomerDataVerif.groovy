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

public class CustomerDataVerif {

	int colmcount
	int i

	@Keyword
	public CustomerDataPersonal (Sql instance, String name, String idtype, String custmodel){
		String customerdata
		ArrayList<String> listcustomerdata = new ArrayList<>()
		instance.eachRow(("SELECT Cust_name , BIRTH_PLACE , ID_EXPIRED_DT , MOBILE_PHN_NO_1 , FORMAT(BIRTH_DT, 'yyyy-MM-dd') , ID_NO , TAX_ID_NO , MOTHER_MAIDEN_NAME , EMAIL_1 , ADDR , AREA_CODE_4 , AREA_CODE_3 , ZIPCODE , AREA_CODE_2 , AREA_CODE_1 , CITY , MR_ID_TYPE_CODE , MR_MARITAL_STAT_CODE , MR_CUST_MODEL_CODE , MR_GENDER_CODE , MR_BUILDING_OWNERSHIP_CODE  from CUST c WITH(NOLOCK) join cust_addr addr WITH(NOLOCK) on c.CUST_ID = addr.CUST_ID JOIN cust_personal cp WITH(NOLOCK) on c.cust_ID = cp.CUST_ID where c.cust_name = '"+ name +"' and MR_ID_TYPE_CODE = '"+ idtype +"' AND MR_CUST_MODEL_CODE = '"+ custmodel +"' AND MR_CUST_ADDR_TYPE_CODE = 'legal' GROUP BY Cust_name, BIRTH_PLACE, MR_ID_TYPE_CODE, ID_EXPIRED_DT, MR_MARITAL_STAT_CODE, MOBILE_PHN_NO_1, MR_CUST_MODEL_CODE, MR_GENDER_CODE, FORMAT(BIRTH_DT, 'yyyy-MM-dd'), ID_NO, TAX_ID_NO, MOTHER_MAIDEN_NAME, EMAIL_1, ADDR, AREA_CODE_4, AREA_CODE_3, ZIPCODE, AREA_CODE_2, AREA_CODE_1, CITY, MR_BUILDING_OWNERSHIP_CODE"), {  row ->

			ResultSetMetaData rsmd = row.getMetaData()
			colmcount = rsmd.getColumnCount()


			for(i = 0 ; i < colmcount ; i++){
				customerdata = (row[i])
				listcustomerdata.add(customerdata)
			}
		})
		return listcustomerdata
	}

	@Keyword
	public CustomerDataCompany (Sql instance, String name, String idno){
		String customerdata
		ArrayList<String> listcustomerdata = new ArrayList<>()
		instance.eachRow(("SELECT Cust_name , TAX_ID_NO , ADDR , AREA_CODE_4 , AREA_CODE_3 , ZIPCODE , AREA_CODE_2 , AREA_CODE_1 , CITY , MR_COMPANY_TYPE_CODE  , MR_CUST_MODEL_CODE , MR_BUILDING_OWNERSHIP_CODE  from CUST c WITH(NOLOCK) join cust_addr addr WITH(NOLOCK) on c.CUST_ID = addr.CUST_ID JOIN CUST_COMPANY cc WITH(NOLOCK) on c.cust_ID = cc.CUST_ID where c.cust_name = '"+ name +"' AND ID_NO = '"+ idno +"' AND MR_CUST_ADDR_TYPE_CODE = 'legal' GROUP BY Cust_name, TAX_ID_NO, MR_COMPANY_TYPE_CODE ,MR_CUST_MODEL_CODE, ADDR, AREA_CODE_4, AREA_CODE_3, ZIPCODE, AREA_CODE_2, AREA_CODE_1, CITY, MR_BUILDING_OWNERSHIP_CODE"), {  row ->

			ResultSetMetaData rsmd = row.getMetaData()
			colmcount = rsmd.getColumnCount()


			for(i = 0 ; i < colmcount ; i++){
				customerdata = (row[i])
				listcustomerdata.add(customerdata)
			}
		})
		return listcustomerdata
	}

	@Keyword
	public FamilyDataPersonal (Sql instance, String name, String idno){
		String customerdata
		ArrayList<String> listcustomerdata = new ArrayList<>()
		instance.eachRow(("SELECT Cust_name , BIRTH_PLACE , MR_ID_TYPE_CODE , ID_EXPIRED_DT , MR_MARITAL_STAT_CODE , MOBILE_PHN_NO_1 , MR_CUST_MODEL_CODE , MR_GENDER_CODE , FORMAT(BIRTH_DT, 'yyyy-MM-dd') , ID_NO , TAX_ID_NO , MOTHER_MAIDEN_NAME , EMAIL_1 , MR_NATIONALITY_CODE , UPPER(COUNTRY_NAME) , ADDR , AREA_CODE_4 , AREA_CODE_3 , ZIPCODE , AREA_CODE_2 , AREA_CODE_1 , CITY , MR_BUILDING_OWNERSHIP_CODE  from CUST c WITH(NOLOCK) join cust_addr addr WITH(NOLOCK) on c.CUST_ID = addr.CUST_ID join cust_personal cp WITH(NOLOCK) on c.cust_ID = cp.CUST_ID join REF_COUNTRY country WITH(NOLOCK) on cp.WNA_COUNTRY_CODE = country.COUNTRY_CODE where c.cust_name = '"+ name + "' AND ID_NO = '"+ idno +"' AND MR_CUST_ADDR_TYPE_CODE = 'legal' GROUP BY Cust_name , BIRTH_PLACE , MR_ID_TYPE_CODE , ID_EXPIRED_DT , MR_MARITAL_STAT_CODE , MOBILE_PHN_NO_1 , MR_CUST_MODEL_CODE , MR_GENDER_CODE , FORMAT(BIRTH_DT, 'yyyy-MM-dd') , ID_NO , TAX_ID_NO , MOTHER_MAIDEN_NAME , EMAIL_1 , MR_NATIONALITY_CODE , UPPER(COUNTRY_NAME) , ADDR , AREA_CODE_4 , AREA_CODE_3 , ZIPCODE , AREA_CODE_2 , AREA_CODE_1 , CITY , MR_BUILDING_OWNERSHIP_CODE"), {  row ->

			ResultSetMetaData rsmd = row.getMetaData()
			colmcount = rsmd.getColumnCount()


			for(i = 0 ; i < colmcount ; i++){
				customerdata = (row[i])
				listcustomerdata.add(customerdata)
			}
		})
		return listcustomerdata
	}

	//	@Keyword
	//	public CheckCustomerExisting (Sql instance, String appno){
	//		int checkresult
	//		instance.eachRow(("USE LOS SELECT COUNT(IS_EXISTING_CUST) FROM APP_CUST a JOIN APP b on a.APP_ID = b.APP_ID WHERE APP_NO = '"+ appno + "' and IS_CUSTOMER = 1"), {  row ->
	//
	//			checkresult = (row[0])
	//		})
	//		return checkresult
	//	}

	@Keyword
	public CustomerDataStoreDBPersonal (Sql instance, String appno){
		String customerdata
		ArrayList<String> listcustomerdata = new ArrayList<>()
		//instance.eachRow(("SELECT b.PROD_OFFERING_CODE , Cust_name , BIRTH_PLACE , h.REF_MASTER_NAME , FORMAT(ID_EXPIRED_DT, 'MM/dd/yyyy') , MR_MARITAL_STAT_CODE , MOBILE_PHN_NO_1 , f.REF_MASTER_NAME , MR_GENDER_CODE , FORMAT(BIRTH_DT, 'MM/dd/yyyy') , ID_NO , TAX_ID_NO , MOTHER_MAIDEN_NAME , EMAIL_1 , e.ATTR_VALUE , SUBQ.ATTR_VALUE , ADDR , AREA_CODE_4 , AREA_CODE_3 , ZIPCODE , AREA_CODE_2 , AREA_CODE_1 , CITY , g.REF_MASTER_NAME  FROM APP_CUST a JOIN app b ON a.APP_ID = b.APP_ID JOIN APP_CUST_PERSONAL c ON a.APP_CUST_ID = c.APP_CUST_ID JOIN APP_CUST_ADDR d ON a.APP_CUST_ID = d.APP_CUST_ID JOIN APP_CUST_ATTR_CONTENT e ON e.APP_CUST_ID = a.APP_CUST_ID JOIN REF_MASTER_LOS f ON a.MR_CUST_MODEL_CODE = f.REF_MASTER_CODE JOIN REF_MASTER_LOS g ON d.MR_HOUSE_OWNERSHIP_CODE = g.REF_MASTER_CODE JOIN REF_MASTER_LOS h ON a.MR_ID_TYPE_CODE = h.REF_MASTER_CODE, (SELECT ATTR_VALUE, a.APP_ID FROM APP_CUST_ATTR_CONTENT g JOIN APP_CUST a ON g.APP_CUST_ID = a.APP_CUST_ID JOIN app b ON a.APP_ID = b.APP_ID WHERE APP_NO = '"+ appno +"'AND IS_CUSTOMER = 1 AND g.REF_ATTR_CODE = 'AUTH_AML') as SUBQ WHERE a.APP_ID = SUBQ.APP_ID AND IS_CUSTOMER = 1 AND MR_CUST_ADDR_TYPE_CODE = 'LEGAL' AND MR_CUST_TYPE_CODE = 'PERSONAL' AND e.REF_ATTR_CODE = 'AML_CUST_DEPARTMENT' AND f.REF_MASTER_TYPE_CODE = 'CUST_MODEL' AND g.REF_MASTER_TYPE_CODE = 'BUILDING_OWNERSHIP' AND h.REF_MASTER_TYPE_CODE = 'ID_TYPE'"), {  row ->
		instance.eachRow(("SELECT [ProductOffering] , [CustName] , [BirthPlace] , [ID_TYPE] ,[IDExpiredDate] , [MaritalStatus] , [MobilePHN1] , [CUST_MODEL] , [Gender] , [BirthDate] , [IdNo] , [TaxID] , [MotherMaidenName] ,[Email1] , [Addr] , [RT] , [RW] , [Zipcode] , [Kecamatan] , [Kelurahan] , [Kota] , [BUILDING_OWNERSHIP]  FROM (SELECT [ProductOffering], [CustName], [BirthPlace], [IDExpiredDate], [MaritalStatus], [MobilePHN1], [Gender], [BirthDate], [IdNo], [TaxID], [MotherMaidenName], [Email1], [Addr], [RT], [RW], [Zipcode], [Kecamatan], [Kelurahan], [Kota], mastername.Code, REF_MASTER_NAME FROM (select [ProductOffering], [CustName], [BirthPlace], [IDExpiredDate], [MaritalStatus], [MobilePHN1], [Gender], [BirthDate], [IdNo], [TaxID], [MotherMaidenName], [Email1], [Addr], [RT], [RW], [Zipcode], [Kecamatan], [Kelurahan], [Kota], [Code], value FROM (SELECT a.PROD_OFFERING_CODE AS [ProductOffering], Cust_name AS [CustName], BIRTH_PLACE AS [BirthPlace], ac.MR_ID_TYPE_CODE AS 'ID_TYPE', FORMAT(ID_EXPIRED_DT, 'MM/dd/yyyy') AS [IDExpiredDate], MR_MARITAL_STAT_CODE AS [MaritalStatus], MOBILE_PHN_NO_1 AS [MobilePHN1], ac.MR_CUST_MODEL_CODE AS 'CUST_MODEL', MR_GENDER_CODE AS [Gender], FORMAT(BIRTH_DT, 'MM/dd/yyyy') AS [BirthDate], ID_NO AS [IdNo], TAX_ID_NO AS [TaxID], MOTHER_MAIDEN_NAME AS [MotherMaidenName], EMAIL_1 AS [Email1],  ADDR AS [Addr], AREA_CODE_4 AS [RT], AREA_CODE_3 AS [RW], ZIPCODE AS [Zipcode], AREA_CODE_2 AS [Kecamatan], AREA_CODE_1 AS [Kelurahan], CITY AS [Kota], addr.MR_HOUSE_OWNERSHIP_CODE AS 'BUILDING_OWNERSHIP' FROM APP_CUST ac JOIN APP a WITH(NOLOCK) ON ac.APP_ID = a.APP_ID JOIN APP_CUST_PERSONAL acp WITH(NOLOCK) ON ac.APP_CUST_ID = acp.APP_CUST_ID JOIN APP_CUST_ADDR addr WITH(NOLOCK) ON ac.APP_CUST_ID = addr.APP_CUST_ID WHERE ac.IS_CUSTOMER = 1 AND APP_NO = '"+ appno +"' AND MR_CUST_ADDR_TYPE_CODE = 'LEGAL' AND MR_CUST_TYPE_CODE = 'PERSONAL') as Orig unpivot (value for [Code] in ([ID_TYPE],[CUST_MODEL],[BUILDING_OWNERSHIP]))as unpiv) as mastername JOIN REF_MASTER_LOS rf WITH(NOLOCK) ON rf.REF_MASTER_Code = mastername.value WHERE rf.IS_ACTIVE = '1') AS ref PIVOT (MAX(ref.REF_MASTER_NAME) for [Code] in ([ID_TYPE],[CUST_MODEL],[BUILDING_OWNERSHIP])) as piv"), {  row ->

			ResultSetMetaData rsmd = row.getMetaData()
			colmcount = rsmd.getColumnCount()


			for(i = 0 ; i < colmcount ; i++){
				customerdata = (row[i])
				listcustomerdata.add(customerdata)
			}
		})
		return listcustomerdata
	}

	@Keyword
	public CustomerDataStoreDBPersonalLookUp (Sql instance, String appno, String name){
		String customerdata
		ArrayList<String> listcustomerdata = new ArrayList<>()
		instance.eachRow(("SELECT Cust_name , BIRTH_PLACE , FORMAT(ID_EXPIRED_DT, 'yyyy-MM-dd') , MOBILE_PHN_NO_1 , FORMAT(BIRTH_DT, 'yyyy-MM-dd') , ID_NO , TAX_ID_NO , MOTHER_MAIDEN_NAME , EMAIL_1 , ADDR , AREA_CODE_4 , AREA_CODE_3 , ZIPCODE , AREA_CODE_2 , AREA_CODE_1 , CITY , ac.MR_ID_TYPE_CODE ,MR_MARITAL_STAT_CODE , MR_CUST_MODEL_CODE , MR_GENDER_CODE ,aca.MR_HOUSE_OWNERSHIP_CODE  FROM APP_CUST ac WITH(NOLOCK) JOIN app a WITH(NOLOCK) ON ac.APP_ID = a.APP_ID JOIN APP_CUST_PERSONAL acp WITH(NOLOCK) ON ac.APP_CUST_ID = acp.APP_CUST_ID JOIN APP_CUST_ADDR aca WITH(NOLOCK) ON ac.APP_CUST_ID = aca.APP_CUST_ID where APP_NO = '"+appno+"' AND IS_CUSTOMER = 1 AND Cust_name = '"+name+"' AND aca.MR_CUST_ADDR_TYPE_CODE = 'LEGAL'"), {  row ->
			ResultSetMetaData rsmd = row.getMetaData()
			colmcount = rsmd.getColumnCount()


			for(i = 0 ; i < colmcount ; i++){
				customerdata = (row[i])
				listcustomerdata.add(customerdata)
			}
		})
		return listcustomerdata
	}

	@Keyword
	public FamilyDataStoreDBPersonal (Sql instance, String appno, String name){
		String familydata
		ArrayList<String> listcustomerdata = new ArrayList<>()
		//		instance.eachRow(("SELECT MR_CUST_RELATIONSHIP_CODE  ,Cust_name , BIRTH_PLACE , g.REF_MASTER_NAME , FORMAT(ID_EXPIRED_DT, 'MM/dd/yyyy') , MR_MARITAL_STAT_CODE , MOBILE_PHN_NO_1 , h.REF_MASTER_NAME , e.MR_PROFESSION_CODE , FORMAT(e.ESTABLISHMENT_DT, 'MM/dd/yyyy') , MR_GENDER_CODE , FORMAT(BIRTH_DT, 'MM/dd/yyyy') , ID_NO , TAX_ID_NO , MOTHER_MAIDEN_NAME , EMAIL_1 , MR_NATIONALITY_CODE , NATIONALITY_COUNTRY_CODE , MR_JOB_POSITION_CODE , f.ATTR_VALUE , SUBQ.ATTR_VALUE , ADDR , AREA_CODE_4 , AREA_CODE_3 , ZIPCODE , AREA_CODE_2 , AREA_CODE_1 , CITY , i.REF_MASTER_NAME  FROM APP_CUST a JOIN app b ON a.APP_ID = b.APP_ID JOIN APP_CUST_PERSONAL c ON a.APP_CUST_ID = c.APP_CUST_ID JOIN APP_CUST_ADDR d ON a.APP_CUST_ID = d.APP_CUST_ID JOIN APP_CUST_PERSONAL_JOB_DATA e ON c.APP_CUST_PERSONAL_ID = e.APP_CUST_PERSONAL_ID JOIN APP_CUST_ATTR_CONTENT f ON f.APP_CUST_ID = a.APP_CUST_ID JOIN REF_MASTER_LOS g ON a.MR_ID_TYPE_CODE = g.REF_MASTER_CODE JOIN REF_MASTER_LOS h ON a.MR_CUST_MODEL_CODE = h.REF_MASTER_CODE JOIN REF_MASTER_LOS i ON d.MR_HOUSE_OWNERSHIP_CODE = i.REF_MASTER_CODE, (SELECT ATTR_VALUE, a.APP_ID FROM APP_CUST_ATTR_CONTENT g JOIN APP_CUST a ON g.APP_CUST_ID = a.APP_CUST_ID JOIN app b ON a.APP_ID = b.APP_ID WHERE APP_NO = '"+ appno +"' AND IS_FAMILY = 1 AND g.REF_ATTR_CODE = 'AUTH_AML') as SUBQ where a.APP_ID = SUBQ.APP_ID AND IS_FAMILY = 1 AND MR_CUST_ADDR_TYPE_CODE = 'LEGAL' AND MR_CUST_TYPE_CODE = 'PERSONAL' AND f.REF_ATTR_CODE = 'AML_CUST_DEPARTMENT' AND g.REF_MASTER_TYPE_CODE = 'ID_TYPE' AND h.REF_MASTER_TYPE_CODE = 'CUST_MODEL' AND i.REF_MASTER_TYPE_CODE = 'BUILDING_OWNERSHIP'"), {  row ->
		instance.eachRow(("SELECT [Relationship] , [CustName] , [BirthPlace] , [ID_TYPE] ,[IDExpiredDate] , [MaritalStatus] , [MobilePHN1] , [CUST_MODEL] , [ProfessionCode] , [EstablishmentDate] , [Gender] , [BirthDate] , [Idno] , [TaxID] , [MotherMaidenName] , [Email1] , [NationalityCode] , [CountryCode] , [JobPositionCode] , [Addr] , [RT] , [RW] , [Zipcode] , [Kecamatan] , [Kelurahan] , [Kota] , [BUILDING_OWNERSHIP]  FROM (SELECT [Relationship], [CustName], [BirthPlace], [IDExpiredDate], [MaritalStatus], [MobilePHN1], [ProfessionCode], [EstablishmentDate], [Gender], [BirthDate], [Idno], [TaxID], [MotherMaidenName], [Email1], [NationalityCode], [CountryCode], [JobPositionCode], [Addr], [RT], [RW], [Zipcode], [Kecamatan], [Kelurahan], [Kota], mastername.Code, REF_MASTER_NAME FROM (select [Relationship], [CustName], [BirthPlace], [IDExpiredDate], [MaritalStatus], [MobilePHN1], [ProfessionCode], [EstablishmentDate], [Gender], [BirthDate], [Idno], [TaxID], [MotherMaidenName], [Email1], [NationalityCode], [CountryCode], [JobPositionCode], [Addr], [RT], [RW], [Zipcode], [Kecamatan], [Kelurahan], [Kota], [Code], value FROM (SELECT MR_CUST_RELATIONSHIP_CODE AS [Relationship] ,Cust_name AS [CustName], BIRTH_PLACE AS [BirthPlace], ac.MR_ID_TYPE_CODE AS 'ID_TYPE', FORMAT(ID_EXPIRED_DT, 'MM/dd/yyyy') AS [IDExpiredDate], MR_MARITAL_STAT_CODE AS [MaritalStatus], MOBILE_PHN_NO_1 AS [MobilePHN1], MR_CUST_MODEL_CODE AS 'CUST_MODEL', acpjd.MR_PROFESSION_CODE AS [ProfessionCode], FORMAT(acpjd.ESTABLISHMENT_DT, 'MM/dd/yyyy') AS [EstablishmentDate], MR_GENDER_CODE AS [Gender], FORMAT(BIRTH_DT, 'MM/dd/yyyy') AS [BirthDate], ID_NO AS [Idno], TAX_ID_NO AS [TaxID], MOTHER_MAIDEN_NAME AS [MotherMaidenName], EMAIL_1 AS [Email1], MR_NATIONALITY_CODE AS [NationalityCode], NATIONALITY_COUNTRY_CODE AS [CountryCode], MR_JOB_POSITION_CODE AS [JobPositionCode], ADDR AS [Addr], AREA_CODE_4 AS [RT], AREA_CODE_3 AS [RW], ZIPCODE AS [Zipcode], AREA_CODE_2 AS [Kecamatan], AREA_CODE_1 AS [Kelurahan], CITY AS [Kota], aca.MR_HOUSE_OWNERSHIP_CODE AS 'BUILDING_OWNERSHIP' FROM APP_CUST ac WITH(NOLOCK) JOIN app a WITH(NOLOCK) ON ac.APP_ID = a.APP_ID JOIN APP_CUST_PERSONAL acp WITH(NOLOCK) ON ac.APP_CUST_ID = acp.APP_CUST_ID JOIN APP_CUST_ADDR aca WITH(NOLOCK) ON ac.APP_CUST_ID = aca.APP_CUST_ID JOIN APP_CUST_PERSONAL_JOB_DATA acpjd WITH(NOLOCK) ON acp.APP_CUST_PERSONAL_ID = acpjd.APP_CUST_PERSONAL_ID where APP_NO = '"+ appno +"' AND IS_FAMILY = 1 AND Cust_name = '"+ name +"' and MR_CUST_ADDR_TYPE_CODE = 'legal') as Orig unpivot (value for [Code] in ([ID_TYPE],[CUST_MODEL],[BUILDING_OWNERSHIP]))as unpiv) as mastername JOIN REF_MASTER_LOS rf WITH(NOLOCK) ON rf.REF_MASTER_Code = mastername.value WHERE rf.IS_ACTIVE = '1') AS ref PIVOT (MAX(ref.REF_MASTER_NAME) for [Code] in ([ID_TYPE],[CUST_MODEL],[BUILDING_OWNERSHIP])) as piv"), {  row ->
			ResultSetMetaData rsmd = row.getMetaData()
			colmcount = rsmd.getColumnCount()


			for(i = 0 ; i < colmcount ; i++){
				familydata = (row[i])
				listcustomerdata.add(familydata)
			}
		})
		return listcustomerdata
	}

	@Keyword
	public FamilyDataStoreDBPersonalLookUp (Sql instance, String appno, String name, String proffesionname){
		String familydata
		ArrayList<String> listcustomerdata = new ArrayList<>()
		//		instance.eachRow(("SELECT MR_CUST_RELATIONSHIP_CODE  ,Cust_name , BIRTH_PLACE , g.REF_MASTER_NAME , FORMAT(ID_EXPIRED_DT, 'MM/dd/yyyy') , MR_MARITAL_STAT_CODE , MOBILE_PHN_NO_1 , h.REF_MASTER_NAME , e.MR_PROFESSION_CODE , FORMAT(e.ESTABLISHMENT_DT, 'MM/dd/yyyy') , MR_GENDER_CODE , FORMAT(BIRTH_DT, 'MM/dd/yyyy') , ID_NO , TAX_ID_NO , MOTHER_MAIDEN_NAME , EMAIL_1 , MR_NATIONALITY_CODE , NATIONALITY_COUNTRY_CODE , MR_JOB_POSITION_CODE , f.ATTR_VALUE , SUBQ.ATTR_VALUE , ADDR , AREA_CODE_4 , AREA_CODE_3 , ZIPCODE , AREA_CODE_2 , AREA_CODE_1 , CITY , i.REF_MASTER_NAME  FROM APP_CUST a JOIN app b ON a.APP_ID = b.APP_ID JOIN APP_CUST_PERSONAL c ON a.APP_CUST_ID = c.APP_CUST_ID JOIN APP_CUST_ADDR d ON a.APP_CUST_ID = d.APP_CUST_ID JOIN APP_CUST_PERSONAL_JOB_DATA e ON c.APP_CUST_PERSONAL_ID = e.APP_CUST_PERSONAL_ID JOIN APP_CUST_ATTR_CONTENT f ON f.APP_CUST_ID = a.APP_CUST_ID JOIN REF_MASTER_LOS g ON a.MR_ID_TYPE_CODE = g.REF_MASTER_CODE JOIN REF_MASTER_LOS h ON a.MR_CUST_MODEL_CODE = h.REF_MASTER_CODE JOIN REF_MASTER_LOS i ON d.MR_HOUSE_OWNERSHIP_CODE = i.REF_MASTER_CODE, (SELECT ATTR_VALUE, a.APP_ID FROM APP_CUST_ATTR_CONTENT g JOIN APP_CUST a ON g.APP_CUST_ID = a.APP_CUST_ID JOIN app b ON a.APP_ID = b.APP_ID WHERE APP_NO = '"+ appno +"' AND IS_FAMILY = 1 AND g.REF_ATTR_CODE = 'AUTH_AML') as SUBQ where a.APP_ID = SUBQ.APP_ID AND IS_FAMILY = 1 AND MR_CUST_ADDR_TYPE_CODE = 'LEGAL' AND MR_CUST_TYPE_CODE = 'PERSONAL' AND f.REF_ATTR_CODE = 'AML_CUST_DEPARTMENT' AND g.REF_MASTER_TYPE_CODE = 'ID_TYPE' AND h.REF_MASTER_TYPE_CODE = 'CUST_MODEL' AND i.REF_MASTER_TYPE_CODE = 'BUILDING_OWNERSHIP'"), {  row ->

		instance.eachRow(("SELECT Cust_name , BIRTH_PLACE , ac.MR_ID_TYPE_CODE , FORMAT(ID_EXPIRED_DT, 'yyyy-MM-dd') , MR_MARITAL_STAT_CODE , MOBILE_PHN_NO_1 , ac.MR_CUST_MODEL_CODE , MR_GENDER_CODE ,FORMAT(BIRTH_DT, 'yyyy-MM-dd') , ID_NO , TAX_ID_NO , MOTHER_MAIDEN_NAME , EMAIL_1 , MR_NATIONALITY_CODE , rc.COUNTRY_NAME , ADDR , AREA_CODE_4 , AREA_CODE_3 , ZIPCODE , AREA_CODE_2 , AREA_CODE_1 , CITY , aca.MR_HOUSE_OWNERSHIP_CODE , MR_CUST_RELATIONSHIP_CODE  , rpl.PROFESSION_NAME , REF_MASTER_NAME , FORMAT(ESTABLISHMENT_DT,'yyyy-MM-dd')  FROM APP_CUST ac WITH(NOLOCK) JOIN app a WITH(NOLOCK) ON ac.APP_ID = a.APP_ID JOIN APP_CUST_PERSONAL acp WITH(NOLOCK) ON ac.APP_CUST_ID = acp.APP_CUST_ID JOIN APP_CUST_ADDR aca WITH(NOLOCK) ON ac.APP_CUST_ID = aca.APP_CUST_ID JOIN v_ref_country rc with(nolock) on rc.country_code = CASE WHEN acp.mr_nationality_code = 'FOREIGNER' THEN acp.nationality_country_code ELSE 'INA' END join APP_CUST_PERSONAL_JOB_DATA  acpjd with(nolock) on acpjd.APP_CUST_PERSONAL_ID = acp.APP_CUST_PERSONAL_ID JOIN REF_PROFESSION_LOS rpl on rpl.PROFESSION_CODE = acpjd.MR_PROFESSION_CODE join ref_master_Los rml on rml.REF_MASTER_CODE = acpjd.MR_JOB_POSITION_CODE where APP_NO = '"+ appno +"' AND IS_FAMILY = 1 AND Cust_name = '"+ name +"' and MR_CUST_ADDR_TYPE_CODE = 'legal' and ref_master_type_code = 'JOB_POSITION' AND PROFESSION_NAME = '"+ proffesionname +"' GROUP BY Cust_name , BIRTH_PLACE , ac.MR_ID_TYPE_CODE , FORMAT(ID_EXPIRED_DT, 'yyyy-MM-dd') , MR_MARITAL_STAT_CODE , MOBILE_PHN_NO_1 , ac.MR_CUST_MODEL_CODE , MR_GENDER_CODE ,FORMAT(BIRTH_DT, 'yyyy-MM-dd') , ID_NO , TAX_ID_NO , MOTHER_MAIDEN_NAME , EMAIL_1 , MR_NATIONALITY_CODE , rc.COUNTRY_NAME , ADDR , AREA_CODE_4 , AREA_CODE_3 , ZIPCODE , AREA_CODE_2 , AREA_CODE_1 , CITY , aca.MR_HOUSE_OWNERSHIP_CODE , MR_CUST_RELATIONSHIP_CODE  , rpl.PROFESSION_NAME , REF_MASTER_NAME , FORMAT(ESTABLISHMENT_DT,'yyyy-MM-dd')"), {  row ->

			ResultSetMetaData rsmd = row.getMetaData()
			colmcount = rsmd.getColumnCount()


			for(i = 0 ; i < colmcount ; i++){
				familydata = (row[i])
				listcustomerdata.add(familydata)
			}
		})


		return listcustomerdata

	}


	@Keyword
	public GuarantorDataStoreDBPersonal (Sql instance, String appno, String name){
		String guarantordata
		ArrayList<String> listcustomerdata = new ArrayList<>()
		//		instance.eachRow(("SELECT MR_CUST_RELATIONSHIP_CODE  ,Cust_name , BIRTH_PLACE , f.REF_MASTER_NAME , FORMAT(ID_EXPIRED_DT, 'MM/dd/yyyy') , MR_MARITAL_STAT_CODE , MOBILE_PHN_NO_1 , g.REF_MASTER_NAME , MR_GENDER_CODE , FORMAT(BIRTH_DT, 'MM/dd/yyyy') , ID_NO , TAX_ID_NO , MOTHER_MAIDEN_NAME , EMAIL_1 , e.ATTR_VALUE , SUBQ.ATTR_VALUE , ADDR , AREA_CODE_4 , AREA_CODE_3 , ZIPCODE , AREA_CODE_2 , AREA_CODE_1 , CITY , h.REF_MASTER_NAME  FROM APP_CUST a JOIN app b ON a.APP_ID = b.APP_ID JOIN APP_CUST_PERSONAL c ON a.APP_CUST_ID = c.APP_CUST_ID JOIN APP_CUST_ADDR d ON a.APP_CUST_ID = d.APP_CUST_ID JOIN APP_CUST_ATTR_CONTENT e ON e.APP_CUST_ID = a.APP_CUST_ID JOIN REF_MASTER_LOS f ON a.MR_ID_TYPE_CODE = f.REF_MASTER_CODE JOIN REF_MASTER_LOS g ON a.MR_CUST_MODEL_CODE = g.REF_MASTER_CODE JOIN REF_MASTER_LOS h ON d.MR_HOUSE_OWNERSHIP_CODE = h.REF_MASTER_CODE, (SELECT ATTR_VALUE, a.APP_ID FROM APP_CUST_ATTR_CONTENT g JOIN APP_CUST a ON g.APP_CUST_ID = a.APP_CUST_ID JOIN app b ON a.APP_ID = b.APP_ID WHERE APP_NO = '"+ appno +"' AND IS_GUARANTOR = 1 AND g.REF_ATTR_CODE = 'AUTH_AML') as SUBQ WHERE APP_NO = '" + appno +"' AND IS_GUARANTOR = 1 AND MR_CUST_ADDR_TYPE_CODE = 'LEGAL' AND MR_CUST_TYPE_CODE = 'PERSONAL' AND e.REF_ATTR_CODE = 'AML_CUST_DEPARTMENT' AND f.REF_MASTER_TYPE_CODE = 'ID_TYPE' AND g.REF_MASTER_TYPE_CODE = 'CUST_MODEL' AND h.REF_MASTER_TYPE_CODE = 'BUILDING_OWNERSHIP'"), {  row ->
		instance.eachRow(("SELECT [Relationship] , [CustName] , [BirthPlace] , [ID_TYPE] ,[IDExpiredDate] , [MaritalStatus] , [MobilePHN1] , [CUST_MODEL] , [Gender] , [BirthDate] , [Idno] , [TaxID] , [MotherMaidenName] , [Email1] , [Addr] , [RT] , [RW] , [Zipcode] , [Kecamatan] , [Kelurahan] , [Kota] , [BUILDING_OWNERSHIP]  FROM (SELECT [Relationship], [CustName], [BirthPlace], [IDExpiredDate], [MaritalStatus], [MobilePHN1], [Gender], [BirthDate], [Idno], [TaxID], [MotherMaidenName], [Email1], [Addr], [RT], [RW], [Zipcode], [Kecamatan], [Kelurahan], [Kota], mastername.Code, REF_MASTER_NAME FROM (select [Relationship], [CustName], [BirthPlace], [IDExpiredDate], [MaritalStatus], [MobilePHN1], [Gender], [BirthDate], [Idno], [TaxID], [MotherMaidenName], [Email1], [Addr], [RT], [RW], [Zipcode], [Kecamatan], [Kelurahan], [Kota], [Code], value FROM (SELECT MR_CUST_RELATIONSHIP_CODE AS [Relationship] ,Cust_name AS [CustName], BIRTH_PLACE AS [BirthPlace], ac.MR_ID_TYPE_CODE AS 'ID_TYPE', FORMAT(ID_EXPIRED_DT, 'MM/dd/yyyy') AS [IDExpiredDate], MR_MARITAL_STAT_CODE AS [MaritalStatus], MOBILE_PHN_NO_1 AS [MobilePHN1], MR_CUST_MODEL_CODE AS 'CUST_MODEL', MR_GENDER_CODE AS [Gender], FORMAT(BIRTH_DT, 'MM/dd/yyyy') AS [BirthDate], ID_NO AS [Idno], TAX_ID_NO AS [TaxID], MOTHER_MAIDEN_NAME AS [MotherMaidenName], EMAIL_1 AS [Email1], ADDR AS [Addr], AREA_CODE_4 AS [RT], AREA_CODE_3 AS [RW], ZIPCODE AS [Zipcode], AREA_CODE_2 AS [Kecamatan], AREA_CODE_1 AS [Kelurahan], CITY AS [Kota], aca.MR_HOUSE_OWNERSHIP_CODE AS 'BUILDING_OWNERSHIP' FROM APP_CUST ac WITH(NOLOCK) JOIN app a WITH(NOLOCK) ON ac.APP_ID = a.APP_ID JOIN APP_CUST_PERSONAL acp WITH(NOLOCK) ON ac.APP_CUST_ID = acp.APP_CUST_ID JOIN APP_CUST_ADDR aca WITH(NOLOCK) ON ac.APP_CUST_ID = aca.APP_CUST_ID where APP_NO = '"+ appno +"' AND IS_GUARANTOR = 1 AND Cust_name = '"+ name +"' AND aca.MR_CUST_ADDR_TYPE_CODE = 'LEGAL') as Orig unpivot (value for [Code] in ([ID_TYPE],[CUST_MODEL],[BUILDING_OWNERSHIP]))as unpiv) as mastername JOIN REF_MASTER_LOS rf WITH(NOLOCK) ON rf.REF_MASTER_Code = mastername.value WHERE rf.IS_ACTIVE = '1') AS ref PIVOT (MAX(ref.REF_MASTER_NAME) for [Code] in ([ID_TYPE],[CUST_MODEL],[BUILDING_OWNERSHIP])) as piv"), {  row ->
			ResultSetMetaData rsmd = row.getMetaData()
			colmcount = rsmd.getColumnCount()


			for(i = 0 ; i < colmcount ; i++){
				guarantordata = (row[i])
				listcustomerdata.add(guarantordata)
			}
		})
		return listcustomerdata
	}

	@Keyword
	public GuarantorDataStoreDBPersonalLookUp (Sql instance, String appno, String name){
		String guarantordata
		ArrayList<String> listcustomerdata = new ArrayList<>()
		//		instance.eachRow(("SELECT MR_CUST_RELATIONSHIP_CODE  ,Cust_name , BIRTH_PLACE , f.REF_MASTER_NAME , FORMAT(ID_EXPIRED_DT, 'MM/dd/yyyy') , MR_MARITAL_STAT_CODE , MOBILE_PHN_NO_1 , g.REF_MASTER_NAME , MR_GENDER_CODE , FORMAT(BIRTH_DT, 'MM/dd/yyyy') , ID_NO , TAX_ID_NO , MOTHER_MAIDEN_NAME , EMAIL_1 , e.ATTR_VALUE , SUBQ.ATTR_VALUE , ADDR , AREA_CODE_4 , AREA_CODE_3 , ZIPCODE , AREA_CODE_2 , AREA_CODE_1 , CITY , h.REF_MASTER_NAME  FROM APP_CUST a JOIN app b ON a.APP_ID = b.APP_ID JOIN APP_CUST_PERSONAL c ON a.APP_CUST_ID = c.APP_CUST_ID JOIN APP_CUST_ADDR d ON a.APP_CUST_ID = d.APP_CUST_ID JOIN APP_CUST_ATTR_CONTENT e ON e.APP_CUST_ID = a.APP_CUST_ID JOIN REF_MASTER_LOS f ON a.MR_ID_TYPE_CODE = f.REF_MASTER_CODE JOIN REF_MASTER_LOS g ON a.MR_CUST_MODEL_CODE = g.REF_MASTER_CODE JOIN REF_MASTER_LOS h ON d.MR_HOUSE_OWNERSHIP_CODE = h.REF_MASTER_CODE, (SELECT ATTR_VALUE, a.APP_ID FROM APP_CUST_ATTR_CONTENT g JOIN APP_CUST a ON g.APP_CUST_ID = a.APP_CUST_ID JOIN app b ON a.APP_ID = b.APP_ID WHERE APP_NO = '"+ appno +"' AND IS_GUARANTOR = 1 AND g.REF_ATTR_CODE = 'AUTH_AML') as SUBQ WHERE APP_NO = '" + appno +"' AND IS_GUARANTOR = 1 AND MR_CUST_ADDR_TYPE_CODE = 'LEGAL' AND MR_CUST_TYPE_CODE = 'PERSONAL' AND e.REF_ATTR_CODE = 'AML_CUST_DEPARTMENT' AND f.REF_MASTER_TYPE_CODE = 'ID_TYPE' AND g.REF_MASTER_TYPE_CODE = 'CUST_MODEL' AND h.REF_MASTER_TYPE_CODE = 'BUILDING_OWNERSHIP'"), {  row ->
		instance.eachRow(("SELECT Cust_name , BIRTH_PLACE , FORMAT(ID_EXPIRED_DT, 'yyyy-MM-dd') , MOBILE_PHN_NO_1 , FORMAT(BIRTH_DT, 'yyyy-MM-dd') , ID_NO , TAX_ID_NO , MOTHER_MAIDEN_NAME , EMAIL_1 , ADDR , AREA_CODE_4 , AREA_CODE_3 , ZIPCODE , AREA_CODE_2 , AREA_CODE_1 , CITY , ac.MR_ID_TYPE_CODE ,MR_MARITAL_STAT_CODE , MR_CUST_MODEL_CODE , MR_GENDER_CODE ,aca.MR_HOUSE_OWNERSHIP_CODE , MR_CUST_RELATIONSHIP_CODE  FROM APP_CUST ac WITH(NOLOCK) JOIN app a WITH(NOLOCK) ON ac.APP_ID = a.APP_ID JOIN APP_CUST_PERSONAL acp WITH(NOLOCK) ON ac.APP_CUST_ID = acp.APP_CUST_ID JOIN APP_CUST_ADDR aca WITH(NOLOCK) ON ac.APP_CUST_ID = aca.APP_CUST_ID where APP_NO = '"+appno+"' AND IS_GUARANTOR = 1 AND Cust_name = '"+name+"' AND aca.MR_CUST_ADDR_TYPE_CODE = 'LEGAL'"), {  row ->
			ResultSetMetaData rsmd = row.getMetaData()
			colmcount = rsmd.getColumnCount()


			for(i = 0 ; i < colmcount ; i++){
				guarantordata = (row[i])
				listcustomerdata.add(guarantordata)
			}

		})
		return listcustomerdata
	}

	@Keyword
	public GuarantorDataStoreDBCompany (Sql instance, String appno, String name){
		String guarantordata
		ArrayList<String> listcustomerdata = new ArrayList<>()
		instance.eachRow(("SELECT [RELATIONSHIP] , CUST_NAME  ,TAX_ID_NO  , MR_COMPANY_TYPE_CODE , [CUST_MODEL] , ADDR , AREA_CODE_4 ,  AREA_CODE_3  ,ZIPCODE , AREA_CODE_2 , AREA_CODE_1 , CITY , [BUILDING]  FROM (SELECT CUST_NAME ,TAX_ID_NO  , ADDR , AREA_CODE_4,  AREA_CODE_3 ,ZIPCODE , AREA_CODE_2 , AREA_CODE_1 , CITY , MR_COMPANY_TYPE_CODE, mastername.Code, REF_MASTER_NAME FROM (SELECT CUST_NAME ,TAX_ID_NO  , ADDR , AREA_CODE_4,  AREA_CODE_3 ,ZIPCODE , AREA_CODE_2 , AREA_CODE_1 , CITY , MR_COMPANY_TYPE_CODE, [Code],value FROM (SELECT ac.CUST_NAME,TAX_ID_NO  , ADDR , AREA_CODE_4,  AREA_CODE_3 , ZIPCODE , AREA_CODE_2 , AREA_CODE_1 , CITY , MR_COMPANY_TYPE_CODE , MR_HOUSE_OWNERSHIP_CODE as [BUILDING], MR_CUST_RELATIONSHIP_CODE AS [RELATIONSHIP], MR_CUST_MODEL_CODE AS [CUST_MODEL] FROM APP_CUST ac WITH(NOLOCK) JOIN app a WITH(NOLOCK) ON ac.APP_ID = a.APP_ID JOIN APP_CUST_COMPANY acc WITH(NOLOCK) ON ac.APP_CUST_ID = acc.APP_CUST_ID JOIN APP_CUST_ADDR aca WITH(NOLOCK) ON ac.APP_CUST_ID = aca.APP_CUST_ID where a.APP_NO = '"+ appno +"' AND ac.CUST_NAME = '"+ name +"' AND ac.IS_GUARANTOR = 1 AND aca.MR_CUST_ADDR_TYPE_CODE = 'LEGAL' AND MR_CUST_TYPE_CODE = 'COMPANY') AS orig unpivot (value for [Code] in ([BUILDING],[RELATIONSHIP],[CUST_MODEL]) ) as unpiv ) as mastername JOIN REF_MASTER_LOS rf WITH(NOLOCK) ON rf.REF_MASTER_Code = mastername.value where rf.ref_master_type_code in ('BUILDING_OWNERSHIP','GUAR_COMPANY_RELATIONSHIP','CUST_MODEL')) AS ref  PIVOT (MAX(ref.REF_MASTER_NAME) for [Code] in ([BUILDING],[RELATIONSHIP],[CUST_MODEL])) as piv"), {  row ->
			ResultSetMetaData rsmd = row.getMetaData()
			colmcount = rsmd.getColumnCount()


			for(i = 0 ; i < colmcount ; i++){
				guarantordata = (row[i])
				listcustomerdata.add(guarantordata)
			}
		})
		return listcustomerdata
	}

	@Keyword
	public GuarantorDataStoreDBCompanyLookUp (Sql instance, String appno, String name){
		String guarantordata
		ArrayList<String> listcustomerdata = new ArrayList<>()
		instance.eachRow(("SELECT ac.CUST_NAME ,TAX_ID_NO , ADDR , AREA_CODE_4 ,  AREA_CODE_3 , ZIPCODE , AREA_CODE_2 , AREA_CODE_1 , CITY , MR_COMPANY_TYPE_CODE , MR_CUST_MODEL_CODE , MR_HOUSE_OWNERSHIP_CODE , MR_CUST_RELATIONSHIP_CODE  FROM APP_CUST ac WITH(NOLOCK) JOIN app a WITH(NOLOCK) ON ac.APP_ID = a.APP_ID JOIN APP_CUST_COMPANY acc WITH(NOLOCK) ON ac.APP_CUST_ID = acc.APP_CUST_ID JOIN APP_CUST_ADDR aca WITH(NOLOCK) ON ac.APP_CUST_ID = aca.APP_CUST_ID where a.APP_NO = '"+ appno +"' AND ac.CUST_NAME = '"+ name +"' AND ac.IS_GUARANTOR = 1 AND aca.MR_CUST_ADDR_TYPE_CODE = 'LEGAL' AND MR_CUST_TYPE_CODE = 'COMPANY'"), {  row ->
			ResultSetMetaData rsmd = row.getMetaData()
			colmcount = rsmd.getColumnCount()


			for(i = 0 ; i < colmcount ; i++){
				guarantordata = (row[i])
				listcustomerdata.add(guarantordata)
			}
		})
		return listcustomerdata
	}


	@Keyword
	public CustomerDataStoreDBCompany (Sql instance, String appno, String name){
		String customerdata
		ArrayList<String> listcustomerdata = new ArrayList<>()
		instance.eachRow(("SELECT ac.CUST_NAME  ,TAX_ID_NO  ,MR_COMPANY_TYPE_CODE  ,MR_CUST_MODEL_CODE , ADDR , AREA_CODE_4 , AREA_CODE_3 , ZIPCODE , AREA_CODE_2 , AREA_CODE_1 , CITY , rml.REF_MASTER_NAME  FROM APP_CUST ac WITH(NOLOCK) JOIN app a WITH(NOLOCK) ON ac.APP_ID = a.APP_ID JOIN APP_CUST_COMPANY acc WITH(NOLOCK) ON ac.APP_CUST_ID = acc.APP_CUST_ID JOIN APP_CUST_ADDR aca WITH(NOLOCK) ON ac.APP_CUST_ID = aca.APP_CUST_ID JOIN REF_MASTER_LOS rml WITH(NOLOCK) ON rml.REF_MASTER_CODE = aca.MR_HOUSE_OWNERSHIP_CODE,(SELECT rml.REF_MASTER_NAME, a.APP_NO, ac.CUST_NAME FROM APP_CUST_COMPANY acc WITH(NOLOCK) JOIN APP_CUST ac WITH(NOLOCK) ON acc.APP_CUST_ID = ac.APP_CUST_ID JOIN APP a WITH(NOLOCK) ON a.APP_ID = ac.APP_ID JOIN REF_MASTER_LOS rml WITH(NOLOCK) ON rml.REF_MASTER_CODE = ac.MR_CUST_MODEL_CODE WHERE REF_MASTER_TYPE_CODE = 'CUST_MODEL') as SUBQ where a.APP_NO = '"+ appno +"' AND ac.CUST_NAME = '"+ name +"' AND ac.IS_CUSTOMER = 1 AND aca.MR_CUST_ADDR_TYPE_CODE = 'LEGAL' AND MR_CUST_TYPE_CODE = 'COMPANY' AND rml.REF_MASTER_TYPE_CODE = 'BUILDING_OWNERSHIP' AND SUBQ.APP_NO = a.APP_NO AND SUBQ.CUST_NAME = ac.CUST_NAME AND rml.IS_ACTIVE = '1'"), {  row ->

			ResultSetMetaData rsmd = row.getMetaData()
			colmcount = rsmd.getColumnCount()


			for(i = 0 ; i < colmcount ; i++){
				customerdata = (row[i])
				listcustomerdata.add(customerdata)
			}
		})
		return listcustomerdata
	}

	@Keyword
	public CustomerDataStoreDBCompanyLookUp (Sql instance, String appno, String name){
		String customerdata
		ArrayList<String> listcustomerdata = new ArrayList<>()
		instance.eachRow(("SELECT ac.CUST_NAME  , ac.TAX_ID_NO  , aca.ADDR , aca.AREA_CODE_4 , aca.AREA_CODE_3 , aca.ZIPCODE , aca.AREA_CODE_2 , aca.AREA_CODE_1 , aca.CITY , acc.MR_COMPANY_TYPE_CODE  , SUBQ.REF_MASTER_NAME , rml.REF_MASTER_NAME  FROM APP_CUST ac WITH(NOLOCK) JOIN app a WITH(NOLOCK) ON ac.APP_ID = a.APP_ID JOIN APP_CUST_COMPANY acc WITH(NOLOCK) ON ac.APP_CUST_ID = acc.APP_CUST_ID JOIN APP_CUST_ADDR aca WITH(NOLOCK) ON ac.APP_CUST_ID = aca.APP_CUST_ID JOIN REF_MASTER_LOS rml WITH(NOLOCK) ON rml.REF_MASTER_CODE = aca.MR_HOUSE_OWNERSHIP_CODE, (SELECT rml.REF_MASTER_NAME, a.APP_NO, ac.CUST_NAME FROM APP_CUST_COMPANY acc WITH(NOLOCK) JOIN APP_CUST ac WITH(NOLOCK) ON acc.APP_CUST_ID = ac.APP_CUST_ID JOIN APP a WITH(NOLOCK) ON a.APP_ID = ac.APP_ID JOIN REF_MASTER_LOS rml WITH(NOLOCK) ON rml.REF_MASTER_CODE = ac.MR_CUST_MODEL_CODE WHERE REF_MASTER_TYPE_CODE = 'CUST_MODEL') as SUBQ where a.APP_NO = '"+ appno +"' AND ac.CUST_NAME = '"+ name +"' AND ac.IS_CUSTOMER = 1 AND aca.MR_CUST_ADDR_TYPE_CODE = 'LEGAL' AND MR_CUST_TYPE_CODE = 'COMPANY' AND rml.REF_MASTER_TYPE_CODE = 'BUILDING_OWNERSHIP' AND SUBQ.APP_NO = a.APP_NO AND SUBQ.CUST_NAME = ac.CUST_NAME AND rml.IS_ACTIVE = '1'"), {  row ->

			ResultSetMetaData rsmd = row.getMetaData()
			colmcount = rsmd.getColumnCount()


			for(i = 0 ; i < colmcount ; i++){
				customerdata = (row[i])
				listcustomerdata.add(customerdata)
			}
		})
		return listcustomerdata
	}

	@Keyword
	public MSDataStoreDBCompany (Sql instance, String appno, String name){
		String MSdata
		ArrayList<String> listcustomerdata = new ArrayList<>()
		instance.eachRow(("SELECT CUST_NAME  ,TAX_ID_NO  , [POSITION_SLIK] , MR_COMPANY_TYPE_CODE , [CUST_MODEL] , [SHARE_PRCNT] , [ISACTIVE] , IS_OWNER , ADDR , AREA_CODE_4 ,  AREA_CODE_3  ,ZIPCODE , AREA_CODE_2 , AREA_CODE_1 , CITY , [BUILDING]  FROM (SELECT CUST_NAME ,TAX_ID_NO  , ADDR , AREA_CODE_4,  AREA_CODE_3 ,ZIPCODE , AREA_CODE_2 , AREA_CODE_1 , CITY , MR_COMPANY_TYPE_CODE, [SHARE_PRCNT], [ISACTIVE], IS_OWNER, mastername.Code, REF_MASTER_NAME FROM (SELECT CUST_NAME ,TAX_ID_NO  , ADDR , AREA_CODE_4,  AREA_CODE_3 ,ZIPCODE , AREA_CODE_2 , AREA_CODE_1 , CITY , MR_COMPANY_TYPE_CODE, [SHARE_PRCNT], [ISACTIVE], IS_OWNER, [Code],value FROM (SELECT ac.CUST_NAME,TAX_ID_NO  , ADDR , AREA_CODE_4, AREA_CODE_3 , ZIPCODE , AREA_CODE_2 , AREA_CODE_1 , CITY , MR_COMPANY_TYPE_CODE , MR_HOUSE_OWNERSHIP_CODE as [BUILDING], CAST(MR_POSITION_SLIK_CODE as nvarchar(50)) AS [POSITION_SLIK], MR_CUST_MODEL_CODE AS [CUST_MODEL], FORMAT(SHARE_PRCNT, 'N0') AS [SHARE_PRCNT], accMS.IS_ACTIVE AS [ISACTIVE], IS_OWNER FROM APP_CUST ac WITH(NOLOCK) JOIN app a WITH(NOLOCK) ON ac.APP_ID = a.APP_ID JOIN APP_CUST_COMPANY acc WITH(NOLOCK) ON ac.APP_CUST_ID = acc.APP_CUST_ID JOIN APP_CUST_ADDR aca WITH(NOLOCK) ON ac.APP_CUST_ID = aca.APP_CUST_ID JOIN APP_CUST_COMPANY_MGMNT_SHRHOLDER accMS WITH(NOLOCK) ON ac.APP_CUST_ID = accMS.APP_CUST_ID WHERE a.APP_NO = '"+ appno +"' AND CUST_NAME = '"+ name +"' AND IS_SHAREHOLDER = 1 AND MR_CUST_ADDR_TYPE_CODE = 'LEGAL' AND MR_CUST_TYPE_CODE = 'COMPANY') AS orig unpivot (value for [Code] in ([BUILDING],[POSITION_SLIK],[CUST_MODEL])) as unpiv ) as mastername JOIN REF_MASTER_LOS rf WITH(NOLOCK) ON rf.REF_MASTER_Code = mastername.value where rf.ref_master_type_code in ('BUILDING_OWNERSHIP','CUST_MODEL','POSITION_SLIK')) AS ref  PIVOT (MAX(ref.REF_MASTER_NAME) for [Code] in ([BUILDING],[POSITION_SLIK],[CUST_MODEL])) as piv"), {  row ->

			ResultSetMetaData rsmd = row.getMetaData()
			colmcount = rsmd.getColumnCount()


			for(i = 0 ; i < colmcount ; i++){
				MSdata = (row[i])
				listcustomerdata.add(MSdata)
			}
		})
		return listcustomerdata
	}

	@Keyword
	public MSDataStoreDBCompanyLookUp (Sql instance, String appno, String name){
		String MSdata
		ArrayList<String> listcustomerdata = new ArrayList<>()
		instance.eachRow(("SELECT CUST_NAME  ,TAX_ID_NO  , ADDR , AREA_CODE_4 ,  AREA_CODE_3  ,ZIPCODE , AREA_CODE_2 , AREA_CODE_1 , CITY , [POSITION_SLIK] , MR_COMPANY_TYPE_CODE , [CUST_MODEL] , [SHARE_PRCNT] , [ISACTIVE] , IS_OWNER , [BUILDING]  FROM (SELECT CUST_NAME ,TAX_ID_NO  , ADDR , AREA_CODE_4,  AREA_CODE_3 ,ZIPCODE , AREA_CODE_2 , AREA_CODE_1 , CITY , MR_COMPANY_TYPE_CODE, [SHARE_PRCNT], [ISACTIVE], IS_OWNER, mastername.Code, REF_MASTER_NAME FROM (SELECT CUST_NAME ,TAX_ID_NO  , ADDR , AREA_CODE_4,  AREA_CODE_3 ,ZIPCODE , AREA_CODE_2 , AREA_CODE_1 , CITY , MR_COMPANY_TYPE_CODE, [SHARE_PRCNT], [ISACTIVE], IS_OWNER, [Code],value FROM (SELECT ac.CUST_NAME,TAX_ID_NO  , ADDR , AREA_CODE_4, AREA_CODE_3 , ZIPCODE , AREA_CODE_2 , AREA_CODE_1 , CITY , MR_COMPANY_TYPE_CODE , MR_HOUSE_OWNERSHIP_CODE as [BUILDING], CAST(MR_POSITION_SLIK_CODE as nvarchar(50)) AS [POSITION_SLIK], MR_CUST_MODEL_CODE AS [CUST_MODEL], FORMAT(SHARE_PRCNT, 'N0') AS [SHARE_PRCNT], accMS.IS_ACTIVE AS [ISACTIVE], IS_OWNER FROM APP_CUST ac WITH(NOLOCK) JOIN app a WITH(NOLOCK) ON ac.APP_ID = a.APP_ID JOIN APP_CUST_COMPANY acc WITH(NOLOCK) ON ac.APP_CUST_ID = acc.APP_CUST_ID JOIN APP_CUST_ADDR aca WITH(NOLOCK) ON ac.APP_CUST_ID = aca.APP_CUST_ID JOIN APP_CUST_COMPANY_MGMNT_SHRHOLDER accMS WITH(NOLOCK) ON ac.APP_CUST_ID = accMS.APP_CUST_ID WHERE a.APP_NO = '"+ appno +"' AND CUST_NAME = '"+ name +"' AND IS_SHAREHOLDER = 1 AND MR_CUST_ADDR_TYPE_CODE = 'LEGAL' AND MR_CUST_TYPE_CODE = 'COMPANY') AS orig unpivot (value for [Code] in ([BUILDING],[POSITION_SLIK],[CUST_MODEL])) as unpiv ) as mastername JOIN REF_MASTER_LOS rf WITH(NOLOCK) ON rf.REF_MASTER_Code = mastername.value where rf.ref_master_type_code in ('BUILDING_OWNERSHIP','CUST_MODEL','POSITION_SLIK')) AS ref  PIVOT (MAX(ref.REF_MASTER_NAME) for [Code] in ([BUILDING],[POSITION_SLIK],[CUST_MODEL])) as piv"), {  row ->

			ResultSetMetaData rsmd = row.getMetaData()
			colmcount = rsmd.getColumnCount()


			for(i = 0 ; i < colmcount ; i++){
				MSdata = (row[i])
				listcustomerdata.add(MSdata)
			}
		})
		return listcustomerdata
	}

	@Keyword
	public MSDataStoreDBPersonal (Sql instance, String appno, String name){
		String MSdata
		ArrayList<String> listcustomerdata = new ArrayList<>()
		instance.eachRow(("SELECT [CustName] , [BirthPlace] , [ID_TYPE] ,[IDExpiredDate] , [MaritalStatus] , [MobilePHN1] , [CUST_MODEL] , [ProffesionCode] , [POSITION_SLIK] , [EstablishmentDate] , [Gender] , [BirthDate] , [Idno] , [TaxID] , [MotherMaidenName] , [Email1] , [JOB_POSITION] , [Sharepercent] , [isOwner] , [isActive] , [isSigner] , [DEPARTMENT_AML] , [AuthAML] , [Addr] , [RT] , [RW] , [Zipcode] , [Kecamatan] , [Kelurahan] , [Kota] , [BUILDING_OWNERSHIP]  FROM (SELECT [CustName], [BirthPlace], [IDExpiredDate], [MaritalStatus], [MobilePHN1], [ProffesionCode], [Gender], [EstablishmentDate], [BirthDate], [Idno], [TaxID], [MotherMaidenName], [Email1], [Sharepercent], [isOwner], [isActive], [isSigner], [AuthAML], [Addr], [RT], [RW], [Zipcode], [Kecamatan], [Kelurahan], [Kota], mastername.Code, REF_MASTER_NAME FROM (select [CustName], [BirthPlace], [IDExpiredDate], [MaritalStatus], [MobilePHN1], [ProffesionCode], [Gender], [EstablishmentDate], [BirthDate], [Idno], [TaxID], [MotherMaidenName], [Email1], [Sharepercent], [isOwner], [isActive], [isSigner], [AuthAML], [Addr], [RT], [RW], [Zipcode], [Kecamatan], [Kelurahan], [Kota], [Code], value FROM (SELECT Cust_name AS [CustName], BIRTH_PLACE AS [BirthPlace], ac.MR_ID_TYPE_CODE AS 'ID_TYPE', FORMAT(ID_EXPIRED_DT, 'MM/dd/yyyy') AS [IDExpiredDate], MR_MARITAL_STAT_CODE AS [MaritalStatus], MOBILE_PHN_NO_1 AS [MobilePHN1], ac.MR_CUST_MODEL_CODE AS 'CUST_MODEL', rpl.PROFESSION_NAME AS [ProffesionCode], CAST(MR_POSITION_SLIK_CODE as nvarchar(50)) AS 'POSITION_SLIK', FORMAT(accMS.ESTABLISHMENT_DT, 'MM/dd/yyyy') AS [EstablishmentDate], MR_GENDER_CODE AS [Gender], FORMAT(BIRTH_DT, 'MM/dd/yyyy') AS [BirthDate], ID_NO AS [Idno], TAX_ID_NO AS [TaxID], MOTHER_MAIDEN_NAME AS [MotherMaidenName], EMAIL_1 AS [Email1],  accMS.MR_JOB_POSITION_CODE AS 'JOB_POSITION', FORMAT(SHARE_PRCNT, 'N0') AS [Sharepercent], IS_OWNER AS [isOwner], accMS.IS_ACTIVE AS [isActive], IS_SIGNER AS [isSigner], CAST(attr.ATTR_VALUE as nvarchar(50)) AS 'DEPARTMENT_AML', SUBQ.ATTR_VALUE AS [AuthAML], ADDR AS [Addr], AREA_CODE_4 AS [RT], AREA_CODE_3 AS [RW], ZIPCODE AS [Zipcode], AREA_CODE_2 AS [Kecamatan], AREA_CODE_1 AS [Kelurahan], CITY AS [Kota], aca.MR_HOUSE_OWNERSHIP_CODE AS 'BUILDING_OWNERSHIP' FROM APP_CUST ac WITH(NOLOCK) JOIN app a WITH(NOLOCK) ON ac.APP_ID = a.APP_ID JOIN APP_CUST_PERSONAL acp WITH(NOLOCK) ON ac.APP_CUST_ID = acp.APP_CUST_ID JOIN APP_CUST_ADDR aca WITH(NOLOCK) ON ac.APP_CUST_ID = aca.APP_CUST_ID JOIN APP_CUST_COMPANY_MGMNT_SHRHOLDER accMS WITH(NOLOCK) ON acp.APP_CUST_ID = accMS.APP_CUST_ID JOIN APP_CUST_PERSONAL_JOB_DATA acpjd WITH(NOLOCK) ON acpjd.APP_CUST_PERSONAL_ID = acp.APP_CUST_PERSONAL_ID JOIN APP_CUST_ATTR_CONTENT attr WITH(NOLOCK) ON attr.APP_CUST_ID = ac.APP_CUST_ID JOIN REF_PROFESSION_LOS rpl WITH(NOLOCK) ON rpl.PROFESSION_CODE = acpjd.MR_PROFESSION_CODE, (SELECT ATTR_VALUE, ac.APP_ID FROM APP_CUST_ATTR_CONTENT acat JOIN APP_CUST ac WITH(NOLOCK) ON acat.APP_CUST_ID = ac.APP_CUST_ID JOIN app a WITH(NOLOCK) ON ac.APP_ID = a.APP_ID WHERE APP_NO = '"+ appno +"' AND IS_SHAREHOLDER = 1 AND acat.REF_ATTR_CODE = 'AUTH_AML') as SUBQ where ac.APP_ID = SUBQ.APP_ID AND MR_CUST_ADDR_TYPE_CODE = 'LEGAL' AND accMS.MR_SHRHOLDER_TYPE_CODE = 'PERSONAL' AND CUST_NAME = '"+ name +"' AND attr.REF_ATTR_CODE = 'AML_CUST_DEPARTMENT') as Orig unpivot (value for [Code] in ([ID_TYPE],[CUST_MODEL],[BUILDING_OWNERSHIP],[JOB_POSITION],[DEPARTMENT_AML],[POSITION_SLIK]))as unpiv) as mastername JOIN REF_MASTER_LOS rf WITH(NOLOCK) ON rf.REF_MASTER_Code = mastername.value WHERE rf.IS_ACTIVE = '1') AS ref PIVOT (MAX(ref.REF_MASTER_NAME) for [Code] in ([ID_TYPE],[CUST_MODEL],[BUILDING_OWNERSHIP],[JOB_POSITION],[DEPARTMENT_AML],[POSITION_SLIK])) as piv"), {  row ->

			ResultSetMetaData rsmd = row.getMetaData()
			colmcount = rsmd.getColumnCount()


			for(i = 0 ; i < colmcount ; i++){
				MSdata = (row[i])
				listcustomerdata.add(MSdata)
			}
		})
		return listcustomerdata
	}


	@Keyword
	public MSDataStoreDBPersonalLookUp (Sql instance, String appno, String name){
		String MSdata
		ArrayList<String> listcustomerdata = new ArrayList<>()
		instance.eachRow(("SELECT [CustName] , [BirthPlace] , [IDExpiredDate] , [MobilePHN1] , [BirthDate] , [Idno] , [TaxID] , [MotherMaidenName] , [Email1] , [Addr] , [RT] , [RW] , [Zipcode] , [Kecamatan] , [Kelurahan] , [Kota] , [ID_TYPE] , [MaritalStatus] , [CUST_MODEL] , [ProffesionCode] , [POSITION_SLIK] , [EstablishmentDate] , [Gender] , [JOB_POSITION] , [Sharepercent] , [isOwner] , [isActive] , [isSigner] , [DEPARTMENT_AML] , [AuthAML] , [BUILDING_OWNERSHIP]  FROM (SELECT [CustName], [BirthPlace], [IDExpiredDate], [MaritalStatus], [MobilePHN1], [ProffesionCode], [Gender], [EstablishmentDate], [BirthDate], [Idno], [TaxID], [MotherMaidenName], [Email1], [Sharepercent], [isOwner], [isActive], [isSigner], [AuthAML], [Addr], [RT], [RW], [Zipcode], [Kecamatan], [Kelurahan], [Kota], mastername.Code, REF_MASTER_NAME FROM (select [CustName], [BirthPlace], [IDExpiredDate], [MaritalStatus], [MobilePHN1], [ProffesionCode], [Gender], [EstablishmentDate], [BirthDate], [Idno], [TaxID], [MotherMaidenName], [Email1], [Sharepercent], [isOwner], [isActive], [isSigner], [AuthAML], [Addr], [RT], [RW], [Zipcode], [Kecamatan], [Kelurahan], [Kota], [Code], value FROM (SELECT Cust_name AS [CustName], BIRTH_PLACE AS [BirthPlace], ac.MR_ID_TYPE_CODE AS 'ID_TYPE', FORMAT(ID_EXPIRED_DT, 'yyyy-MM-dd') AS [IDExpiredDate], MR_MARITAL_STAT_CODE AS [MaritalStatus], MOBILE_PHN_NO_1 AS [MobilePHN1], ac.MR_CUST_MODEL_CODE AS 'CUST_MODEL', rpl.PROFESSION_NAME AS [ProffesionCode], CAST(MR_POSITION_SLIK_CODE as nvarchar(50)) AS 'POSITION_SLIK', FORMAT(accMS.ESTABLISHMENT_DT, 'yyyy-MM-dd') AS [EstablishmentDate], MR_GENDER_CODE AS [Gender], FORMAT(BIRTH_DT, 'yyyy-MM-dd') AS [BirthDate], ID_NO AS [Idno], TAX_ID_NO AS [TaxID], MOTHER_MAIDEN_NAME AS [MotherMaidenName], EMAIL_1 AS [Email1],  accMS.MR_JOB_POSITION_CODE AS 'JOB_POSITION', FORMAT(SHARE_PRCNT, 'N0') AS [Sharepercent], IS_OWNER AS [isOwner], accMS.IS_ACTIVE AS [isActive], IS_SIGNER AS [isSigner], CAST(attr.ATTR_VALUE as nvarchar(50)) AS 'DEPARTMENT_AML', SUBQ.ATTR_VALUE AS [AuthAML], ADDR AS [Addr], AREA_CODE_4 AS [RT], AREA_CODE_3 AS [RW], ZIPCODE AS [Zipcode], AREA_CODE_2 AS [Kecamatan], AREA_CODE_1 AS [Kelurahan], CITY AS [Kota], aca.MR_HOUSE_OWNERSHIP_CODE AS 'BUILDING_OWNERSHIP' FROM APP_CUST ac WITH(NOLOCK) JOIN app a WITH(NOLOCK) ON ac.APP_ID = a.APP_ID JOIN APP_CUST_PERSONAL acp WITH(NOLOCK) ON ac.APP_CUST_ID = acp.APP_CUST_ID JOIN APP_CUST_ADDR aca WITH(NOLOCK) ON ac.APP_CUST_ID = aca.APP_CUST_ID JOIN APP_CUST_COMPANY_MGMNT_SHRHOLDER accMS WITH(NOLOCK) ON acp.APP_CUST_ID = accMS.APP_CUST_ID JOIN APP_CUST_PERSONAL_JOB_DATA acpjd WITH(NOLOCK) ON acpjd.APP_CUST_PERSONAL_ID = acp.APP_CUST_PERSONAL_ID JOIN APP_CUST_ATTR_CONTENT attr WITH(NOLOCK) ON attr.APP_CUST_ID = ac.APP_CUST_ID JOIN REF_PROFESSION_LOS rpl WITH(NOLOCK) ON rpl.PROFESSION_CODE = acpjd.MR_PROFESSION_CODE, (SELECT ATTR_VALUE, ac.APP_ID FROM APP_CUST_ATTR_CONTENT acat JOIN APP_CUST ac WITH(NOLOCK) ON acat.APP_CUST_ID = ac.APP_CUST_ID JOIN app a WITH(NOLOCK) ON ac.APP_ID = a.APP_ID WHERE APP_NO = '"+ appno +"' AND IS_SHAREHOLDER = 1 AND acat.REF_ATTR_CODE = 'AUTH_AML') as SUBQ where ac.APP_ID = SUBQ.APP_ID AND MR_CUST_ADDR_TYPE_CODE = 'LEGAL' AND accMS.MR_SHRHOLDER_TYPE_CODE = 'PERSONAL' AND CUST_NAME = '"+ name +"' AND attr.REF_ATTR_CODE = 'AML_CUST_DEPARTMENT') as Orig unpivot (value for [Code] in ([ID_TYPE],[CUST_MODEL],[BUILDING_OWNERSHIP],[JOB_POSITION],[DEPARTMENT_AML],[POSITION_SLIK]))as unpiv) as mastername JOIN REF_MASTER_LOS rf WITH(NOLOCK) ON rf.REF_MASTER_Code = mastername.value WHERE rf.IS_ACTIVE = '1') AS ref PIVOT (MAX(ref.REF_MASTER_NAME) for [Code] in ([ID_TYPE],[CUST_MODEL],[BUILDING_OWNERSHIP],[JOB_POSITION],[DEPARTMENT_AML],[POSITION_SLIK])) as piv"), {  row ->

			ResultSetMetaData rsmd = row.getMetaData()
			colmcount = rsmd.getColumnCount()


			for(i = 0 ; i < colmcount ; i++){
				MSdata = (row[i])
				listcustomerdata.add(MSdata)
			}
		})
		return listcustomerdata
	}


	@Keyword
	public NAP2TabReferantorStoreDB (Sql instance, String appno, String name){
		String referantordata
		ArrayList <String> listreferantor = new ArrayList<String>()
		instance.eachRow(("SELECT CASE WHEN MR_REFERANTOR_TYPE = 'AGENCY_PERSONAL' OR MR_REFERANTOR_TYPE = 'AGENCY_COMPANY' THEN 'AGENCY' WHEN MR_REFERANTOR_TYPE = 'CUSTOMER_PERSONAL' OR MR_REFERANTOR_TYPE = 'CUSTOMER_COMPANY' THEN 'CUSTOMER' ELSE 'MULTIFINANCE EMPLOYEE' END, ar.REFERANTOR_NAME, d.BANK_NAME, ar.BANK_ACC_NAME, ar.BANK_ACC_NO, ar.MR_TAX_CALC_METHOD FROM APP_CUST ac WITH(NOLOCK) JOIN APP a WITH(NOLOCK) ON ac.APP_ID = a.APP_ID JOIN APP_REFERANTOR ar WITH(NOLOCK) ON a.APP_ID = ar.APP_ID JOIN REF_BANK_LOS d WITH(NOLOCK) ON ar.REF_BANK_CODE = d.BANK_CODE WHERE APP_NO = '"+ appno +"' AND CUST_NAME = '"+ name +"'"), {  row ->

			ResultSetMetaData rsmd = row.getMetaData()
			colmcount = rsmd.getColumnCount()


			for(i = 0 ; i < colmcount ; i++){
				referantordata = (row[i])
				listreferantor.add(referantordata)
			}

		})
		return listreferantor
	}


	@Keyword
	public NAP2TabApplicationStoreDB (Sql instance, String appno, String name){
		String applicationdata
		ArrayList<String> listapplicationdata = new ArrayList<>()
		instance.eachRow(("SELECT [APPSRCNAME] , [FIRST_INST_TYPE] , [PAYMENTFREQ] , [TENOR] , [DPSRCPAYMENT] , [INTRSTTYPE] , [FLOATINGPERIOD] , [INST_SCHM] , [WOP] , [CUST_NOTIF_OPT] , [CSP_USL_AML] , [ADDR] , [RT] , [RW] , [ZIPCODE] , [KECAMATAN] , [KELURAHAN] , [KOTA] , [PHN1AREA] , [PHN1] , [PHN1EXT] , [PHN2AREA] , [PHN2] , [PHN2EXT] , [PHN3AREA] , [PHN3] , [PHN3EXT] , [FAXAREA] , [FAX] , [CHARACTERISTIC_OF_CREDIT] , [PREVAGGRMT] , [WAY_OF_RESTRUCTURE] , [SLIKECO]  FROM (SELECT [APPSRCNAME], [PAYMENTFREQ], [TENOR], [FLOATINGPERIOD], [ADDR], [RT], [RW], [ZIPCODE], [KECAMATAN], [KELURAHAN], [KOTA], [PHN1AREA], [PHN1], [PHN1EXT], [PHN2AREA], [PHN2], [PHN2EXT], [PHN3AREA], [PHN3], [PHN3EXT], [FAXAREA], [FAX], [PREVAGGRMT], [SLIKECO],  mastername.Code, REF_MASTER_NAME FROM ( select [APPSRCNAME], [PAYMENTFREQ], [TENOR], [FLOATINGPERIOD], [ADDR], [RT], [RW], [ZIPCODE], [KECAMATAN], [KELURAHAN], [KOTA], [PHN1AREA], [PHN1], [PHN1EXT], [PHN2AREA], [PHN2], [PHN2EXT], [PHN3AREA], [PHN3], [PHN3EXT], [FAXAREA], [FAX], [PREVAGGRMT], [SLIKECO], [Code], value FROM ( SELECT ras.APP_SRC_NAME AS [APPSRCNAME], MR_FIRST_INST_TYPE_CODE AS 'FIRST_INST_TYPE', PAY_FREQ_CODE AS [PAYMENTFREQ], TENOR AS [TENOR], MR_DP_SRC_PAYMENT_CODE AS 'DPSRCPAYMENT', CAST(acpfd.INTEREST_TYPE as nvarchar(50)) AS 'INTRSTTYPE', FLOATING_PERIOD_CODE AS [FLOATINGPERIOD], MR_INST_SCHEME_CODE AS 'INST_SCHM', MR_WOP_CODE AS 'WOP', MR_CUST_NOTIFY_OPT_CODE AS 'CUST_NOTIF_OPT', MR_INST_SRC_PAYMENT_CODE AS 'CSP_USL_AML', aca.ADDR AS [ADDR], aca.AREA_CODE_3 AS [RT], aca.AREA_CODE_4 AS [RW], aca.ZIPCODE AS [ZIPCODE], aca.AREA_CODE_2 AS [KECAMATAN], aca.AREA_CODE_1 AS [KELURAHAN], aca.CITY AS [KOTA], aca.PHN_AREA_1 AS [PHN1AREA], aca.PHN_1 AS [PHN1], aca.PHN_EXT_1 AS [PHN1EXT], aca.PHN_AREA_2 AS [PHN2AREA], aca.PHN_2 AS [PHN2], aca.PHN_EXT_2 AS [PHN2EXT] , aca.PHN_AREA_3 AS [PHN3AREA], aca.PHN_3 AS [PHN3], aca.PHN_EXT_3 AS [PHN3EXT], aca.FAX_AREA AS [FAXAREA], aca.FAX AS [FAX], MR_CHARACTERISTIC_OF_CREDIT_CODE AS 'CHARACTERISTIC_OF_CREDIT', agr.PREV_AGRMNT_NO AS [PREVAGGRMT], MR_WAY_OF_RESTRUCTURE_CODE AS 'WAY_OF_RESTRUCTURE', agr.MR_SLIK_SEC_ECO_CODE AS [SLIKECO] FROM APP a WITH(NOLOCK) JOIN APP_CUST ac WITH(NOLOCK) ON a.APP_ID = ac.APP_ID JOIN APP_CUST_ADDR aca WITH(NOLOCK) ON ac.APP_CUST_ID = aca.APP_CUST_ID JOIN APP_GOVERMENT_REGULATION agr WITH(NOLOCK) ON agr.APP_ID = a.APP_ID JOIN APP_FIN_DATA acpfd WITH(NOLOCK) ON acpfd.APP_ID = ac.APP_ID JOIN REF_APP_SRC ras WITH(NOLOCK) ON ras.APP_SRC_CODE = a.MR_APP_SOURCE_CODE WHERE APP_NO = '"+appno+"' AND aca.MR_CUST_ADDR_TYPE_CODE = 'MAILING' AND CUST_NAME = '"+name+"') as Orig unpivot (value for [Code] in ([FIRST_INST_TYPE],[WOP],[DPSRCPAYMENT],[INTRSTTYPE],[INST_SCHM],[CUST_NOTIF_OPT],[CSP_USL_AML],[CHARACTERISTIC_OF_CREDIT],[WAY_OF_RESTRUCTURE]) )as unpiv) as mastername JOIN REF_MASTER_LOS rf WITH(NOLOCK) ON rf.REF_MASTER_Code = mastername.value WHERE rf.IS_ACTIVE = '1' AND rf.REF_MASTER_TYPE_CODE IN ('FIRST_INST_TYPE','WOP','INTRSTTYPE','INST_SCHM','CUST_NOTIF_OPT','CSP_USL_AML','CHARACTERISTIC_OF_CREDIT','WAY_OF_RESTRUCTURE')) AS ref PIVOT(MAX(ref.REF_MASTER_NAME) for [Code] in ([FIRST_INST_TYPE],[WOP],[DPSRCPAYMENT],[INTRSTTYPE],[INST_SCHM],[CUST_NOTIF_OPT],[CSP_USL_AML],[CHARACTERISTIC_OF_CREDIT],[WAY_OF_RESTRUCTURE])) as piv"), {  row ->

			ResultSetMetaData rsmd = row.getMetaData()
			colmcount = rsmd.getColumnCount()


			for(i = 0 ; i < colmcount ; i++){
				applicationdata = (row[i])
				listapplicationdata.add(applicationdata)
			}
		})
		return listapplicationdata
	}

	@Keyword
	public BankAccountTabApplicationDataStoreDB (Sql instance, String appno){
		String BankAccountTabApplication
		ArrayList<String> listbankacctabapplication = new ArrayList<>()
		instance.eachRow(("SELECT rbl.BANK_NAME , aoi.BANK_ACC_NO , aoi.BANK_ACC_NAME  FROM app a WITH(NOLOCK) JOiN APP_OTHER_INFO aoi WITH(NOLOCK) ON a.APP_ID = aoi.APP_ID JOIN REF_BANK_LOS rbl WITH(NOLOCK) ON aoi.BANK_CODE = rbl.BANK_CODE WHERE APP_NO = '"+ appno +"'"), {  row ->

			ResultSetMetaData rsmd = row.getMetaData()
			colmcount = rsmd.getColumnCount()


			for(i = 0 ; i < colmcount ; i++){
				BankAccountTabApplication = (row[i])
				listbankacctabapplication.add(BankAccountTabApplication)
			}
		})
		return listbankacctabapplication
	}

	@Keyword
	public NAP2TabApplicationAttrStoreDB (Sql instance, String appno){
		String applicationdata
		ArrayList <String> listapplicationattr = new ArrayList<>()
		instance.eachRow(("SELECT ATTR_VALUE FROM APP_ATTR_CONTENT aac WITH(NOLOCK) JOIN APP a WITH(NOLOCK) ON a.APP_ID = aac.APP_ID WHERE APP_NO = '"+ appno +"'"), {  row ->

			ResultSetMetaData rsmd = row.getMetaData()
			colmcount = rsmd.getColumnCount()


			for(i = 0 ; i < colmcount ; i++){
				applicationdata = (row[i])
				listapplicationattr.add(applicationdata)
			}

		})
		return listapplicationattr
	}

	@Keyword
	public NAP2TabAssetSupplierInfoStoreDB (Sql instance, String appno){
		String assetdata
		ArrayList <String> listassetsupp = new ArrayList<>()
		instance.eachRow(("SELECT SUPPL_CODE , SUPPL_NAME , SUBQ.SUPPL_EMP_NAME , se.SUPPL_EMP_NAME  FROM APP_ASSET_SUPPL_EMP se WITH(NOLOCK) JOIN APP_ASSET aaw WITH(NOLOCK) ON aaw.APP_ASSET_ID = se.APP_ASSET_ID JOIN APP a WITH(NOLOCK) ON a.APP_ID = aaw.APP_ID, (SELECT SUPPL_EMP_NAME FROM APP_ASSET_SUPPL_EMP se WITH(NOLOCK) JOIN APP_ASSET aaw WITH(NOLOCK) ON aaw.APP_ASSET_ID = se.APP_ASSET_ID JOIN APP a WITH(NOLOCK) ON a.APP_ID = aaw.APP_ID WHERE APP_NO = '"+ appno +"' AND MR_SUPPL_EMP_POSITION_CODE = 'SALES_PERSON') as SUBQ WHERE APP_NO = '"+ appno +"' AND se.MR_SUPPL_EMP_POSITION_CODE = 'ADMIN_HEAD'"), {  row ->

			ResultSetMetaData rsmd = row.getMetaData()
			colmcount = rsmd.getColumnCount()


			for(i = 0 ; i < colmcount ; i++){
				assetdata = (row[i])
				listassetsupp.add(assetdata)
			}
		})
		return listassetsupp
	}

	@Keyword
	public NAP2TabAssetStoreDB (Sql instance, String appno){
		String assetinfo
		ArrayList<String> listassetinfo = new ArrayList<>()
		instance.eachRow(("SELECT [ASSETCODE] , [ASSETCONDITION] , [ASSETPRICE] , [ASSET_USAGE] , [ASSETCOLOR] , [BPKB] , [BPKBISSUEDATE] , [ASSETNOTES] , [MANYEAR] , [DPPERCENT] , [DPAMOUNT] , [SERIALNO1] , [SERIALNO2] , [SERIALNO3] , [SERIALNO4] , [SERIALNO5] , [REGION1] , [USERNAME] , [CUST_PERSONAL_RELATIONSHIP] , [OWNERTYPE] , [OWNERNAME] , [OWNERRELATIONSHIP] , [OWNERPROF] , [ID_TYPE] , [OWNERID] , [OWNERMOBILE] , [OWNERADDR] , [OWNERRT] , [OWNERRW] , [OWNERZIPCODE] , [OWNERKECAMATAN] , [OWNERKELURAHAN] , [OWNERKOTA] , [LOCATIONADDR] , [LOCATIONRT] , [LOCATIONRW] , [LOCATIONZIPCODE] , [LOCATIONKECAMATAN] , [LOCATIONKELURAHAN] , [LOCATIONKOTA]  FROM (SELECT [ASSETCODE], [ASSETCONDITION], [ASSETPRICE], [ASSETCOLOR], [BPKB], [BPKBISSUEDATE], [ASSETNOTES], [MANYEAR], [DPPERCENT], [DPAMOUNT], [SERIALNO1], [SERIALNO2], [SERIALNO3], [SERIALNO4], [SERIALNO5], [REGION1], [USERNAME], [OWNERTYPE], [OWNERNAME], [OWNERRELATIONSHIP], [OWNERPROF], [OWNERID], [OWNERMOBILE], [OWNERADDR], [OWNERRT], [OWNERRW], [OWNERZIPCODE], [OWNERKECAMATAN], [OWNERKELURAHAN], [OWNERKOTA], [LOCATIONADDR], [LOCATIONRT], [LOCATIONRW], [LOCATIONZIPCODE], [LOCATIONKECAMATAN], [LOCATIONKELURAHAN], [LOCATIONKOTA],  mastername.Code, REF_MASTER_NAME FROM ( select [ASSETCODE], [ASSETCONDITION], [ASSETPRICE], [ASSETCOLOR], [BPKB], [BPKBISSUEDATE], [ASSETNOTES], [MANYEAR], [DPPERCENT], [DPAMOUNT], [SERIALNO1], [SERIALNO2], [SERIALNO3], [SERIALNO4], [SERIALNO5], [REGION1], [USERNAME], [OWNERTYPE], [OWNERNAME], [OWNERRELATIONSHIP], [OWNERPROF], [OWNERID], [OWNERMOBILE], [OWNERADDR], [OWNERRT], [OWNERRW], [OWNERZIPCODE], [OWNERKECAMATAN], [OWNERKELURAHAN], [OWNERKOTA], [LOCATIONADDR], [LOCATIONRT], [LOCATIONRW], [LOCATIONZIPCODE], [LOCATIONKECAMATAN], [LOCATIONKELURAHAN], [LOCATIONKOTA], [Code], value FROM ( SELECT  aa.FULL_ASSET_CODE AS [ASSETCODE], MR_ASSET_CONDITION_CODE AS [ASSETCONDITION], CAST(ASSET_PRICE_AMT as INT) AS [ASSETPRICE], aa.MR_ASSET_USAGE_CODE AS 'ASSET_USAGE', aa.COLOR AS [ASSETCOLOR], REPLACE(aa.TAX_CITY_ISSUER, ' ', '') AS [BPKB], FORMAT(aa.TAX_ISSUE_DT, 'MM/dd/yyy') AS [BPKBISSUEDATE], aa.ASSET_NOTES AS [ASSETNOTES], aa.MANUFACTURING_YEAR AS [MANYEAR], CONVERT(INT, DOWN_PAYMENT_PRCNT) AS [DPPERCENT], CONVERT(INT, DOWN_PAYMENT_AMT) AS [DPAMOUNT], aa.SERIAL_NO_1 AS [SERIALNO1], aa.SERIAL_NO_2 AS [SERIALNO2], aa.SERIAL_NO_3 AS [SERIALNO3], aa.SERIAL_NO_4 AS [SERIALNO4], aa.SERIAL_NO_5 AS [SERIALNO5], aattr.ATTR_VALUE AS [REGION1], acr.USER_NAME AS [USERNAME], acr.MR_USER_RELATIONSHIP_CODE AS 'CUST_PERSONAL_RELATIONSHIP', acr.MR_OWNER_TYPE_CODE AS [OWNERTYPE], acr.OWNER_NAME AS [OWNERNAME], rml.REF_MASTER_NAME AS [OWNERRELATIONSHIP], acr.OWNER_PROFESSION_CODE AS [OWNERPROF], acr.MR_ID_TYPE_CODE AS 'ID_TYPE', acr.OWNER_ID_NO AS [OWNERID], acr.OWNER_MOBILE_PHN_NO AS [OWNERMOBILE], acr.OWNER_ADDR AS [OWNERADDR], acr.LOCATION_AREA_CODE_4 AS [OWNERRT], acr.LOCATION_AREA_CODE_3 AS [OWNERRW], acr.OWNER_ZIPCODE AS [OWNERZIPCODE], acr.OWNER_AREA_CODE_2 AS [OWNERKECAMATAN], acr.OWNER_AREA_CODE_1 AS [OWNERKELURAHAN], acr.OWNER_CITY AS [OWNERKOTA], acr.LOCATION_ADDR AS [LOCATIONADDR], acr.LOCATION_AREA_CODE_4 AS [LOCATIONRT], acr.LOCATION_AREA_CODE_3 AS [LOCATIONRW], acr.LOCATION_ZIPCODE AS [LOCATIONZIPCODE], acr.LOCATION_AREA_CODE_2 AS [LOCATIONKECAMATAN], acr.LOCATION_AREA_CODE_1 AS [LOCATIONKELURAHAN], acr.LOCATION_CITY AS [LOCATIONKOTA] FROM APP_ASSET aa WITH(NOLOCK) JOIN APP a WITH(NOLOCK) ON aa.APP_ID = a.APP_ID JOIN APP_ASSET_ATTR aattr WITH(NOLOCK) ON aa.APP_ASSET_ID = aattr.APP_ASSET_ID JOIN APP_COLLATERAL col WITH(NOLOCK) ON aa.APP_ID = col.APP_ID JOIN APP_COLLATERAL_REGISTRATION acr WITH(NOLOCK) ON col.APP_COLLATERAL_ID = acr.APP_COLLATERAL_ID  JOIN REF_MASTER_LOS rml WITH(NOLOCK) ON acr.MR_OWNER_RELATIONSHIP_CODE = rml.REF_MASTER_CODE WHERE APP_NO = '"+ appno +"' AND aattr.ASSET_ATTR_NAME = 'ASSET REGION') as Orig unpivot (value for [Code] in ([ASSET_USAGE],[CUST_PERSONAL_RELATIONSHIP],[ID_TYPE]) )as unpiv) as mastername JOIN REF_MASTER_LOS rf WITH(NOLOCK) ON rf.REF_MASTER_Code = mastername.value WHERE rf.IS_ACTIVE = '1') AS ref  PIVOT (MAX(ref.REF_MASTER_NAME) for [Code] in ([ASSET_USAGE],[CUST_PERSONAL_RELATIONSHIP],[ID_TYPE])) as piv"), {  row ->

			ResultSetMetaData rsmd = row.getMetaData()
			colmcount = rsmd.getColumnCount()


			for(i = 0 ; i < colmcount ; i++){
				assetinfo = (row[i])
				listassetinfo.add(assetinfo)
			}

		})
		return listassetinfo
	}

	@Keyword
	public NAP2AccessoriesStoreDB (Sql instance, String appno){
		String accessoriesdata
		ArrayList <String> listaccessories = new ArrayList<String>()
		instance.eachRow(("SELECT aa.SUPPL_CODE , aa.SUPPL_NAME , aaa.ASSET_ACCESSORY_CODE , aaa.ASSET_ACCESSORY_NAME , CONVERT(INT, aaa.ACCESSORY_PRICE_AMT) , CONVERT(INT, aaa.DOWN_PAYMENT_PRCNT) , CONVERT(INT, aaa.DOWN_PAYMENT_AMT) , aaa.ACCESSORY_NOTES  FROM APP_ASSET_ACCESSORY aaa WITH(NOLOCK) JOIN APP_ASSET aa WITH(NOLOCK) ON aaa.APP_ASSET_ID = aa.APP_ASSET_ID JOIN APP a WITH(NOLOCK) ON aa.APP_ID = a.APP_ID WHERE APP_NO = '"+ appno +"'"), {  row ->

			ResultSetMetaData rsmd = row.getMetaData()
			colmcount = rsmd.getColumnCount()


			for(i = 0 ; i < colmcount ; i++){
				accessoriesdata = (row[i])
				listaccessories.add(accessoriesdata)
			}
		})
		return listaccessories
	}

	@Keyword
	public NAP2InsuranceCustStoreDB (Sql instance, String appno){
		String insurancedata
		ArrayList<String> insurancelist = new ArrayList<>()
		instance.eachRow(("SELECT aio.CUST_INSCO_BRANCH_NAME , CONVERT(INT , aio.CUST_CVG_AMT) , aio.INS_POLICY_NO , aio.INS_POLICY_NAME , FORMAT(aio.CUST_COVER_START_DT, 'MM/dd/yyyy') , FORMAT(aio.END_DT, 'MM/dd/yyyy') , aio.CUST_NOTES  FROM APP_INS ai WITH(NOLOCK) JOIN APP a WITH(NOLOCK) ON ai.APP_ID = a.APP_ID JOIN APP_INS_OBJ aio WITH(NOLOCK) ON a.APP_ID = aio.APP_ID WHERE APP_NO = '"+ appno +"'"), {  row ->

			ResultSetMetaData rsmd = row.getMetaData()
			colmcount = rsmd.getColumnCount()


			for(i = 0 ; i < colmcount ; i++){
				insurancedata = (row[i])
				insurancelist.add(insurancedata)
			}
		})
		return insurancelist
	}

	@Keyword
	public NAP2InsuranceCustMFStoreDB (Sql instance, String appno){
		String insurancedata
		ArrayList<String> insurancelist = new ArrayList<>()
		instance.eachRow(("SELECT aio.CUST_INSCO_BRANCH_NAME , CONVERT(INT , aio.CUST_CVG_AMT) , aio.INS_POLICY_NO , aio.INS_POLICY_NAME , FORMAT(aio.CUST_COVER_START_DT, 'MM/dd/yyyy') , FORMAT(aio.START_DT, 'MM/dd/yyyy'), aio.CUST_NOTES, FORMAT(aio.END_DT, 'MM/dd/yyyy')  FROM APP_INS ai WITH(NOLOCK) JOIN APP a WITH(NOLOCK) ON ai.APP_ID = a.APP_ID JOIN APP_INS_OBJ aio WITH(NOLOCK) ON a.APP_ID = aio.APP_ID WHERE APP_NO = '"+ appno +"'"), {  row ->

			ResultSetMetaData rsmd = row.getMetaData()
			colmcount = rsmd.getColumnCount()


			for(i = 0 ; i < colmcount ; i++){
				insurancedata = (row[i])
				insurancelist.add(insurancedata)
			}
		})
		return insurancelist
	}

	@Keyword
	public NAP2InsuranceMFStoreDB (Sql instance, String appno){
		String insurancedata
		ArrayList<String> insurancelist = new ArrayList<>()
		instance.eachRow(("SELECT aio.INS_ASSET_REGION , CONVERT(INT , aio.CVG_AMT) , rml.REF_MASTER_NAME , SUBQ.REF_MASTER_NAME , aio.INSCO_BRANCH_NAME , aio.NOTES, INS_LENGTH , CAST(CUST_ADMIN_FEE_AMT as int) , CAST(CUST_STAMP_DUTY_FEE as INT)  FROM APP_INS ai WITH(NOLOCK) JOIN APP a WITH(NOLOCK) ON ai.APP_ID = a.APP_ID JOIN APP_INS_OBJ aio WITH(NOLOCK) ON a.APP_ID = aio.APP_ID JOIN REF_MASTER_LOS rml WITH(NOLOCK) ON rml.REF_MASTER_CODE = aio.INS_ASSET_COVER_PERIOD, (SELECT rml.REF_MASTER_NAME, APP_NO FROM APP_INS_OBJ aio WITH(NOLOCK) JOIN APP a WITH(NOLOCK) ON aio.APP_ID = a.APP_ID JOIN REF_MASTER_LOS rml WITH(NOLOCK) ON rml.REF_MASTER_CODE = aio.PAY_PERIOD_TO_INSCO) as SUBQ WHERE a.APP_NO = '"+ appno +"' AND a.APP_NO = SUBQ.APP_NO"), {  row ->

			ResultSetMetaData rsmd = row.getMetaData()
			colmcount = rsmd.getColumnCount()


			for(i = 0 ; i < colmcount ; i++){
				insurancedata = (row[i])
				insurancelist.add(insurancedata)
			}

		})
		return insurancelist
	}


	@Keyword
	public NAP2InsuranceMainCVGtoreDB (Sql instance, String appno){
		String insurancedata
		ArrayList<String> insurancelist = new ArrayList<>()
		instance.eachRow(("SELECT rml.REF_MASTER_NAME FROM APP_INS_MAIN_CVG aimc JOIN APP_INS_OBJ aio WITH(NOLOCK) ON aio.APP_INS_OBJ_ID = aimc.APP_INS_OBJ_ID JOIN APP a WITH(NOLOCK) ON a.APP_ID = aio.APP_ID JOIN REF_MASTER_LOS rml WITH(NOLOCK) ON rml.REF_MASTER_CODE = aimc.MR_MAIN_CVG_TYPE_CODE WHERE APP_NO = '"+ appno +"'"), {  row ->

			ResultSetMetaData rsmd = row.getMetaData()
			colmcount = rsmd.getColumnCount()


			for(i = 0 ; i < colmcount ; i++){
				insurancedata = (row[i])
				insurancelist.add(insurancedata)
			}
		})
		return insurancelist
	}

	@Keyword
	public NAP2InsuranceAddCVGtoreDB (Sql instance, String appno){
		String insurancedata
		ArrayList<String> insurancelist = new ArrayList<>()
		instance.eachRow(("SELECT rml.REF_MASTER_NAME FROM APP_INS_MAIN_CVG aimc JOIN APP_INS_OBJ aio WITH(NOLOCK) ON aio.APP_INS_OBJ_ID = aimc.APP_INS_OBJ_ID JOIN APP a WITH(NOLOCK) ON a.APP_ID = aio.APP_ID JOIN APP_INS_ADD_CVG aiac WITH(NOLOCK) ON aiac.APP_INS_MAIN_CVG_ID = aimc.APP_INS_MAIN_CVG_ID JOIN REF_MASTER_LOS rml WITH(NOLOCK) ON rml.REF_MASTER_CODE = aiac.MR_ADD_CVG_TYPE_CODE WHERE APP_NO = '"+ appno +"' GROUP BY rml.REF_MASTER_NAME"), {  row ->

			ResultSetMetaData rsmd = row.getMetaData()
			colmcount = rsmd.getColumnCount()


			for(i = 0 ; i < colmcount ; i++){
				insurancedata = (row[i])
				insurancelist.add(insurancedata)
			}

		})
		return insurancelist
	}


	@Keyword
	public NAP2InsuranceMainPremiRateStoreDB (Sql instance, String appno){
		String insurancedata
		ArrayList<String> insurancelist = new ArrayList<>()
		instance.eachRow(("SELECT CUST_MAIN_PREMI_RATE FROM APP_INS_MAIN_CVG aimc JOIN APP_INS_OBJ aio WITH(NOLOCK) ON aio.APP_INS_OBJ_ID = aimc.APP_INS_OBJ_ID JOIN APP a WITH(NOLOCK) ON a.APP_ID = aio.APP_ID WHERE a.APP_NO = '"+ appno +"'"), {  row ->

			ResultSetMetaData rsmd = row.getMetaData()
			colmcount = rsmd.getColumnCount()


			for(i = 0 ; i < colmcount ; i++){
				insurancedata = (row[i])
				insurancelist.add(insurancedata)
			}
		})
		return insurancelist
	}

	@Keyword
	public NAP2InsuranceMultiMainCVGtoreDB (Sql instance, String appno){
		String insurancedata
		ArrayList<String> insurancelist = new ArrayList<>()
		instance.eachRow(("SELECT [YEAR] , IS_CAPITALIZED , [PAID] ,  [SUMINSURED] , [MAINCVG]  FROM (SELECT [YEAR], test.Code, REF_MASTER_NAME, IS_CAPITALIZED,  [SUMINSURED] FROM (select [YEAR], [Code], value, IS_CAPITALIZED,  [SUMINSURED] from (SELECT YEAR_NO AS [YEAR], IS_CAPITALIZED , MR_INS_PAID_BY_CODE as [PAID], FORMAT(aimc.SUM_INSURED_PRCNT, 'N0') as [SUMINSURED], MR_MAIN_CVG_TYPE_CODE as [MAINCVG] FROM APP_INS_MAIN_CVG aimc JOIN APP_INS_OBJ aio WITH(NOLOCK) ON aio.APP_INS_OBJ_ID = aimc.APP_INS_OBJ_ID JOIN APP a WITH(NOLOCK) ON a.APP_ID = aio.APP_ID WHERE a.APP_NO = '"+ appno +"') as Orig unpivot(value for [Code] in ([PAID],[MAINCVG]) )as unpiv) as test JOIN REF_MASTER_LOS rf on rf.REF_MASTER_Code = test.value) AS A PIVOT (MAX(A.REF_MASTER_NAME) for [Code] in ([PAID],[MAINCVG])) as piv"), {  row ->

			ResultSetMetaData rsmd = row.getMetaData()
			colmcount = rsmd.getColumnCount()


			for(i = 0 ; i < colmcount ; i++){
				insurancedata = (row[i])
				insurancelist.add(insurancedata)
			}
		})
		return insurancelist
	}

	@Keyword
	public NAP2InsuranceMultiAddCVGtoreDB (Sql instance, String appno){
		String insurancedata
		ArrayList<String> insurancelist = new ArrayList<>()
		instance.eachRow(("SELECT YEAR_NO ,rml.REF_MASTER_NAME, CASE WHEN(rml.REF_MASTER_NAME = 'TPL' OR rml.REF_MASTER_NAME = 'Tanggung Jawab Hukum Terhadap Penumpang' OR rml.REF_MASTER_NAME = 'Kecelakaan Diri Untuk Penumpang') THEN CUST_ADD_PREMI_AMT ELSE CUST_ADD_PREMI_RATE END FROM APP_INS_MAIN_CVG aimc JOIN APP_INS_OBJ aio WITH(NOLOCK) ON aio.APP_INS_OBJ_ID = aimc.APP_INS_OBJ_ID JOIN APP a WITH(NOLOCK) ON a.APP_ID = aio.APP_ID JOIN APP_INS_ADD_CVG aiac WITH(NOLOCK) ON aiac.APP_INS_MAIN_CVG_ID = aimc.APP_INS_MAIN_CVG_ID JOIN REF_MASTER_LOS rml WITH(NOLOCK) ON rml.REF_MASTER_CODE = aiac.MR_ADD_CVG_TYPE_CODE WHERE APP_NO = '"+ appno +"' ORDER BY YEAR_NO,APP_INS_ADD_CVG_ID"), {  row ->
			//SELECT YEAR_NO ,rml.REF_MASTER_NAME, CASE WHEN(rml.REF_MASTER_NAME = 'TPL' OR rml.REF_MASTER_NAME = 'Tanggung Jawab Hukum Terhadap Penumpang' OR rml.REF_MASTER_NAME = 'Kecelakaan Diri Untuk Penumpang') THEN CUST_ADD_PREMI_AMT ELSE FORMAT(CUST_ADD_PREMI_RATE, 'N4') END FROM APP_INS_MAIN_CVG aimc JOIN APP_INS_OBJ aio WITH(NOLOCK) ON aio.APP_INS_OBJ_ID = aimc.APP_INS_OBJ_ID JOIN APP a WITH(NOLOCK) ON a.APP_ID = aio.APP_ID JOIN APP_INS_ADD_CVG aiac WITH(NOLOCK) ON aiac.APP_INS_MAIN_CVG_ID = aimc.APP_INS_MAIN_CVG_ID JOIN REF_MASTER_LOS rml WITH(NOLOCK) ON rml.REF_MASTER_CODE = aiac.MR_ADD_CVG_TYPE_CODE WHERE APP_NO = '"+ appno +"' ORDER BY YEAR_NO
			ResultSetMetaData rsmd = row.getMetaData()
			colmcount = rsmd.getColumnCount()


			for(i = 0 ; i < colmcount ; i++){
				insurancedata = (row[i])
				insurancelist.add(insurancedata)
			}

		})
		return insurancelist
	}

	@Keyword
	public NAP2InsuranceCVGStoreDB (Sql instance, String appno){
		String insurancedata
		ArrayList <String> listCVG = new ArrayList<String>()
		instance.eachRow(("SELECT CONVERT(INT, e.SUM_INSURED_AMT) , CONVERT(INT, e.CUST_ADD_PREMI_AMT) , CONVERT(INT, TOTAL_CUST_FEE_AMT) , CONVERT(INT , a.TOTAL_CUST_DISC_AMT) , CONVERT(INT, TOTAL_PREMI_PAID_BY_CUST_AMT)  FROM APP_INS a JOIN APP b ON a.APP_ID = b.APP_ID JOIN APP_INS_OBJ c ON b.APP_ID = c.APP_ID JOIN APP_INS_MAIN_CVG d ON d.APP_INS_OBJ_ID = c.APP_INS_OBJ_ID JOIN APP_INS_ADD_CVG e ON e.APP_INS_MAIN_CVG_ID = d.APP_INS_MAIN_CVG_ID JOIN REF_MASTER_LOS f ON f.REF_MASTER_CODE = c.INS_ASSET_COVER_PERIOD JOIN REF_MASTER_LOS g ON g.REF_MASTER_CODE = c.PAY_PERIOD_TO_INSCO JOIN REF_MASTER_LOS h ON h.REF_MASTER_CODE = d.MR_MAIN_CVG_TYPE_CODE WHERE APP_NO = '"+ appno +"'"), {  row ->

			ResultSetMetaData rsmd = row.getMetaData()
			colmcount = rsmd.getColumnCount()


			for(i = 0 ; i < colmcount ; i++){
				insurancedata = (row[i])
				listCVG.add(insurancedata)
			}
		})
		return listCVG
	}


	@Keyword
	public NAP2LifeInsuranceStoreDB (Sql instance, String appno){
		String lifeinsurancedata
		ArrayList <String> listlifeinsurance = new ArrayList<String>()
		instance.eachRow(("SELECT alih.LIFE_INSCO_BRANCH_NAME , rml.REF_MASTER_NAME , 100 - CONVERT(int, alih.PAID_IN_ADV_PRCNT) , NEW_COVER_NOTES , IS_CUST_COVER , IS_GUARANTOR_COVER , IS_SPOUSE_COVER , CONVERT(INT, CUST_ADMIN_FEE_AMT)  FROM APP_LIFE_INS_D alid WITH(NOLOCK) JOIN APP_LIFE_INS_H alih WITH(NOLOCK) ON alid.APP_LIFE_INS_H_ID = alih.APP_LIFE_INS_H_ID JOIN APP a WITH(NOLOCK) ON alih.APP_ID = a.APP_ID JOIN REF_MASTER_LOS rml WITH(NOLOCK) ON rml.REF_MASTER_CODE = alih.MR_LIFE_INS_PAID_METHOD_CODE WHERE APP_NO = '"+ appno +"'"), {  row ->

			ResultSetMetaData rsmd = row.getMetaData()
			colmcount = rsmd.getColumnCount()


			for(i = 0 ; i < colmcount ; i++){
				lifeinsurancedata = (row[i])
				listlifeinsurance.add(lifeinsurancedata)
			}
		})
		return listlifeinsurance
	}

	@Keyword
	public NAP2FinancialStoreDB (Sql instance, String appno){
		String financialdata
		ArrayList <String> listfinancialdata = new ArrayList<String>()
		instance.eachRow(("SELECT CONVERT(INT, TOTAL_FEE_AMT) , CONVERT(INT, TOTAL_FEE_CPTLZ_AMT) , rml.REF_MASTER_NAME , EFFECTIVE_RATE_PRCNT , FLAT_RATE_PRCNT , GRACE_PERIOD , afd.MR_GRACE_PERIOD_TYPE_CODE , CONVERT(INT,TDP_PAID_COY_AMT)  FROM APP_FIN_DATA afd WITH(NOLOCK) JOIN APP a WITH(NOLOCK) ON a.APP_ID = afd.APP_ID JOIN REF_MASTER_LOS rml ON rml.REF_MASTER_CODE = afd.MR_PROVISION_FEE_CALC_METHOD_CODE WHERE APP_NO = '"+ appno +"'"), {  row ->

			ResultSetMetaData rsmd = row.getMetaData()
			colmcount = rsmd.getColumnCount()


			for(i = 0 ; i < colmcount ; i++){
				financialdata = (row[i])
				listfinancialdata.add(financialdata)
			}
		})
		return listfinancialdata
	}

	@Keyword
	public NAP2FinancialFeeStoreDB (Sql instance, String appno){
		String financialfeedata
		ArrayList <String> listfinancialfeedata = new ArrayList<String>()
		instance.eachRow(("SELECT CONVERT(INT,app_fee_amt), CONVERT(INT,app_fee_prcnt),CASE WHEN is_cptlz = 1 THEN 'Yes' ELSE 'No' END AS IS_CPTLZ, CONVERT(INT,cptlz_amt) FROM APP_FEE af with(nolock) join app a with(nolock) on af.app_id = a.app_id where app_no = '"+appno+"'"), {  row ->

			ResultSetMetaData rsmd = row.getMetaData()
			colmcount = rsmd.getColumnCount()


			for(i = 0 ; i < colmcount ; i++){
				financialfeedata = (row[i])
				listfinancialfeedata.add(financialfeedata)
			}
		})
		return listfinancialfeedata
	}


	@Keyword
	public NAP2SubsidyStoreDB (Sql instance, String appno){
		String subsidy
		ArrayList <String> listsubsidy = new ArrayList<String>()
		instance.eachRow(("select MR_SUBSIDY_FROM_TYPE_NAME, SUBSIDY_FROM_VALUE_NAME, MR_SUBSIDY_ALLOC_NAME, MR_SUBSIDY_SOURCE_NAME, CONVERT(INT, SUBSIDY_AMT), CONVERT(INT, SUBSIDY_PRCNT) from APP_SUBSIDY asub JOIN APP a ON a.APP_ID = asub.APP_ID WHERE APP_NO = '"+ appno +"'"), {  row ->

			ResultSetMetaData rsmd = row.getMetaData()
			colmcount = rsmd.getColumnCount()


			for(i = 0 ; i < colmcount ; i++){
				subsidy = (row[i])
				listsubsidy.add(subsidy)
			}
		})
		return listsubsidy
	}

	@Keyword
	public NAP2TermConditionStoreDB (Sql instance, String appno){
		String termandcondition
		ArrayList <String> listTC = new ArrayList<String>()
		instance.eachRow(("SELECT rtl.TC_NAME, at.IS_MANDATORY, IS_CHECKED, FORMAT(PROMISED_DT, 'MM/dd/yyy'), FORMAT(EXPIRED_DT, 'MM/dd/yyyy'), IS_WAIVED FROM APP_TC at WITH(NOLOCK)JOIN APP a WITH(NOLOCK) ON a.APP_ID = at.APP_ID JOIN REF_TC_LOS rtl WITH(NOLOCK) ON rtl.TC_CODE = at.TC_CODE WHERE APP_NO = '"+ appno +"'"), {  row ->

			ResultSetMetaData rsmd = row.getMetaData()
			colmcount = rsmd.getColumnCount()


			for(i = 0 ; i < colmcount ; i++){
				termandcondition = (row[i])
				listTC.add(termandcondition)
			}
		})
		return listTC
	}


	@Keyword
	public NAP3CommissionDataStoreDB(Sql instance, String appno, String allocationType){
		HashMap<String, ArrayList> result = new HashMap<>()
		ArrayList<String> resultSupp = new ArrayList<String>()
		ArrayList<String> resultSuppEmp = new ArrayList<String>()
		ArrayList<String> resultRef = new ArrayList<String>()

		if(allocationType.equalsIgnoreCase("Amount")){
			instance.eachRow(("SELECT COMMISSION_RECIPIENT_REF_NO, MR_COMMISSION_SOURCE_CODE, COMMISSION_AMT, REFUND_AMT FROM APP_COMMISSION_H ach WITH(NOLOCK) JOIN APP_COMMISSION_D acd WITH(NOLOCK) ON acd.APP_COMMISSION_H_ID = ach.APP_COMMISSION_H_ID WHERE APP_id = (Select app_id from app WITH(NOLOCK) where app_no = '"+appno+"') and MR_COMMISSION_RECIPIENT_TYPE_CODE = 'SUPPLIER'"), {  row ->
				if(row[3]==0.00){
					resultSupp.add("-1")
				}
				else{
					resultSupp.add(row[2])
				}
			})
			instance.eachRow(("SELECT COMMISSION_RECIPIENT_REF_NO, MR_COMMISSION_SOURCE_CODE, COMMISSION_AMT, REFUND_AMT FROM APP_COMMISSION_H ach WITH(NOLOCK) JOIN APP_COMMISSION_D acd WITH(NOLOCK) ON acd.APP_COMMISSION_H_ID = ach.APP_COMMISSION_H_ID WHERE APP_id = (Select app_id from app WITH(NOLOCK) where app_no = '"+appno+"') and MR_COMMISSION_RECIPIENT_TYPE_CODE = 'SUPPLIER_EMP'"), {  row ->
				if(row[3]==0.00){
					resultSuppEmp.add("-1")
				}
				else{
					resultSuppEmp.add(row[2])
				}
			})
			instance.eachRow(("SELECT COMMISSION_RECIPIENT_REF_NO, MR_COMMISSION_SOURCE_CODE, COMMISSION_AMT, REFUND_AMT FROM APP_COMMISSION_H ach WITH (NOLOCK) JOIN APP_COMMISSION_D acd WITH (NOLOCK) ON ach.APP_COMMISSION_H_ID = acd.APP_COMMISSION_H_ID WHERE APP_id = (Select app_id from app WITH (NOLOCK) where app_no = '"+appno+"') and MR_COMMISSION_RECIPIENT_TYPE_CODE = 'REFERANTOR'"), {  row ->
				if(row[3]==0.00){
					resultRef.add("-1")
				}
				else{
					resultRef.add(row[2])
				}
			})
		}
		else if(allocationType.equalsIgnoreCase("Percentage")){
			instance.eachRow(("SELECT COMMISSION_RECIPIENT_REF_NO, MR_COMMISSION_SOURCE_CODE, CASE WHEN (COMMISSION_AMT/NULLIF(REFUND_AMT, 0))*100 IS NULL THEN 0 ELSE (COMMISSION_AMT/NULLIF(REFUND_AMT, 0))*100 END, REFUND_AMT FROM APP_COMMISSION_H ach WITH(NOLOCK) JOIN APP_COMMISSION_D acd WITH(NOLOCK) ON acd.APP_COMMISSION_H_ID = ach.APP_COMMISSION_H_ID WHERE APP_id = (Select app_id from app WITH(NOLOCK) where app_no = '"+appno+"') and MR_COMMISSION_RECIPIENT_TYPE_CODE = 'SUPPLIER'"), {  row ->

				if(row[3]==0.00){
					resultSupp.add("-1")
				}
				else{
					resultSupp.add(row[2])
				}
			})
			instance.eachRow(("SELECT COMMISSION_RECIPIENT_REF_NO, MR_COMMISSION_SOURCE_CODE, CASE WHEN (COMMISSION_AMT/NULLIF(REFUND_AMT, 0))*100 IS NULL THEN 0 ELSE (COMMISSION_AMT/NULLIF(REFUND_AMT, 0))*100 END, REFUND_AMT FROM APP_COMMISSION_H ach WITH(NOLOCK) JOIN APP_COMMISSION_D acd WITH(NOLOCK) ON acd.APP_COMMISSION_H_ID = ach.APP_COMMISSION_H_ID WHERE APP_id = (Select app_id from app WITH(NOLOCK) where app_no = '"+appno+"') and MR_COMMISSION_RECIPIENT_TYPE_CODE = 'SUPPLIER_EMP'"), {  row ->

				if(row[3]==0.00){
					resultSuppEmp.add("-1")
				}
				else{
					resultSuppEmp.add(row[2])
				}
			})
			instance.eachRow(("SELECT COMMISSION_RECIPIENT_REF_NO, MR_COMMISSION_SOURCE_CODE, CASE WHEN (COMMISSION_AMT/NULLIF(REFUND_AMT, 0))*100 IS NULL THEN 0 ELSE (COMMISSION_AMT/NULLIF(REFUND_AMT, 0))*100 END, REFUND_AMT FROM APP_COMMISSION_H ach WITH(NOLOCK) JOIN APP_COMMISSION_D acd WITH(NOLOCK) ON acd.APP_COMMISSION_H_ID = ach.APP_COMMISSION_H_ID WHERE APP_id = (Select app_id from app WITH(NOLOCK) where app_no = '"+appno+"') and MR_COMMISSION_RECIPIENT_TYPE_CODE = 'REFERANTOR'"), {  row ->

				if(row[3]==0.00){
					resultRef.add("-1")
				}
				else{
					resultRef.add(row[2])
				}
			})
		}

		result.put("Supp",resultSupp)
		result.put("SuppEmp",resultSuppEmp)
		result.put("Ref",resultRef)
		return result
	}


	@Keyword
	public countCommissionRecipientDB(Sql instance, String appno){
		ArrayList<Integer> listcount = new ArrayList<>()
		instance.eachRow(("SELECT 'SUPPLIER' , COUNT(*)  FROM APP_COMMISSION_H WITH(NOLOCK) WHERE APP_id = (Select app_id from app where app_no = '"+appno+"') AND MR_COMMISSION_RECIPIENT_TYPE_CODE = 'SUPPLIER' UNION SELECT 'SUPPLIER_EMP' , COUNT(*)  FROM APP_COMMISSION_H WITH(NOLOCK) WHERE APP_id = (Select app_id from app where app_no = '"+appno+"') AND MR_COMMISSION_RECIPIENT_TYPE_CODE = 'SUPPLIER_EMP' UNION SELECT 'REFERANTOR' , COUNT(*)  FROM APP_COMMISSION_H WHERE APP_id = (Select app_id from app where app_no = '"+appno+"') AND MR_COMMISSION_RECIPIENT_TYPE_CODE = 'REFERANTOR'"), {  row ->
			listcount.add(row[1])
		})
		return listcount
	}

	@Keyword
	public countCommissionSourceSupplierDB(Sql instance, String appno){
		Integer countCS
		instance.eachRow(("SELECT ach.APP_COMMISSION_H_ID, COMMISSION_RECIPIENT_REF_NO, COUNT(*)  FROM APP_COMMISSION_H ach WITH (NOLOCK) JOIN APP_COMMISSION_D acd WITH(NOLOCK) ON acd.APP_COMMISSION_H_ID = ach.APP_COMMISSION_H_ID WHERE APP_id = (Select app_id from app WITH(NOLOCK) where app_no = '"+appno+"') and MR_COMMISSION_RECIPIENT_TYPE_CODE = 'SUPPLIER' GROUP BY COMMISSION_RECIPIENT_REF_NO, ach.APP_COMMISSION_H_ID ORDER BY ach.APP_COMMISSION_H_ID"), {  row ->
			countCS = row[2]
		})
		return countCS
	}

	@Keyword
	public countCommissionSourceSupplierEmpDB(Sql instance, String appno){
		ArrayList<Integer> countCS = new ArrayList<>()
		instance.eachRow(("SELECT ach.APP_COMMISSION_H_ID, COMMISSION_RECIPIENT_REF_NO, COUNT(MR_COMMISSION_SOURCE_CODE)  FROM APP_COMMISSION_H ach WITH(NOLOCK) JOIN APP_COMMISSION_D acd WITH(NOLOCK) ON acd.APP_COMMISSION_H_ID = ach.APP_COMMISSION_H_ID WHERE APP_id = (Select app_id from app WITH(NOLOCK) where app_no = '"+appno+"') and MR_COMMISSION_RECIPIENT_TYPE_CODE = 'SUPPLIER_EMP' GROUP BY COMMISSION_RECIPIENT_REF_NO, ach.APP_COMMISSION_H_ID ORDER BY ach.APP_COMMISSION_H_ID"), {  row ->
			println(row[2])
			countCS.add(row[2])
		})
		return countCS
	}

	@Keyword
	public countCommissionSourceReferantorDB(Sql instance, String appno){
		ArrayList<Integer> countCS = new ArrayList<>()
		instance.eachRow(("SELECT ach.APP_COMMISSION_H_ID, COMMISSION_RECIPIENT_REF_NO, COUNT(MR_COMMISSION_SOURCE_CODE)  FROM APP_COMMISSION_H ach WITH(NOLOCK) JOIN APP_COMMISSION_D acd WITH(NOLOCK) ON acd.APP_COMMISSION_H_ID = ach.APP_COMMISSION_H_ID WHERE APP_id = (Select app_id from app WITH(NOLOCK) where app_no = '"+appno+"') and MR_COMMISSION_RECIPIENT_TYPE_CODE = 'REFERANTOR' GROUP BY COMMISSION_RECIPIENT_REF_NO, ach.APP_COMMISSION_H_ID ORDER BY ach.APP_COMMISSION_H_ID"), {  row ->
			countCS.add(row[2])
		})
		return countCS
	}


	@Keyword
	public NAP3ReservedFundDataStoreDB(Sql instance, String appno){
		ArrayList<String> rsvAmt = new ArrayList<>()
		instance.eachRow(("select reserved_fund_amt from app_reserved_fund WITH(NOLOCK) where app_id = (select app_id from app where app_no='"+appno+"')"), {  row ->
			rsvAmt.add(row[0])
		})
		return rsvAmt
	}


	@Keyword
	public NAP4CustomerDetailPersonalStoreData (Sql instance, String appno, String name){
		String customerdetail
		ArrayList<String> listcust = new ArrayList<>()
		instance.eachRow(("SELECT [Custprefix] , [no_of_dependent] , [no_of_residence] , [family_card_no] , [IS_RIP] , [IS_VIP] , [IS_AFF_MF] , [SALUTATION] , [NICKNAME] , [Custsuffix] , [NATIONALITY] , [Countryname] , [EDUCATION] , [RELIGION] , [VIPNOTES]  FROM (SELECT [Custprefix],  [no_of_dependent], [no_of_residence], [family_card_no], [IS_RIP], [IS_VIP], [IS_AFF_MF], [NICKNAME], [Custsuffix], [Countryname], [VIPNOTES], mastername.Code, REF_MASTER_NAME FROM (select [Custprefix], [no_of_dependent], [no_of_residence], [family_card_no], [IS_RIP], [IS_VIP], [IS_AFF_MF], [NICKNAME], [Custsuffix], [Countryname], [VIPNOTES], [Code], value FROM (SELECT MR_EDUCATION_CODE AS 'EDUCATION', MR_NATIONALITY_CODE as 'NATIONALITY', MR_RELIGION_CODE as 'RELIGION', MR_SALUTATION_CODE as 'SALUTATION', acp.CUST_PREFIX_NAME as [Custprefix], acp.NO_OF_DEPENDENTS as [no_of_dependent] , acp.NO_OF_RESIDENCE as [no_of_residence], acp.FAMILY_CARD_NO as [family_card_no], acp.IS_REST_IN_PEACE as [IS_RIP], ac.IS_VIP as [IS_VIP], ac.IS_AFFILIATE_WITH_MF as [IS_AFF_MF], acp.NICK_NAME as [NICKNAME], acp.CUST_SUFFIX_NAME as [Custsuffix], rc.COUNTRY_NAME as [Countryname], ac.VIP_NOTES as [VIPNOTES] FROM APP_CUST_PERSONAL acp WITH(NOLOCK) join app_cust ac WITH(NOLOCK) ON ac.app_cust_id = acp.APP_CUST_ID JOIN APP a WITH(NOLOCK) ON ac.APP_ID = a.APP_ID JOIN APP_CUST_GRP acg WITH(NOLOCK) ON acg.APP_CUST_ID = ac.APP_CUST_ID JOIN V_REF_COUNTRY rc WITH(NOLOCK) ON rc.COUNTRY_CODE = acp.NATIONALITY_COUNTRY_CODE WHERE ac.cust_name='"+ name +"' and app_no ='"+ appno +"') as Orig unpivot (value for [Code] in ([EDUCATION],[NATIONALITY],[RELIGION],[SALUTATION]) )as unpiv) as mastername JOIN REF_MASTER_LOS rf WITH(NOLOCK) ON rf.REF_MASTER_Code = mastername.value WHERE rf.IS_ACTIVE = '1') AS ref PIVOT (MAX(ref.REF_MASTER_NAME) for [Code] in ([EDUCATION],[NATIONALITY],[RELIGION],[SALUTATION])) as piv"), {  row ->

			ResultSetMetaData rsmd = row.getMetaData()
			colmcount = rsmd.getColumnCount()


			for(i = 0 ; i < colmcount ; i++){
				customerdetail = (row[i])
				listcust.add(customerdetail)
			}
		})
		return listcust
	}


	@Keyword
	public NAP4AddressStoreData (Sql instance, String appno, String name, String addresstype){
		String address
		ArrayList<String> listaddr = new ArrayList<>()
		instance.eachRow(("SELECT [CUST_ADDR_TYPE] , [ADDR] , [RT] , [RW] , [ZIPCODE] , [KECAMATAN] , [KELURAHAN] , [KOTA] , [PHN1AREA] , [PHN1] , [PHN1EXT] , [PHN2AREA] , [PHN2] , [PHN2EXT] , [PHN3AREA] , [PHN3] , [PHN3EXT] , [FAXAREA] , [FAX] , [BUILDING_OWNERSHIP]  FROM (SELECT [ADDR], [RT], [RW], [ZIPCODE], [KECAMATAN], [KELURAHAN], [KOTA], [PHN1AREA], [PHN1], [PHN1EXT], [PHN2AREA], [PHN2], [PHN2EXT], [PHN3AREA], [PHN3], [PHN3EXT], [FAXAREA], [FAX], mastername.Code, REF_MASTER_NAME FROM (select [ADDR], [RT], [RW], [ZIPCODE], [KECAMATAN], [KELURAHAN], [KOTA], [PHN1AREA], [PHN1], [PHN1EXT], [PHN2AREA], [PHN2], [PHN2EXT], [PHN3AREA], [PHN3], [PHN3EXT], [FAXAREA], [FAX], [Code], value FROM (SELECT MR_CUST_ADDR_TYPE_CODE AS 'CUST_ADDR_TYPE', aca.ADDR AS [ADDR], aca.AREA_CODE_4 AS [RT], aca.AREA_CODE_3 AS [RW], aca.ZIPCODE AS [ZIPCODE], aca.AREA_CODE_1 AS [KECAMATAN], aca.AREA_CODE_2 AS [KELURAHAN], aca.CITY AS [KOTA], aca.PHN_AREA_1 AS [PHN1AREA], aca.PHN_1 AS [PHN1], aca.PHN_EXT_1 AS [PHN1EXT], aca.PHN_AREA_2 AS [PHN2AREA], aca.PHN_2 AS [PHN2], aca.PHN_EXT_2 AS [PHN2EXT], aca.PHN_AREA_3 AS [PHN3AREA], aca.PHN_3 AS [PHN3], aca.PHN_EXT_3 AS [PHN3EXT], aca.FAX_AREA AS [FAXAREA], aca.FAX AS [FAX], MR_HOUSE_OWNERSHIP_CODE AS 'BUILDING_OWNERSHIP' FROM APP_CUST_ADDR aca WITH(NOLOCK) JOIN APP_CUST ac WITH(NOLOCK) ON aca.APP_CUST_ID = ac.APP_CUST_ID JOIN APP a WITH(NOLOCK) ON a.APP_ID = ac.APP_ID JOIN REF_MASTER_LOS rml WITH(NOLOCK) ON rml.REF_MASTER_CODE = aca.MR_CUST_ADDR_TYPE_CODE WHERE a.APP_NO = '"+ appno +"'  AND ac.CUST_NAME = '"+ name +"' AND rml.REF_MASTER_NAME = '"+ addresstype +"') as orig unpivot (value for [Code] in ([CUST_ADDR_TYPE],[BUILDING_OWNERSHIP]) )as unpiv) as mastername JOIN REF_MASTER_LOS rf WITH(NOLOCK) ON rf.REF_MASTER_Code = mastername.value WHERE rf.IS_ACTIVE = '1') AS ref PIVOT (MAX(ref.REF_MASTER_NAME) for [Code] in ([CUST_ADDR_TYPE],[BUILDING_OWNERSHIP])) as piv"), {  row ->

			ResultSetMetaData rsmd = row.getMetaData()
			colmcount = rsmd.getColumnCount()


			for(i = 0 ; i < colmcount ; i++){
				address = (row[i])
				listaddr.add(address)
			}
		})
		return listaddr
	}

	@Keyword
	public NAP4JobdataEmployeePersonalStoreData (Sql instance, String appno, String name){
		String Jobdata
		ArrayList<String> listjob = new ArrayList<>()
		instance.eachRow(("SELECT [PROFCODE] , [JOB_POSITION] , [JOB_STAT] , [ISMFEMP] , [ISWELLKNOWNCOY] , [COYNAME] , [INDUSTRYTYPE] , [COY_SCALE] , [ESTABLISHMENTDATE] , [JOBTITLE] , [NUMOFEMP]  FROM (SELECT [PROFCODE], [ISMFEMP], [ISWELLKNOWNCOY], [COYNAME], [INDUSTRYTYPE], [ESTABLISHMENTDATE], [JOBTITLE], [NUMOFEMP], mastername.Code, REF_MASTER_NAME FROM (select [PROFCODE], [ISMFEMP], [ISWELLKNOWNCOY], [COYNAME], [INDUSTRYTYPE], [ESTABLISHMENTDATE], [JOBTITLE], [NUMOFEMP], [Code], value FROM (SELECT MR_PROFESSION_CODE AS [PROFCODE], MR_JOB_POSITION_CODE AS 'JOB_POSITION', MR_JOB_STAT_CODE AS  'JOB_STAT', acpjd.IS_MF_EMP [ISMFEMP], acpjd.IS_WELLKNOWN_COY AS [ISWELLKNOWNCOY], acpjd.COY_NAME AS [COYNAME], INDUSTRY_TYPE_CODE AS [INDUSTRYTYPE], MR_COY_SCALE_CODE AS 'COY_SCALE', FORMAT(acpjd.EMPLOYMENT_ESTABLISHMENT_DT, 'MM/dd/yyyy') AS [ESTABLISHMENTDATE], JOB_TITLE_NAME AS [JOBTITLE], NUM_OF_EMPLOYEE AS [NUMOFEMP] FROM APP_CUST_PERSONAL_JOB_DATA acpjd WITH(NOLOCK) JOIN APP_CUST_PERSONAL acp WITH(NOLOCK) ON acp.APP_CUST_PERSONAL_ID = acpjd.APP_CUST_PERSONAL_ID JOIN APP_CUST ac WITH(NOLOCK) ON ac.APP_CUST_ID = acp.APP_CUST_ID JOIN APP a WITH(NOLOCK) ON a.APP_ID = ac.APP_ID WHERE a.APP_NO = '"+ appno +"' AND ac.CUST_NAME = '"+ name +"' ) as orig unpivot(value for [Code] in ([JOB_STAT],[COY_SCALE],[JOB_POSITION]))as unpiv) as mastername JOIN REF_MASTER_LOS rf WITH(NOLOCK) ON rf.REF_MASTER_Code = mastername.value WHERE rf.IS_ACTIVE = '1') AS ref PIVOT (MAX(ref.REF_MASTER_NAME) for [Code] in ([JOB_STAT],[COY_SCALE], [JOB_POSITION])) as piv"), {  row ->

			ResultSetMetaData rsmd = row.getMetaData()
			colmcount = rsmd.getColumnCount()


			for(i = 0 ; i < colmcount ; i++){
				Jobdata = (row[i])
				listjob.add(Jobdata)
			}
		})
		return listjob
	}

	@Keyword
	public NAP4JobdataProfPersonalStoreData (Sql instance, String appno, String name){
		String Jobdata
		ArrayList<String> listjob = new ArrayList<>()
		instance.eachRow(("SELECT MR_PROFESSION_CODE , MR_JOB_POSITION_CODE , acpjd.IS_WELLKNOWN_COY , acpjd.COY_NAME , INDUSTRY_TYPE_CODE , MR_COY_SCALE_CODE, FORMAT(acpjd.EMPLOYMENT_ESTABLISHMENT_DT, 'MM/dd/yyyy') , JOB_TITLE_NAME , NUM_OF_EMPLOYEE , MR_INVESTMENT_TYPE_CODE FROM APP_CUST_PERSONAL_JOB_DATA acpjd WITH(NOLOCK) JOIN APP_CUST_PERSONAL acp WITH(NOLOCK) ON acp.APP_CUST_PERSONAL_ID = acpjd.APP_CUST_PERSONAL_ID JOIN APP_CUST ac WITH(NOLOCK) ON ac.APP_CUST_ID = acp.APP_CUST_ID JOIN APP a WITH(NOLOCK) ON a.APP_ID = ac.APP_ID WHERE a.APP_NO = '"+ appno +"' AND ac.CUST_NAME = '"+ name +"'"), {  row ->

			ResultSetMetaData rsmd = row.getMetaData()
			colmcount = rsmd.getColumnCount()


			for(i = 0 ; i < colmcount ; i++){
				Jobdata = (row[i])
				listjob.add(Jobdata)
			}
		})
		return listjob
	}

	@Keyword
	public NAP4JobdataNonProfPersonalStoreData (Sql instance, String appno, String name){
		String Jobdata
		ArrayList<String> listjob = new ArrayList<>()
		instance.eachRow(("SELECT acpjd.MR_PROFESSION_CODE , acpjd.JOB_TITLE_NAME  FROM APP_CUST_PERSONAL_JOB_DATA acpjd WITH(NOLOCK) JOIN APP_CUST_PERSONAL acp ON acp.APP_CUST_PERSONAL_ID = acpjd.APP_CUST_PERSONAL_ID JOIN APP_CUST ac WITH(NOLOCK) ON ac.APP_CUST_ID = acp.APP_CUST_ID JOIN APP a WITH(NOLOCK) ON a.APP_ID = ac.APP_ID WHERE a.APP_NO = '"+ appno +"' AND ac.CUST_NAME = '"+ name +"'"), {  row ->

			ResultSetMetaData rsmd = row.getMetaData()
			colmcount = rsmd.getColumnCount()


			for(i = 0 ; i < colmcount ; i++){
				Jobdata = (row[i])
				listjob.add(Jobdata)
			}
		})
		return listjob
	}

	@Keyword
	public NAP4JobdataSMEPersonalStoreData (Sql instance, String appno, String name){
		String Jobdata
		ArrayList<String> listjob = new ArrayList<>()
		instance.eachRow(("SELECT [PROFCODE] , [JOB_POSITION] , [ISWELLKNOWNCOY] , [COYNAME] , [INDUSTRYTYPE] , [COY_SCALE] , [ESTABLISHMENTDATE] , [JOBTITLE] , [NUMOFEMP] , [INVESTMENT_TYPE]  FROM (SELECT [PROFCODE], [ISWELLKNOWNCOY], [COYNAME], [INDUSTRYTYPE], [ESTABLISHMENTDATE], [JOBTITLE], [NUMOFEMP], mastername.Code, REF_MASTER_NAME FROM (select [PROFCODE], [ISWELLKNOWNCOY], [COYNAME], [INDUSTRYTYPE], [ESTABLISHMENTDATE], [JOBTITLE], [NUMOFEMP], [Code], value FROM (SELECT MR_PROFESSION_CODE AS [PROFCODE], MR_JOB_POSITION_CODE AS 'JOB_POSITION', acpjd.IS_WELLKNOWN_COY AS [ISWELLKNOWNCOY], acpjd.COY_NAME AS [COYNAME], INDUSTRY_TYPE_CODE AS [INDUSTRYTYPE], MR_COY_SCALE_CODE AS 'COY_SCALE', FORMAT(acpjd.EMPLOYMENT_ESTABLISHMENT_DT, 'MM/dd/yyyy') AS [ESTABLISHMENTDATE], JOB_TITLE_NAME AS [JOBTITLE], NUM_OF_EMPLOYEE AS [NUMOFEMP], MR_INVESTMENT_TYPE_CODE AS 'INVESTMENT_TYPE' FROM APP_CUST_PERSONAL_JOB_DATA acpjd WITH(NOLOCK) JOIN APP_CUST_PERSONAL acp ON acp.APP_CUST_PERSONAL_ID = acpjd.APP_CUST_PERSONAL_ID JOIN APP_CUST ac WITH(NOLOCK) ON ac.APP_CUST_ID = acp.APP_CUST_ID JOIN APP a WITH(NOLOCK) ON a.APP_ID = ac.APP_ID WHERE a.APP_NO = '"+ appno +"' AND ac.CUST_NAME = '"+ name +"' ) as orig unpivot (value for [Code] in ([COY_SCALE],[JOB_POSITION],[INVESTMENT_TYPE]))as unpiv) as mastername JOIN REF_MASTER_LOS rf on rf.REF_MASTER_Code = mastername.value WHERE rf.IS_ACTIVE = '1') AS ref PIVOT (MAX(ref.REF_MASTER_NAME) for [Code] in ([COY_SCALE], [JOB_POSITION], [INVESTMENT_TYPE])) as piv"), {  row ->

			ResultSetMetaData rsmd = row.getMetaData()
			colmcount = rsmd.getColumnCount()


			for(i = 0 ; i < colmcount ; i++){
				Jobdata = (row[i])
				listjob.add(Jobdata)
			}
		})
		return listjob
	}

	@Keyword
	public NAP4JobdataAddressStoreData (Sql instance, String appno, String name){
		String Jobdata
		ArrayList<String> listjob = new ArrayList<>()
		instance.eachRow(("SELECT ADDR , AREA_CODE_4 , AREA_CODE_3 , ZIPCODE , AREA_CODE_2 , AREA_CODE_1 , CITY , NOTES , PHN_AREA_1 , PHN_1 , PHN_EXT_1 , PHN_AREA_2 , PHN_2 , PHN_EXT_2 , PHN_AREA_3 , PHN_3 , PHN_EXT_3 , FAX_AREA , FAX  FROM APP_CUST_PERSONAL_JOB_DATA acpjd WITH(NOLOCK) JOIN APP_CUST_PERSONAL acp WITH(NOLOCK) ON acp.APP_CUST_PERSONAL_ID = acpjd.APP_CUST_PERSONAL_ID JOIN APP_CUST ac WITH(NOLOCK) ON ac.APP_CUST_ID = acp.APP_CUST_ID JOIN APP a WITH(NOLOCK) ON a.APP_ID = ac.APP_ID JOIN APP_CUST_ADDR aca WITH(NOLOCK) ON aca.APP_CUST_ID = acp.APP_CUST_ID WHERE a.APP_NO = '"+ appno +"' AND ac.CUST_NAME = '"+ name +"' AND aca.MR_CUST_ADDR_TYPE_CODE = 'JOB'"), {  row ->

			ResultSetMetaData rsmd = row.getMetaData()
			colmcount = rsmd.getColumnCount()


			for(i = 0 ; i < colmcount ; i++){
				Jobdata = (row[i])
				listjob.add(Jobdata)
			}
		})
		return listjob
	}

	@Keyword
	public NAP4PrevJobAddressStoreData (Sql instance, String appno, String name){
		String Jobdata
		ArrayList<String> listjob = new ArrayList<>()
		instance.eachRow(("SELECT PREV_COY_NAME , FORMAT(PREV_EMPLOYMENT_DT, 'MM/dd/yyy') , ADDR , AREA_CODE_4 , AREA_CODE_3 , ZIPCODE , AREA_CODE_2 , AREA_CODE_1 , CITY , NOTES , PHN_AREA_1 , PHN_1 , PHN_EXT_1 , PHN_AREA_2 , PHN_2 , PHN_EXT_2 , PHN_AREA_3 , PHN_3 , PHN_EXT_3 , FAX_AREA , FAX  FROM APP_CUST_PERSONAL_JOB_DATA acpjd WITH(NOLOCK) JOIN APP_CUST_PERSONAL acp WITH(NOLOCK) ON acp.APP_CUST_PERSONAL_ID = acpjd.APP_CUST_PERSONAL_ID JOIN APP_CUST ac WITH(NOLOCK) ON ac.APP_CUST_ID = acp.APP_CUST_ID JOIN APP a WITH(NOLOCK) ON a.APP_ID = ac.APP_ID JOIN APP_CUST_ADDR aca WITH(NOLOCK) ON aca.APP_CUST_ID = acp.APP_CUST_ID WHERE a.APP_NO = '"+ appno +"' AND ac.CUST_NAME = '"+ name +"' AND aca.MR_CUST_ADDR_TYPE_CODE = 'PREV_JOB'"), {  row ->

			ResultSetMetaData rsmd = row.getMetaData()
			colmcount = rsmd.getColumnCount()


			for(i = 0 ; i < colmcount ; i++){
				Jobdata = (row[i])
				listjob.add(Jobdata)
			}
		})
		return listjob
	}

	@Keyword
	public NAP4OtherBizAddressStoredata (Sql instance, String appno, String name){
		String Jobdata
		ArrayList<String> listjob = new ArrayList<>()
		instance.eachRow(("SELECT OTH_BIZ_NAME , OTH_BIZ_TYPE , OTH_BIZ_INDUSTRY_TYPE_CODE , OTH_BIZ_JOB_POSITION , FORMAT(OTH_BIZ_ESTABLISHMENT_DT, 'MM/dd/yyy') , ADDR , AREA_CODE_4 , AREA_CODE_3 , ZIPCODE , AREA_CODE_2 , AREA_CODE_1 , CITY , NOTES , PHN_AREA_1 , PHN_1 , PHN_EXT_1 , PHN_AREA_2 , PHN_2 , PHN_EXT_2 , PHN_AREA_3 , PHN_3 , PHN_EXT_3 , FAX_AREA , FAX  FROM APP_CUST_PERSONAL_JOB_DATA acpjd WITH(NOLOCK) JOIN APP_CUST_PERSONAL acp WITH(NOLOCK) ON acp.APP_CUST_PERSONAL_ID = acpjd.APP_CUST_PERSONAL_ID JOIN APP_CUST ac WITH(NOLOCK) ON ac.APP_CUST_ID = acp.APP_CUST_ID JOIN APP a WITH(NOLOCK) ON a.APP_ID = ac.APP_ID JOIN APP_CUST_ADDR aca WITH(NOLOCK) ON aca.APP_CUST_ID = acp.APP_CUST_ID WHERE a.APP_NO = '"+ appno +"' AND ac.CUST_NAME = '"+ name +"' AND aca.MR_CUST_ADDR_TYPE_CODE = 'OTH_BIZ'"), {  row ->

			ResultSetMetaData rsmd = row.getMetaData()
			colmcount = rsmd.getColumnCount()


			for(i = 0 ; i < colmcount ; i++){
				Jobdata = (row[i])
				listjob.add(Jobdata)
			}
		})
		return listjob
	}

	@Keyword
	public NAP4EmergencyContactStoreData (Sql instance, String appno, String name){
		String emergencycontact
		ArrayList <String> listemergencycontact = new ArrayList<String>()
		instance.eachRow(("SELECT acec.CONTACT_PERSON_NAME ,  rml.REF_MASTER_NAME ,   acec.ID_NO ,  FORMAT(acec.ID_EXPIRED_DT, 'MM/dd/yyy'), acec.MR_CUST_RELATIONSHIP_CODE, acec.EMAIL ,  acec.MR_GENDER_CODE ,  acec.BIRTH_PLACE ,  FORMAT(acec.BIRTH_DT, 'MM/dd/yyyy') ,  acec.MOBILE_PHN_NO_1 ,  acec.MOBILE_PHN_NO_2 ,  acec.ADDR ,  acec.AREA_CODE_4 ,  acec.AREA_CODE_3 ,  acec.ZIPCODE ,  acec.AREA_CODE_1 ,  acec.AREA_CODE_2 ,  acec.CITY ,  acec.PHN_AREA_1 ,  acec.PHN_1 ,  acec.PHN_EXT_1 ,  acec.PHN_AREA_2 ,  acec.PHN_2 ,  acec.PHN_EXT_2 ,  acec.PHN_AREA_3 ,  acec.PHN_3 ,  acec.PHN_EXT_3 FROM APP_CUST_EMRGNC_CNTCT acec WITH(NOLOCK) JOIN APP_CUST ac WITH(NOLOCK) ON ac.APP_CUST_ID = acec.APP_CUST_ID JOIN APP a WITH(NOLOCK) ON a.APP_ID = ac.APP_ID JOIN REF_MASTER_LOS rml WITH(NOLOCK) ON rml.REF_MASTER_CODE = acec.MR_ID_TYPE_CODE WHERE a.APP_NO = '"+ appno +"' AND ac.CUST_NAME = '"+ name +"' AND rml.REF_MASTER_TYPE_CODE = 'ID_TYPE' AND rml.IS_ACTIVE = '1'"), {  row ->

			ResultSetMetaData rsmd = row.getMetaData()
			colmcount = rsmd.getColumnCount()


			for(i = 0 ; i < colmcount ; i++){
				emergencycontact = (row[i])
				listemergencycontact.add(emergencycontact)
			}
		})
		return listemergencycontact


	}

	@Keyword
	public NAP4EmergencyContactStoreDataLookUp (Sql instance, String appno, String name){
		String emergencycontact
		ArrayList <String> listemergencycontact = new ArrayList<String>()
		int i
		instance.eachRow(("SELECT acec.CONTACT_PERSON_NAME , acec.MR_ID_TYPE_CODE , acec.ID_NO , acec.ID_EXPIRED_DT , acec.MOBILE_PHN_NO_1 , acec.EMAIL , acec.MR_GENDER_CODE, acec.BIRTH_PLACE , FORMAT(acec.BIRTH_DT, 'yyyy-MM-dd') , acec.MOBILE_PHN_NO_2, acec.ADDR , acec.AREA_CODE_4 , acec.AREA_CODE_3 , acec.ZIPCODE , acec.AREA_CODE_2 , acec.AREA_CODE_1 , acec.CITY,  acec.PHN_AREA_1 , acec.PHN_1 ,  acec.PHN_EXT_1 ,  acec.PHN_AREA_2 ,  acec.PHN_2 ,  acec.PHN_EXT_2 ,  acec.PHN_AREA_3 ,  acec.PHN_3 ,  acec.PHN_EXT_3, acec.MR_CUST_RELATIONSHIP_CODE FROM APP_CUST_EMRGNC_CNTCT acec WITH(NOLOCK) JOIN APP_CUST ac WITH(NOLOCK) ON ac.APP_CUST_ID = acec.APP_CUST_ID JOIN APP a WITH(NOLOCK) ON a.APP_ID = ac.APP_ID WHERE a.APP_NO = '"+ appno +"' AND ac.CUST_NAME = '"+ name +"'"), {  row ->

			for(i = 0 ; i < 27 ; i++){
				emergencycontact = (row[i])
				listemergencycontact.add(emergencycontact)
			}
		})
		return listemergencycontact
	}

	@Keyword
	public NAP4EmergencyContactDataVerif (Sql instance, String name, String idno){
		String emergencycontact
		ArrayList <String> listemergencycontact = new ArrayList<String>()
		int i
		instance.eachRow(("SELECT Cust_name , MR_ID_TYPE_CODE , ID_NO , ID_EXPIRED_DT , MOBILE_PHN_NO_1 , EMAIL_1 , MR_GENDER_CODE, BIRTH_PLACE , FORMAT(BIRTH_DT, 'yyyy-MM-dd') , MOBILE_PHN_NO_2, ADDR , AREA_CODE_4 , AREA_CODE_3 , ZIPCODE , AREA_CODE_2 , AREA_CODE_1 , CITY,  PHN_AREA_1 , PHN_1 ,  PHN_EXT_1 ,  PHN_AREA_2 ,  PHN_2 ,  PHN_EXT_2 ,  PHN_AREA_3 ,  PHN_3 ,  PHN_EXT_3 from CUST c WITH(NOLOCK) join cust_addr addr WITH(NOLOCK) on c.CUST_ID = addr.CUST_ID JOIN cust_personal cp WITH(NOLOCK) on c.cust_ID = cp.CUST_ID where c.cust_name = '"+ name +"' and ID_NO = '"+ idno +"' AND MR_CUST_ADDR_TYPE_CODE = 'legal'"), {  row ->

			for(i = 0 ; i < 26 ; i++){
				emergencycontact = (row[i])
				listemergencycontact.add(emergencycontact)
			}
		})
		return listemergencycontact
	}

	@Keyword
	public NAP4FinancialDataPersonalStoreData (Sql instance, String appno, String name, String date){
		String financialdata
		ArrayList <String> listfinancialdata = new ArrayList<String>()

		instance.eachRow(("SELECT FORMAT(MONTHLY_INCOME_AMT, 'N0') , FORMAT(OTHER_INCOME_AMT, 'N0') , rml.REF_MASTER_NAME , FORMAT(MONTHLY_INSTALLMENT_AMT, 'N0') , FORMAT(MONTHLY_EXPENSE_AMT, 'N0') , FORMAT(acpfd.DATE_AS_OF , 'MM/dd/yyy')  FROM APP_CUST_PERSONAL_FIN_DATA acpfd WITH(NOLOCK) JOIN APP_CUST_PERSONAL acp WITH(NOLOCK) ON acp.APP_CUST_PERSONAL_ID = acpfd.APP_CUST_PERSONAL_ID JOIN APP_CUST ac WITH(NOLOCK) ON ac.APP_CUST_ID = acp.APP_CUST_ID JOIN APP a WITH(NOLOCK) ON a.APP_ID = ac.APP_ID JOIN REF_MASTER_LOS rml WITH(NOLOCK) ON rml.REF_MASTER_CODE = acpfd.MR_SOURCE_OF_INCOME_TYPE_CODE WHERE a.APP_NO = '"+ appno +"' AND ac.CUST_NAME = '"+ name +"' AND FORMAT(acpfd.DATE_AS_OF , 'MM/dd/yyy') = '"+ date +"'"), {  row ->

			ResultSetMetaData rsmd = row.getMetaData()
			colmcount = rsmd.getColumnCount()


			for(i = 0 ; i < colmcount ; i++){
				financialdata = (row[i])
				listfinancialdata.add(financialdata)
			}
		})
		return listfinancialdata
	}

	@Keyword
	public NAP4FinancialAttrStoreData (Sql instance, String appno, String name){
		String financialattr
		ArrayList <String> listfinancialattr = new ArrayList<String>()
		instance.eachRow(("SELECT ATTR_VALUE  FROM APP_CUST_FIN_DATA_ATTR_CONTENT acpfd WITH(NOLOCK) JOIN APP_CUST ac WITH(NOLOCK) ON ac.APP_CUST_ID = acpfd.APP_CUST_ID JOIN APP a WITH(NOLOCK) ON a.APP_ID = ac.APP_ID WHERE a.APP_NO = '"+ appno +"' AND ac.CUST_NAME = '"+ name +"'"), {  row ->

			ResultSetMetaData rsmd = row.getMetaData()
			colmcount = rsmd.getColumnCount()


			for(i = 0 ; i < colmcount ; i++){
				financialattr = (row[i])
				listfinancialattr.add(financialattr)
			}
		})
		return listfinancialattr
	}

	@Keyword
	public NAP4FinDataBankAccStoreData (Sql instance, String appno, String name, String bankno){
		String bankacc
		ArrayList <String> listbankacc = new ArrayList<String>()
		instance.eachRow(("SELECT acba.BANK_CODE , acba.BANK_BRANCH , acba.BANK_ACC_NAME , acba.BANK_ACC_NO , acba.IS_DEFAULT , acba.IS_ACTIVE , FORMAT(acba.BEG_BALANCE_AMT, 'N0')  FROM APP_CUST_BANK_ACC acba WITH(NOLOCK) JOIN APP_CUST_PERSONAL acp WITH(NOLOCK) ON acba.APP_CUST_ID = acp.APP_CUST_ID JOIN APP_CUST ac WITH(NOLOCK) ON ac.APP_CUST_ID = acba.APP_CUST_ID JOIN APP a WITH(NOLOCK) ON a.APP_ID = ac.APP_ID WHERE a.APP_NO = '"+ appno +"' AND ac.CUST_NAME = '"+ name +"' AND acba.BANK_ACC_NO = '"+ bankno +"' GROUP BY acba.BANK_CODE, acba.BANK_BRANCH, acba.BANK_ACC_NAME, acba.BANK_ACC_NO, acba.IS_DEFAULT, acba.IS_ACTIVE, acba.BEG_BALANCE_AMT"), {  row ->

			ResultSetMetaData rsmd = row.getMetaData()
			colmcount = rsmd.getColumnCount()


			for(i = 0 ; i < colmcount ; i++){
				bankacc = (row[i])
				listbankacc.add(bankacc)
			}


		})
		return listbankacc
	}

	@Keyword
	public NAP4FinancialCheckBankStatStoreData (Sql instance, String appno, String name, String accno){
		String bankstat
		instance.eachRow(("SELECT acba.IS_BANK_STMNT  FROM APP_CUST_BANK_ACC acba WITH(NOLOCK) JOIN APP_CUST_PERSONAL acp WITH(NOLOCK) ON acba.APP_CUST_ID = acp.APP_CUST_ID JOIN APP_CUST ac WITH(NOLOCK) ON ac.APP_CUST_ID = acba.APP_CUST_ID JOIN APP a WITH(NOLOCK) ON a.APP_ID = ac.APP_ID JOIN APP_CUST_BANK_STMNT acbs WITH(NOLOCK) ON acba.APP_CUST_BANK_ACC_ID = acbs.APP_CUST_BANK_ACC_ID WHERE a.APP_NO = '"+ appno +"' AND ac.CUST_NAME = '"+ name +"' AND acba.BANK_ACC_NO = '"+ accno +"' GROUP BY acba.IS_BANK_STMNT"), {  row ->

			bankstat = (row[0])

		})
		return bankstat
	}

	@Keyword
	public NAP4BankStatDataStoreData (Sql instance, String appno, String name, String accno){
		String bankstatdata
		ArrayList <String> listbankstatdata = new ArrayList<String>()
		instance.eachRow(("SELECT acbs.MONTH  ,acbs.YEAR , acbs.DEBIT_TRX_COUNT , FORMAT(acbs.DEBIT_AMT, 'N0') , acbs.CREDIT_TRX_COUNT , FORMAT(acbs.CREDIT_AMT, 'N0')  FROM APP_CUST_BANK_ACC acba WITH(NOLOCK) JOIN APP_CUST_PERSONAL acp WITH(NOLOCK) ON acba.APP_CUST_ID = acp.APP_CUST_ID JOIN APP_CUST ac WITH(NOLOCK) ON ac.APP_CUST_ID = acba.APP_CUST_ID JOIN APP a WITH(NOLOCK) ON a.APP_ID = ac.APP_ID JOIN APP_CUST_BANK_STMNT acbs WITH(NOLOCK) ON acba.APP_CUST_BANK_ACC_ID = acbs.APP_CUST_BANK_ACC_ID WHERE a.APP_NO = '"+ appno +"' AND ac.CUST_NAME = '"+ name +"' AND acba.BANK_ACC_NO = '"+ accno +"' ORDER BY YEAR"), {  row ->

			ResultSetMetaData rsmd = row.getMetaData()
			colmcount = rsmd.getColumnCount()


			for(i = 0 ; i < colmcount ; i++){
				bankstatdata = (row[i])
				listbankstatdata.add(bankstatdata)
			}


		})
		return listbankstatdata
	}

	@Keyword
	public NAP4CustomerAssetDataStoreData (Sql instance, String appno, String name){
		String customerasset
		ArrayList <String> listcustomerasset = new ArrayList<String>()
		instance.eachRow(("SELECT rml.REF_MASTER_NAME , aca.ASSET_DESCR , FORMAT(aca.ASSET_VALUE, 'N0') , aca.ASSET_QTY  FROM APP_CUST_ASSET aca WITH(NOLOCK) JOIN APP_CUST ac WITH(NOLOCK) ON ac.APP_CUST_ID = aca.APP_CUST_ID JOIN  APP a WITH(NOLOCK) ON a.APP_ID = ac.APP_ID JOIN REF_MASTER_LOS rml WITH(NOLOCK) ON rml.REF_MASTER_CODE = aca.MR_CUST_ASSET_TYPE_CODE WHERE a.APP_NO = '"+ appno +"' AND ac.CUST_NAME = '"+ name +"'"), {  row ->

			ResultSetMetaData rsmd = row.getMetaData()
			colmcount = rsmd.getColumnCount()

			for(i = 0 ; i < colmcount ; i++){
				customerasset = (row[i])
				listcustomerasset.add(customerasset)
			}
		})
		return listcustomerasset
	}

	@Keyword
	public NAP4OtherAttrInfoStoreData (Sql instance, String appno, String name){
		String Otherattrinfo
		ArrayList<String> listotherinfo = new ArrayList<>()
		instance.eachRow(("SELECT oth.LBPPMS_DEBT_GRP_LBPP_DESCR , oth.LBPPMS_BIZ_SCL_LBPP_DESCR , oth.LBPPMS_CNTRPRT_LBPP_DESCR , oth.LBPPMS_BIZ_SUSTAIN_LBPP_DESCR  FROM APP_CUST_OTHER_INFO oth WITH(NOLOCK) JOIN APP_CUST ac WITH(NOLOCK) ON ac.APP_CUST_ID = oth.APP_CUST_ID JOIN APP a WITH(NOLOCK) ON a.APP_ID = ac.APP_ID WHERE a.APP_NO = '"+ appno +"' AND ac.CUST_NAME = '"+ name +"'"), {  row ->

			ResultSetMetaData rsmd = row.getMetaData()
			colmcount = rsmd.getColumnCount()

			for(i = 0 ; i < colmcount ; i++){
				Otherattrinfo = (row[i])
				listotherinfo.add(Otherattrinfo)
			}
		})
		return listotherinfo
	}

	@Keyword
	public NAP4OtherAttrStoreData (Sql instance, String appno, String name){
		String OtherAttr
		ArrayList <String> listOtherAttr = new ArrayList<String>()
		instance.eachRow(("SELECT ATTR_VALUE FROM APP_CUST_ATTR_CONTENT acac WITH (NOLOCK) JOIN APP_CUST ac WITH (NOLOCK) ON ac.APP_CUST_ID = acac.APP_CUST_ID JOIN APP a WITH (NOLOCK) ON a.APP_ID = ac.APP_ID WHERE a.APP_NO = '"+ appno +"' AND ac.CUST_NAME = '"+ name +"'"), {  row ->

			ResultSetMetaData rsmd = row.getMetaData()
			colmcount = rsmd.getColumnCount()

			for(i = 0 ; i < colmcount ; i++){
				OtherAttr = (row[i])
				listOtherAttr.add(OtherAttr)
			}

		})
		return listOtherAttr
	}

	@Keyword
	public NAP4CustomerDetailCompanyStoreData (Sql instance, String appno, String name){
		String customerdetail
		ArrayList<String> listcust = new ArrayList<>()
		instance.eachRow(("SELECT FORMAT(acc.ESTABLISHMENT_DT, 'MM/dd/yyyy') , acc.NUM_OF_EMP, ac.IS_VIP , ac.IS_AFFILIATE_WITH_MF , acc.INDUSTRY_TYPE_CODE , ac.MR_CUST_MODEL_CODE, ac.VIP_NOTES  FROM APP_CUST ac WITH(NOLOCK) JOIN APP a WITH(NOLOCK) ON a.APP_ID = ac.APP_ID JOIN APP_CUST_COMPANY acc WITH(NOLOCK) ON acc.APP_CUST_ID = ac.APP_CUST_ID WHERE a.APP_NO = '"+ appno +"' AND ac.CUST_NAME = '"+ name +"'"), {  row ->

			ResultSetMetaData rsmd = row.getMetaData()
			colmcount = rsmd.getColumnCount()


			for(i = 0 ; i < colmcount ; i++){
				customerdetail = (row[i])
				listcust.add(customerdetail)
			}
		})
		return listcust
	}

	@Keyword
	public NAP4ContactInformationStoreData (Sql instance, String appno, String name){
		String contactinfo
		ArrayList<String> listcontact = new ArrayList<>()
		instance.eachRow(("SELECT [ContactPersonName] , [JobTitle] , [ID_TYPE] , [ContactIDNo] , [ExpiredDate] , [MobilePhn1] , [Email1] , [GENDER] , [JOB_POSITION] , [BirthPlace] , [BirthDate] , [CUST_COMPANY_RELATIONSHIP] , [Mobilephn2] , [Email2] , [Addr] , [RT] , [RW] , [Zipcode] , [Kecamatan] , [Kelurahan] , [Kota] , [Phone1Area] , [Phone1] , [Phone1Ext] , [Phone2Area] , [Phone2] , [Phone2Ext] , [FaxArea] , [Fax]  FROM (SELECT [ContactPersonName], [JobTitle], [ContactIDNo], [ExpiredDate], [MobilePhn1], [Email1], [BirthPlace], [BirthDate], [Mobilephn2], [Email2], [Addr], [RT], [RW], [Zipcode], [Kecamatan], [Kelurahan], [Kota], [Phone1Area], [Phone1], [Phone1Ext], [Phone2Area], [Phone2], [Phone2Ext], [FaxArea], [Fax], mastername.Code, REF_MASTER_NAME FROM (select [ContactPersonName], [JobTitle], [ContactIDNo], [ExpiredDate], [MobilePhn1], [Email1], [BirthPlace], [BirthDate], [Mobilephn2], [Email2], [Addr], [RT], [RW], [Zipcode], [Kecamatan], [Kelurahan], [Kota], [Phone1Area], [Phone1], [Phone1Ext], [Phone2Area], [Phone2], [Phone2Ext], [FaxArea], [Fax], [Code], value FROM (SELECT acccp.CONTACT_PERSON_NAME as [ContactPersonName], acccp.JOB_TITLE_NAME as [JobTitle], acccp.MR_ID_TYPE_CODE as 'ID_TYPE', acccp.ID_NO as [ContactIDNo], FORMAT(acccp.ID_EXPIRED_DT, 'MM/dd/yyyy') as [ExpiredDate], acccp.MOBILE_PHN_NO_1 as [MobilePhn1], acccp.EMAIL_1 as [Email1], acccp.MR_JOB_POSITION_CODE as 'JOB_POSITION', acccp.BIRTH_PLACE as [BirthPlace], FORMAT(acccp.BIRTH_DT, 'MM/dd/yyyy') as [BirthDate], acccp.MR_CUST_RELATIONSHIP_CODE as 'CUST_COMPANY_RELATIONSHIP', acccp.MOBILE_PHN_NO_2 as [Mobilephn2], acccp.EMAIL_2 as [Email2], aca.ADDR as [Addr], aca.AREA_CODE_4 as [RT], aca.AREA_CODE_3 as [RW], aca.ZIPCODE as [Zipcode], aca.AREA_CODE_1 as [Kecamatan], aca.AREA_CODE_2 as [Kelurahan], aca.CITY as [Kota], aca.PHN_AREA_1 as [Phone1Area], aca.PHN_1 as [Phone1], aca.PHN_EXT_1 as [Phone1Ext], aca.PHN_AREA_2 as [Phone2Area], aca.PHN_2 as [Phone2], aca.PHN_EXT_2 as [Phone2Ext], aca.FAX_AREA as [FaxArea], aca.FAX as [Fax], MR_GENDER_CODE as 'GENDER' FROM APP_CUST_COMPANY_CONTACT_PERSON acccp WITH(NOLOCK) JOIN APP_CUST_COMPANY acc WITH(NOLOCK) ON acc.APP_CUST_COMPANY_ID = acccp.APP_CUST_COMPANY_ID JOIN APP_CUST ac WITH(NOLOCK) ON ac.APP_CUST_ID = acc.APP_CUST_ID JOIN APP a WITH(NOLOCK) ON a.APP_ID = ac.APP_ID JOIN APP_CUST_ADDR aca ON aca.APP_CUST_ID = ac.APP_CUST_ID WHERE a.APP_NO = '"+ appno +"' AND ac.CUST_NAME = '"+ name +"' AND aca.MR_CUST_ADDR_TYPE_CODE = 'COMPANY') as orig unpivot (value for [Code] in ([ID_TYPE],[CUST_COMPANY_RELATIONSHIP],[JOB_POSITION],[GENDER]) )as unpiv) as mastername JOIN REF_MASTER_LOS rf on rf.REF_MASTER_Code = mastername.value WHERE rf.IS_ACTIVE = '1') AS ref PIVOT (MAX(ref.REF_MASTER_NAME) for [Code] in ([ID_TYPE],[CUST_COMPANY_RELATIONSHIP],[JOB_POSITION],[GENDER])) as piv"), {  row ->

			ResultSetMetaData rsmd = row.getMetaData()
			colmcount = rsmd.getColumnCount()


			for(i = 0 ; i < colmcount ; i++){
				contactinfo = (row[i])
				listcontact.add(contactinfo)
			}
		})
		return listcontact
	}

	@Keyword
	public NAP4FinancialDataCompanyStoreData (Sql instance, String appno, String name, String date){
		String financialdata
		ArrayList<String> listfindata = new ArrayList<>()
		instance.eachRow(("SELECT FORMAT(GROSS_MONTHLY_INCOME_AMT, 'N0') , FORMAT(GROSS_MONTHLY_EXPENSE_AMT, 'N0'), FORMAT(RETURN_OF_INVESTMENT_PRCNT, 'N2') , FORMAT(RETURN_OF_EQUITY_PRCNT, 'N2') , FORMAT(RETURN_OF_ASSET_PRCNT, 'N2') , FORMAT(PROFIT_MARGIN_PRCNT, 'N2') , FORMAT(CURRENT_RATIO_PRCNT, 'N2') , FORMAT(DEBT_EQUITY_RATIO_PRCNT, 'N2') , FORMAT(INV_TURN_OVER_PRCNT, 'N2') , FORMAT(AR_TURN_OVER_PRCNT, 'N2') , FORMAT(GROWTH_PRCNT, 'N2') , FORMAT(WORKING_CAPITAL_AMT, 'N0') , FORMAT(OTH_MONTHLY_INST_AMT, 'N0') , FORMAT(REVENUE, 'N0') , FORMAT(OPR_COST, 'N0') , FORMAT(PROFIT_BEFORE_TAX, 'N0') , FORMAT(CURR_ASSET, 'N0') , FORMAT(NET_FIXED_ASSET, 'N0') , FORMAT(TOTAL_ASSET, 'N0') , FORMAT(CURR_LIABLTS, 'N0') , FORMAT(LONG_TERM_LIABLTS, 'N0') , FORMAT(SHAREHOLDER_EQUITY, 'N0') , FORMAT(CURR_RATIO, 'N0') , FORMAT(DATE_AS_OF, 'MM/dd/yyyy') FROM APP_CUST_COMPANY_FIN_DATA accfd WITH(NOLOCK) JOIN APP_CUST ac WITH(NOLOCK) ON ac.APP_CUST_ID = accfd.APP_CUST_ID JOIN APP a WITH(NOLOCK) ON a.APP_ID = ac.APP_ID WHERE a.APP_NO = '"+ appno +"' AND ac.CUST_NAME = '"+ name +"' AND FORMAT(DATE_AS_OF , 'MM/dd/yyy') = '"+ date +"'"), {  row ->

			ResultSetMetaData rsmd = row.getMetaData()
			colmcount = rsmd.getColumnCount()


			for(i = 0 ; i < colmcount ; i++){
				financialdata = (row[i])
				listfindata.add(financialdata)
			}
		})
		return listfindata
	}

	@Keyword
	public NAP4FinancialAttrCompanyStoreData (Sql instance, String appno, String name){
		String financialattr
		ArrayList <String> listfinancialattr = new ArrayList<String>()
		instance.eachRow(("SELECT ATTR_VALUE  FROM APP_CUST_FIN_DATA_ATTR_CONTENT acfdac WITH(NOLOCK) JOIN APP_CUST ac WITH(NOLOCK) ON ac.APP_CUST_ID = acfdac.APP_CUST_ID JOIN APP a WITH(NOLOCK) ON a.APP_ID = ac.APP_ID WHERE a.APP_NO = '"+ appno +"' AND ac.CUST_NAME = '"+ name +"'"), {  row ->

			ResultSetMetaData rsmd = row.getMetaData()
			colmcount = rsmd.getColumnCount()


			for(i = 0 ; i < colmcount ; i++){
				financialattr = (row[i])
				listfinancialattr.add(financialattr)
			}
		})
		return listfinancialattr
	}

	@Keyword
	public NAP4FinDataBankAccCompanyStoreData (Sql instance, String appno, String name, String bankno){
		String bankacc
		ArrayList <String> listbankacc = new ArrayList<String>()
		instance.eachRow(("SELECT acba.BANK_CODE , acba.BANK_BRANCH , acba.BANK_ACC_NAME , acba.BANK_ACC_NO , acba.IS_DEFAULT , acba.IS_ACTIVE , FORMAT(acba.BEG_BALANCE_AMT, 'N0')  FROM APP_CUST_BANK_ACC acba WITH(NOLOCK) JOIN APP_CUST_COMPANY acp WITH(NOLOCK) ON acba.APP_CUST_ID = acp.APP_CUST_ID JOIN APP_CUST ac WITH(NOLOCK) ON ac.APP_CUST_ID = acba.APP_CUST_ID JOIN APP a WITH(NOLOCK) ON a.APP_ID = ac.APP_ID WHERE a.APP_NO = '"+ appno +"' AND ac.CUST_NAME = '"+ name +"' AND acba.BANK_ACC_NO = '"+ bankno +"' GROUP BY acba.BANK_CODE, acba.BANK_BRANCH, acba.BANK_ACC_NAME, acba.BANK_ACC_NO, acba.IS_DEFAULT, acba.IS_ACTIVE, acba.BEG_BALANCE_AMT"), {  row ->

			ResultSetMetaData rsmd = row.getMetaData()
			colmcount = rsmd.getColumnCount()


			for(i = 0 ; i < colmcount ; i++){
				bankacc = (row[i])
				listbankacc.add(bankacc)
			}


		})
		return listbankacc
	}

	@Keyword
	public NAP4FinancialCheckBankStatCompanyStoreData (Sql instance, String appno, String name, String accno){
		String bankstat
		instance.eachRow(("SELECT acba.IS_BANK_STMNT  FROM APP_CUST_BANK_ACC acba WITH(NOLOCK) JOIN APP_CUST_COMPANY acp WITH(NOLOCK) ON acba.APP_CUST_ID = acp.APP_CUST_ID JOIN APP_CUST ac WITH(NOLOCK) ON ac.APP_CUST_ID = acba.APP_CUST_ID JOIN APP a WITH(NOLOCK) ON a.APP_ID = ac.APP_ID JOIN APP_CUST_BANK_STMNT bs WITH(NOLOCK) ON acba.APP_CUST_BANK_ACC_ID = bs.APP_CUST_BANK_ACC_ID WHERE a.APP_NO = '"+ appno +"' AND ac.CUST_NAME = '"+ name +"' AND acba.BANK_ACC_NO = '"+ accno +"' GROUP BY acba.IS_BANK_STMNT"), {  row ->

			bankstat = (row[0])

		})
		return bankstat
	}

	@Keyword
	public NAP4BankStatDataCompanyStoreData (Sql instance, String appno, String name, String accno){
		String bankstatdata
		ArrayList <String> listbankstatdata = new ArrayList<String>()
		instance.eachRow(("SELECT acbs.MONTH  ,acbs.YEAR , acbs.DEBIT_TRX_COUNT , FORMAT(acbs.DEBIT_AMT, 'N0') , acbs.CREDIT_TRX_COUNT , FORMAT(acbs.CREDIT_AMT, 'N0')  FROM APP_CUST_BANK_ACC acba WITH(NOLOCK) JOIN APP_CUST_COMPANY acp WITH(NOLOCK) ON acba.APP_CUST_ID = acp.APP_CUST_ID JOIN APP_CUST ac ON ac.APP_CUST_ID = acba.APP_CUST_ID JOIN APP a WITH(NOLOCK) ON a.APP_ID = ac.APP_ID JOIN APP_CUST_BANK_STMNT acbs WITH(NOLOCK) ON acba.APP_CUST_BANK_ACC_ID = acbs.APP_CUST_BANK_ACC_ID WHERE a.APP_NO = '"+ appno +"' AND ac.CUST_NAME = '"+ name +"' AND acba.BANK_ACC_NO = '"+ accno +"'"), {  row ->

			ResultSetMetaData rsmd = row.getMetaData()
			colmcount = rsmd.getColumnCount()


			for(i = 0 ; i < colmcount ; i++){
				bankstatdata = (row[i])
				listbankstatdata.add(bankstatdata)
			}


		})
		return listbankstatdata
	}

	@Keyword
	public NAP4LegalDocStoreData (Sql instance, String appno, String name){
		String legaldoc
		ArrayList <String> listlegaldocdata = new ArrayList<String>()
		instance.eachRow(("SELECT MR_LEGAL_DOC_TYPE_CODE , DOC_NO , FORMAT(accld.DOC_DT, 'MM/dd/yyyy') , FORMAT(DOC_EXPIRED_DT, 'MM/dd/yyyy') , NOTARY_NAME , NOTARY_LOCATION , DOC_NOTES  FROM APP_CUST_COMPANY_LEGAL_DOC accld WITH(NOLOCK) JOIN APP_CUST_COMPANY acc WITH(NOLOCK) ON acc.APP_CUST_COMPANY_ID = accld.APP_CUST_COMPANY_ID JOIN APP_CUST ac WITH(NOLOCK) ON ac.APP_CUST_ID = acc.APP_CUST_ID JOIN APP a WITH(NOLOCK) ON a.APP_ID = ac.APP_ID WHERE a.APP_NO = '"+ appno +"' AND ac.CUST_NAME = '"+ name +"'"), {  row ->

			ResultSetMetaData rsmd = row.getMetaData()
			colmcount = rsmd.getColumnCount()


			for(i = 0 ; i < colmcount ; i++){
				legaldoc = (row[i])
				listlegaldocdata.add(legaldoc)
			}
		})
		return listlegaldocdata
	}


	@Keyword
	public checkTotalInsurance(Sql instance, String appno){
		String insurancedata
		ArrayList <String> listCVG = new ArrayList<String>()
		instance.eachRow(("SELECT a.TOTAL_CUST_MAIN_PREMI_AMT , a.TOTAL_CUST_ADD_PREMI_AMT , TOTAL_CUST_FEE_AMT, a.TOTAL_CUST_DISC_AMT , TOTAL_CUST_PREMI_AMT FROM APP_INS a JOIN APP b ON a.APP_ID = b.APP_ID  WHERE APP_NO = '"+appno+"'"), {  row ->

			ResultSetMetaData rsmd = row.getMetaData()
			colmcount = rsmd.getColumnCount()


			for(i = 0 ; i < colmcount ; i++){
				insurancedata = (row[i])
				listCVG.add(insurancedata)
			}
		})
		return listCVG
	}
}
