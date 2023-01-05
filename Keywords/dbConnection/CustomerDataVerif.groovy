package dbConnection

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

	//get customer data personal
	@Keyword
	public CustomerDataPersonal (Sql instance, String name, String idtype, String custmodel){
		String customerdata
		ArrayList<String> listcustomerdata = new ArrayList<>()
		instance.eachRow(("SELECT ISNULL(Cust_name,'') as [CUST_NAME] , ISNULL(BIRTH_PLACE,'') AS [BIRTH_PLACE], ISNULL(FORMAT(ID_EXPIRED_DT, 'yyyy-MM-dd'),'') AS [ID_EXPIRED_DATE] , ISNULL(MOBILE_PHN_NO_1,'') AS [MOBILE_PHONE1], ISNULL(FORMAT(BIRTH_DT, 'yyyy-MM-dd'),'') AS [BIRTH_DATE], ISNULL(ID_NO,'') AS [ID_NO], ISNULL(TAX_ID_NO,'') AS TAX_ID_NO , ISNULL(MOTHER_MAIDEN_NAME,'') AS MOTHER_MAIDEN_NAME, ISNULL(EMAIL_1,'') AS EMAIL_1, ISNULL(ADDR,'') AS ADDR , ISNULL(AREA_CODE_4,'') AS AREA_CODE4, ISNULL(AREA_CODE_3,'') AS AREA_CODE3, ISNULL(ZIPCODE,'') AS ZIPCODE, ISNULL(AREA_CODE_2,'') AS AREA_CODE2, ISNULL(AREA_CODE_1,'') AS AREA_CODE1, ISNULL(CITY,'') AS CITY, ISNULL(MR_ID_TYPE_CODE,'') AS ID_TYPE_CODE, ISNULL(MR_MARITAL_STAT_CODE,'') AS MARITAL_STATUS, ISNULL(MR_CUST_MODEL_CODE,'') AS CUST_MODEL, ISNULL(MR_GENDER_CODE,'') AS GENDER, ISNULL(MR_BUILDING_OWNERSHIP_CODE,'') AS OWNERSHIP from CUST c WITH(NOLOCK) join cust_addr addr WITH(NOLOCK) on c.CUST_ID = addr.CUST_ID JOIN cust_personal cp WITH(NOLOCK) on c.cust_ID = cp.CUST_ID where c.cust_name = '"+ name +"' and MR_ID_TYPE_CODE = '"+ idtype +"' AND MR_CUST_MODEL_CODE = '"+ custmodel +"' AND MR_CUST_ADDR_TYPE_CODE = 'legal' GROUP BY Cust_name, BIRTH_PLACE, MR_ID_TYPE_CODE, ID_EXPIRED_DT, MR_MARITAL_STAT_CODE, MOBILE_PHN_NO_1, MR_CUST_MODEL_CODE, MR_GENDER_CODE, FORMAT(BIRTH_DT, 'yyyy-MM-dd'), ID_NO, TAX_ID_NO, MOTHER_MAIDEN_NAME, EMAIL_1, ADDR, AREA_CODE_4, AREA_CODE_3, ZIPCODE, AREA_CODE_2, AREA_CODE_1, CITY, MR_BUILDING_OWNERSHIP_CODE"), {  row ->

			ResultSetMetaData rsmd = row.getMetaData()
			colmcount = rsmd.getColumnCount()


			for(i = 0 ; i < colmcount ; i++){
				customerdata = (row[i])
				listcustomerdata.add(customerdata)
			}
		})
		return listcustomerdata
	}

	//get customer data company
	@Keyword
	public CustomerDataCompany (Sql instance, String name, String idno){
		String customerdata
		ArrayList<String> listcustomerdata = new ArrayList<>()
		instance.eachRow(("SELECT ISNULL(Cust_name,'') AS CUST_NAME, ISNULL(TAX_ID_NO,'') AS TAX_ID_NO, ISNULL(ADDR,'') AS ADDR, ISNULL(AREA_CODE_4,'') AS AREA_CODE4, ISNULL(AREA_CODE_3,'') AS AREA_CODE3, ISNULL(ZIPCODE,'') AS ZIPCODE, ISNULL(AREA_CODE_2,'') AS AREA_CODE2, ISNULL(AREA_CODE_1,'') AS AREA_CODE1, ISNULL(CITY,'') AS CITY, ISNULL(MR_COMPANY_TYPE_CODE,'') AS COMPANY_TYPE_CODE, ISNULL(MR_CUST_MODEL_CODE,'') AS CUST_MODEL, ISNULL(MR_BUILDING_OWNERSHIP_CODE,'') AS OWNERSHIP from CUST c WITH(NOLOCK) join cust_addr addr WITH(NOLOCK) on c.CUST_ID = addr.CUST_ID JOIN CUST_COMPANY cc WITH(NOLOCK) on c.cust_ID = cc.CUST_ID where c.cust_name = '"+ name +"' AND ID_NO = '"+ idno +"' AND MR_CUST_ADDR_TYPE_CODE = 'legal' GROUP BY Cust_name, TAX_ID_NO, MR_COMPANY_TYPE_CODE ,MR_CUST_MODEL_CODE, ADDR, AREA_CODE_4, AREA_CODE_3, ZIPCODE, AREA_CODE_2, AREA_CODE_1, CITY, MR_BUILDING_OWNERSHIP_CODE"), {  row ->

			ResultSetMetaData rsmd = row.getMetaData()
			colmcount = rsmd.getColumnCount()


			for(i = 0 ; i < colmcount ; i++){
				customerdata = (row[i])
				listcustomerdata.add(customerdata)
			}
		})
		return listcustomerdata
	}

	//get family data personal
	@Keyword
	public FamilyDataPersonal (Sql instance, String name, String idno){
		String customerdata
		ArrayList<String> listcustomerdata = new ArrayList<>()
		instance.eachRow(("SELECT ISNULL(Cust_name,'') AS CUST_NAME, ISNULL(BIRTH_PLACE,'') AS BIRTH_PLACE, ISNULL(MR_ID_TYPE_CODE,'') AS ID_TYPE_CODE, ISNULL(FORMAT(ID_EXPIRED_DT, 'yyyy-MM-dd'),'') AS ID_EXPIRED_DATE, ISNULL(MR_MARITAL_STAT_CODE,'') AS MARITAL_STATUS, ISNULL(MOBILE_PHN_NO_1,'') AS MOBILE1, ISNULL(MR_CUST_MODEL_CODE,'') AS CUST_MODEL, ISNULL(MR_GENDER_CODE,'') AS GENDER, ISNULL(FORMAT(BIRTH_DT, 'yyyy-MM-dd'),'') AS BIRTH_DATE, ISNULL(ID_NO,'') AS ID_NO, ISNULL(TAX_ID_NO,'') AS TAX_ID_NO, ISNULL(MOTHER_MAIDEN_NAME,'') AS MOTHER_MAIDEN_NAME, ISNULL(EMAIL_1,'') AS EMAIL1, ISNULL(MR_NATIONALITY_CODE,'') AS NATIONALITY, ISNULL(UPPER(COUNTRY_NAME),'') AS COUNTRY, ISNULL(ADDR,'') AS ADDR, ISNULL(AREA_CODE_4,'') AS AREA4, ISNULL(AREA_CODE_3,'') AS AREA3, ISNULL(ZIPCODE,'') AS ZIPCODE, ISNULL(AREA_CODE_2,'') AS AREA2, ISNULL(AREA_CODE_1,'') AS AREA1, ISNULL(CITY,'') AS CITY, ISNULL(MR_BUILDING_OWNERSHIP_CODE,'') AS OWNERSHIP from CUST c WITH(NOLOCK) join cust_addr addr WITH(NOLOCK) on c.CUST_ID = addr.CUST_ID join cust_personal cp WITH(NOLOCK) on c.cust_ID = cp.CUST_ID join REF_COUNTRY country WITH(NOLOCK) on cp.WNA_COUNTRY_CODE = country.COUNTRY_CODE where c.cust_name = '"+ name + "' AND ID_NO = '"+ idno +"' AND MR_CUST_ADDR_TYPE_CODE = 'legal' GROUP BY Cust_name , BIRTH_PLACE , MR_ID_TYPE_CODE , ID_EXPIRED_DT , MR_MARITAL_STAT_CODE , MOBILE_PHN_NO_1 , MR_CUST_MODEL_CODE , MR_GENDER_CODE , FORMAT(BIRTH_DT, 'yyyy-MM-dd') , ID_NO , TAX_ID_NO , MOTHER_MAIDEN_NAME , EMAIL_1 , MR_NATIONALITY_CODE , UPPER(COUNTRY_NAME) , ADDR , AREA_CODE_4 , AREA_CODE_3 , ZIPCODE , AREA_CODE_2 , AREA_CODE_1 , CITY , MR_BUILDING_OWNERSHIP_CODE"), {  row ->

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

	//get customer data personal store db
	@Keyword
	public CustomerDataStoreDBPersonal (Sql instance, String appno){
		String customerdata
		ArrayList<String> listcustomerdata = new ArrayList<>()
		//instance.eachRow(("SELECT b.PROD_OFFERING_CODE , Cust_name , BIRTH_PLACE , h.REF_MASTER_NAME , FORMAT(ID_EXPIRED_DT, 'MM/dd/yyyy') , MR_MARITAL_STAT_CODE , MOBILE_PHN_NO_1 , f.REF_MASTER_NAME , MR_GENDER_CODE , FORMAT(BIRTH_DT, 'MM/dd/yyyy') , ID_NO , TAX_ID_NO , MOTHER_MAIDEN_NAME , EMAIL_1 , e.ATTR_VALUE , SUBQ.ATTR_VALUE , ADDR , AREA_CODE_4 , AREA_CODE_3 , ZIPCODE , AREA_CODE_2 , AREA_CODE_1 , CITY , g.REF_MASTER_NAME  FROM APP_CUST a JOIN app b ON a.APP_ID = b.APP_ID JOIN APP_CUST_PERSONAL c ON a.APP_CUST_ID = c.APP_CUST_ID JOIN APP_CUST_ADDR d ON a.APP_CUST_ID = d.APP_CUST_ID JOIN APP_CUST_ATTR_CONTENT e ON e.APP_CUST_ID = a.APP_CUST_ID JOIN REF_MASTER_LOS f ON a.MR_CUST_MODEL_CODE = f.REF_MASTER_CODE JOIN REF_MASTER_LOS g ON d.MR_HOUSE_OWNERSHIP_CODE = g.REF_MASTER_CODE JOIN REF_MASTER_LOS h ON a.MR_ID_TYPE_CODE = h.REF_MASTER_CODE, (SELECT ATTR_VALUE, a.APP_ID FROM APP_CUST_ATTR_CONTENT g JOIN APP_CUST a ON g.APP_CUST_ID = a.APP_CUST_ID JOIN app b ON a.APP_ID = b.APP_ID WHERE APP_NO = '"+ appno +"'AND IS_CUSTOMER = 1 AND g.REF_ATTR_CODE = 'AUTH_AML') as SUBQ WHERE a.APP_ID = SUBQ.APP_ID AND IS_CUSTOMER = 1 AND MR_CUST_ADDR_TYPE_CODE = 'LEGAL' AND MR_CUST_TYPE_CODE = 'PERSONAL' AND e.REF_ATTR_CODE = 'AML_CUST_DEPARTMENT' AND f.REF_MASTER_TYPE_CODE = 'CUST_MODEL' AND g.REF_MASTER_TYPE_CODE = 'BUILDING_OWNERSHIP' AND h.REF_MASTER_TYPE_CODE = 'ID_TYPE'"), {  row ->
		instance.eachRow(("SELECT ISNULL([ProductOffering],'') AS PROD_OFFERING, ISNULL([CustName],'') AS CUST_NAME, ISNULL([BirthPlace],'') AS BIRTH_PLACE, ISNULL([ID_TYPE],'') AS ID_TYPE,ISNULL([IDExpiredDate],'') AS ID_EXPIRED_DATE, ISNULL([MaritalStatus],'') AS MARITALSTAT, ISNULL([MobilePHN1],'') AS MOBILE1, ISNULL([CUST_MODEL],'') AS CUST_MODEL, ISNULL([Gender],'') AS GENDER, ISNULL([BirthDate],'') AS BIRTH_DATE, ISNULL([IdNo],'') AS ID_NO, ISNULL([TaxID],'') AS TAX_ID, ISNULL([MotherMaidenName],'') AS MOTHER_MAIDEN_NAME,ISNULL([Email1],'') AS EMAIL1, ISNULL([Addr],'') AS ADDR, ISNULL([RT],'') AS RT, ISNULL([RW],'') AS RW, ISNULL([Zipcode],'') AS ZIPCODE, ISNULL([Kecamatan],'') AS KECAMATAN, ISNULL([Kelurahan],'') AS KELURAHAN, ISNULL([Kota],'') AS KOTA, ISNULL([BUILDING_OWNERSHIP],'') AS OWNERSHIP FROM (SELECT [ProductOffering], [CustName], [BirthPlace], [IDExpiredDate], [MaritalStatus], [MobilePHN1], [Gender], [BirthDate], [IdNo], [TaxID], [MotherMaidenName], [Email1], [Addr], [RT], [RW], [Zipcode], [Kecamatan], [Kelurahan], [Kota], mastername.Code, REF_MASTER_NAME FROM (select [ProductOffering], [CustName], [BirthPlace], [IDExpiredDate], [MaritalStatus], [MobilePHN1], [Gender], [BirthDate], [IdNo], [TaxID], [MotherMaidenName], [Email1], [Addr], [RT], [RW], [Zipcode], [Kecamatan], [Kelurahan], [Kota], [Code], value FROM (SELECT a.PROD_OFFERING_CODE AS [ProductOffering], Cust_name AS [CustName], BIRTH_PLACE AS [BirthPlace], ac.MR_ID_TYPE_CODE AS 'ID_TYPE', FORMAT(ID_EXPIRED_DT, 'MM/dd/yyyy') AS [IDExpiredDate], MR_MARITAL_STAT_CODE AS [MaritalStatus], MOBILE_PHN_NO_1 AS [MobilePHN1], ac.MR_CUST_MODEL_CODE AS 'CUST_MODEL', MR_GENDER_CODE AS [Gender], FORMAT(BIRTH_DT, 'MM/dd/yyyy') AS [BirthDate], ID_NO AS [IdNo], TAX_ID_NO AS [TaxID], MOTHER_MAIDEN_NAME AS [MotherMaidenName], EMAIL_1 AS [Email1],  ADDR AS [Addr], AREA_CODE_4 AS [RT], AREA_CODE_3 AS [RW], ZIPCODE AS [Zipcode], AREA_CODE_2 AS [Kecamatan], AREA_CODE_1 AS [Kelurahan], CITY AS [Kota], addr.MR_HOUSE_OWNERSHIP_CODE AS 'BUILDING_OWNERSHIP' FROM APP_CUST ac JOIN APP a WITH(NOLOCK) ON ac.APP_ID = a.APP_ID JOIN APP_CUST_PERSONAL acp WITH(NOLOCK) ON ac.APP_CUST_ID = acp.APP_CUST_ID JOIN APP_CUST_ADDR addr WITH(NOLOCK) ON ac.APP_CUST_ID = addr.APP_CUST_ID WHERE ac.IS_CUSTOMER = 1 AND APP_NO = '"+ appno +"' AND MR_CUST_ADDR_TYPE_CODE = 'LEGAL' AND MR_CUST_TYPE_CODE = 'PERSONAL') as Orig unpivot (value for [Code] in ([ID_TYPE],[CUST_MODEL],[BUILDING_OWNERSHIP]))as unpiv) as mastername JOIN REF_MASTER_LOS rf WITH(NOLOCK) ON rf.REF_MASTER_Code = mastername.value WHERE rf.IS_ACTIVE = '1') AS ref PIVOT (MAX(ref.REF_MASTER_NAME) for [Code] in ([ID_TYPE],[CUST_MODEL],[BUILDING_OWNERSHIP])) as piv"), {  row ->

			ResultSetMetaData rsmd = row.getMetaData()
			colmcount = rsmd.getColumnCount()


			for(i = 0 ; i < colmcount ; i++){
				customerdata = (row[i])
				listcustomerdata.add(customerdata)
			}
		})
		return listcustomerdata
	}

	//get customer data personal store db lookup
	@Keyword
	public CustomerDataStoreDBPersonalLookUp (Sql instance, String appno, String name){
		String customerdata
		ArrayList<String> listcustomerdata = new ArrayList<>()
		instance.eachRow(("SELECT ISNULL(Cust_name,'') as CUST_NAME , ISNULL(BIRTH_PLACE,'') AS BIRTH_PLACE , ISNULL(FORMAT(ID_EXPIRED_DT, 'yyyy-MM-dd'),'') AS ID_EXP_DATE , ISNULL(MOBILE_PHN_NO_1,'') AS MOBILE1, ISNULL(FORMAT(BIRTH_DT, 'yyyy-MM-dd'),'') AS BIRTH_DATE, ISNULL(ID_NO,'') AS ID_NO, ISNULL(TAX_ID_NO,'') AS TAX_ID_NO , ISNULL(MOTHER_MAIDEN_NAME,'') AS MOTHER_MAIDEN_NAME, ISNULL(EMAIL_1,'') AS EMAIL1, ISNULL(ADDR,'') AS ADDR, ISNULL(AREA_CODE_4,'') AS AREA4, ISNULL(AREA_CODE_3,'') AS AREA3, ISNULL(ZIPCODE,'') AS ZIPCODE, ISNULL(AREA_CODE_2,'') AS AREA2, ISNULL(AREA_CODE_1,'') AS AREA1, ISNULL(CITY,'') AS CITY, ISNULL(ac.MR_ID_TYPE_CODE,'') AS ID_TYPE_CODE,ISNULL(MR_MARITAL_STAT_CODE,'') AS MARITALSTATUS, ISNULL(MR_CUST_MODEL_CODE,'') AS CUST_MODEL, ISNULL(MR_GENDER_CODE,'') AS GENDER,ISNULL(aca.MR_HOUSE_OWNERSHIP_CODE,'') AS OWNERSHIP FROM APP_CUST ac WITH(NOLOCK) JOIN app a WITH(NOLOCK) ON ac.APP_ID = a.APP_ID JOIN APP_CUST_PERSONAL acp WITH(NOLOCK) ON ac.APP_CUST_ID = acp.APP_CUST_ID JOIN APP_CUST_ADDR aca WITH(NOLOCK) ON ac.APP_CUST_ID = aca.APP_CUST_ID where APP_NO = '"+appno+"' AND IS_CUSTOMER = 1 AND Cust_name = '"+name+"' AND aca.MR_CUST_ADDR_TYPE_CODE = 'LEGAL'"), {  row ->
			ResultSetMetaData rsmd = row.getMetaData()
			colmcount = rsmd.getColumnCount()


			for(i = 0 ; i < colmcount ; i++){
				customerdata = (row[i])
				listcustomerdata.add(customerdata)
			}
		})
		return listcustomerdata
	}

	//get family data personal store db
	@Keyword
	public FamilyDataStoreDBPersonal (Sql instance, String appno, String name){
		String familydata
		ArrayList<String> listcustomerdata = new ArrayList<>()
		//		instance.eachRow(("SELECT MR_CUST_RELATIONSHIP_CODE  ,Cust_name , BIRTH_PLACE , g.REF_MASTER_NAME , FORMAT(ID_EXPIRED_DT, 'MM/dd/yyyy') , MR_MARITAL_STAT_CODE , MOBILE_PHN_NO_1 , h.REF_MASTER_NAME , e.MR_PROFESSION_CODE , FORMAT(e.ESTABLISHMENT_DT, 'MM/dd/yyyy') , MR_GENDER_CODE , FORMAT(BIRTH_DT, 'MM/dd/yyyy') , ID_NO , TAX_ID_NO , MOTHER_MAIDEN_NAME , EMAIL_1 , MR_NATIONALITY_CODE , NATIONALITY_COUNTRY_CODE , MR_JOB_POSITION_CODE , f.ATTR_VALUE , SUBQ.ATTR_VALUE , ADDR , AREA_CODE_4 , AREA_CODE_3 , ZIPCODE , AREA_CODE_2 , AREA_CODE_1 , CITY , i.REF_MASTER_NAME  FROM APP_CUST a JOIN app b ON a.APP_ID = b.APP_ID JOIN APP_CUST_PERSONAL c ON a.APP_CUST_ID = c.APP_CUST_ID JOIN APP_CUST_ADDR d ON a.APP_CUST_ID = d.APP_CUST_ID JOIN APP_CUST_PERSONAL_JOB_DATA e ON c.APP_CUST_PERSONAL_ID = e.APP_CUST_PERSONAL_ID JOIN APP_CUST_ATTR_CONTENT f ON f.APP_CUST_ID = a.APP_CUST_ID JOIN REF_MASTER_LOS g ON a.MR_ID_TYPE_CODE = g.REF_MASTER_CODE JOIN REF_MASTER_LOS h ON a.MR_CUST_MODEL_CODE = h.REF_MASTER_CODE JOIN REF_MASTER_LOS i ON d.MR_HOUSE_OWNERSHIP_CODE = i.REF_MASTER_CODE, (SELECT ATTR_VALUE, a.APP_ID FROM APP_CUST_ATTR_CONTENT g JOIN APP_CUST a ON g.APP_CUST_ID = a.APP_CUST_ID JOIN app b ON a.APP_ID = b.APP_ID WHERE APP_NO = '"+ appno +"' AND IS_FAMILY = 1 AND g.REF_ATTR_CODE = 'AUTH_AML') as SUBQ where a.APP_ID = SUBQ.APP_ID AND IS_FAMILY = 1 AND MR_CUST_ADDR_TYPE_CODE = 'LEGAL' AND MR_CUST_TYPE_CODE = 'PERSONAL' AND f.REF_ATTR_CODE = 'AML_CUST_DEPARTMENT' AND g.REF_MASTER_TYPE_CODE = 'ID_TYPE' AND h.REF_MASTER_TYPE_CODE = 'CUST_MODEL' AND i.REF_MASTER_TYPE_CODE = 'BUILDING_OWNERSHIP'"), {  row ->
		instance.eachRow(("SELECT ISNULL([Relationship],'') AS RELATIONSHIP, ISNULL([CustName],'') AS CUST_NAME, ISNULL([BirthPlace],'') AS BIRTH_PLACE, ISNULL([ID_TYPE],'') AS ID_TYPE,ISNULL([IDExpiredDate],'') AS ID_EXP_DATE, ISNULL([MaritalStatus],'') AS MARITAL_STATUS, ISNULL([MobilePHN1],'') AS MOBILE1, ISNULL([CUST_MODEL],'') AS CUST_MODEL, ISNULL([ProfessionCode],'') AS PROF_CODE, ISNULL([EstablishmentDate],'') AS EST_DATE, ISNULL([Gender],'') AS GENDER, ISNULL([BirthDate],'') AS BIRTH_DATE, ISNULL([Idno],'') AS ID_NO, ISNULL([TaxID],'') AS TAX_ID, ISNULL([MotherMaidenName],'') AS MOTHER_MAIDEN_NAME, ISNULL([Email1],'') AS EMAIL1, ISNULL([NationalityCode],'') AS NATIONALITY, case when [CountryCode] = 'INA' then '' else ISNULL([CountryCode],'') END AS COUNTRY, ISNULL([JobPositionCode],'') AS JOB_POS_CODE, ISNULL([Addr],'') AS ADDR, ISNULL([RT],'') AS RT, ISNULL([RW],'') AS RW, ISNULL([Zipcode],'') AS ZIPCODE, ISNULL([Kecamatan],'') AS KECAMATAN, ISNULL([Kelurahan],'') AS KELURAHAN, ISNULL([Kota],'') AS KOTA, ISNULL([BUILDING_OWNERSHIP],'') AS OWNERSHIP FROM (SELECT [Relationship], [CustName], [BirthPlace], [IDExpiredDate], [MaritalStatus], [MobilePHN1], [ProfessionCode], [EstablishmentDate], [Gender], [BirthDate], [Idno], [TaxID], [MotherMaidenName], [Email1], [NationalityCode], [CountryCode], [JobPositionCode], [Addr], [RT], [RW], [Zipcode], [Kecamatan], [Kelurahan], [Kota], mastername.Code, REF_MASTER_NAME FROM (select [Relationship], [CustName], [BirthPlace], [IDExpiredDate], [MaritalStatus], [MobilePHN1], [ProfessionCode], [EstablishmentDate], [Gender], [BirthDate], [Idno], [TaxID], [MotherMaidenName], [Email1], [NationalityCode], [CountryCode], [JobPositionCode], [Addr], [RT], [RW], [Zipcode], [Kecamatan], [Kelurahan], [Kota], [Code], value FROM (SELECT MR_CUST_RELATIONSHIP_CODE AS [Relationship] ,Cust_name AS [CustName], BIRTH_PLACE AS [BirthPlace], ac.MR_ID_TYPE_CODE AS 'ID_TYPE', FORMAT(ID_EXPIRED_DT, 'MM/dd/yyyy') AS [IDExpiredDate], MR_MARITAL_STAT_CODE AS [MaritalStatus], MOBILE_PHN_NO_1 AS [MobilePHN1], MR_CUST_MODEL_CODE AS 'CUST_MODEL', acpjd.MR_PROFESSION_CODE AS [ProfessionCode], FORMAT(acpjd.ESTABLISHMENT_DT, 'MM/dd/yyyy') AS [EstablishmentDate], MR_GENDER_CODE AS [Gender], FORMAT(BIRTH_DT, 'MM/dd/yyyy') AS [BirthDate], ID_NO AS [Idno], TAX_ID_NO AS [TaxID], MOTHER_MAIDEN_NAME AS [MotherMaidenName], EMAIL_1 AS [Email1], MR_NATIONALITY_CODE AS [NationalityCode], NATIONALITY_COUNTRY_CODE AS [CountryCode], MR_JOB_POSITION_CODE AS [JobPositionCode], ADDR AS [Addr], AREA_CODE_4 AS [RT], AREA_CODE_3 AS [RW], ZIPCODE AS [Zipcode], AREA_CODE_2 AS [Kecamatan], AREA_CODE_1 AS [Kelurahan], CITY AS [Kota], aca.MR_HOUSE_OWNERSHIP_CODE AS 'BUILDING_OWNERSHIP' FROM APP_CUST ac WITH(NOLOCK) JOIN app a WITH(NOLOCK) ON ac.APP_ID = a.APP_ID JOIN APP_CUST_PERSONAL acp WITH(NOLOCK) ON ac.APP_CUST_ID = acp.APP_CUST_ID JOIN APP_CUST_ADDR aca WITH(NOLOCK) ON ac.APP_CUST_ID = aca.APP_CUST_ID JOIN APP_CUST_PERSONAL_JOB_DATA acpjd WITH(NOLOCK) ON acp.APP_CUST_PERSONAL_ID = acpjd.APP_CUST_PERSONAL_ID where APP_NO = '"+ appno +"' AND IS_FAMILY = 1 AND Cust_name = '"+ name +"' and MR_CUST_ADDR_TYPE_CODE = 'legal') as Orig unpivot (value for [Code] in ([ID_TYPE],[CUST_MODEL],[BUILDING_OWNERSHIP]))as unpiv) as mastername JOIN REF_MASTER_LOS rf WITH(NOLOCK) ON rf.REF_MASTER_Code = mastername.value WHERE rf.IS_ACTIVE = '1') AS ref PIVOT (MAX(ref.REF_MASTER_NAME) for [Code] in ([ID_TYPE],[CUST_MODEL],[BUILDING_OWNERSHIP])) as piv"), {  row ->
			ResultSetMetaData rsmd = row.getMetaData()
			colmcount = rsmd.getColumnCount()


			for(i = 0 ; i < colmcount ; i++){
				familydata = (row[i])
				listcustomerdata.add(familydata)
			}
		})
		return listcustomerdata
	}

	//get family data personal store db lookup
	@Keyword
	public FamilyDataStoreDBPersonalLookUp (Sql instance, String appno, String name, String proffesionname){
		String familydata
		ArrayList<String> listcustomerdata = new ArrayList<>()
		//		instance.eachRow(("SELECT MR_CUST_RELATIONSHIP_CODE  ,Cust_name , BIRTH_PLACE , g.REF_MASTER_NAME , FORMAT(ID_EXPIRED_DT, 'MM/dd/yyyy') , MR_MARITAL_STAT_CODE , MOBILE_PHN_NO_1 , h.REF_MASTER_NAME , e.MR_PROFESSION_CODE , FORMAT(e.ESTABLISHMENT_DT, 'MM/dd/yyyy') , MR_GENDER_CODE , FORMAT(BIRTH_DT, 'MM/dd/yyyy') , ID_NO , TAX_ID_NO , MOTHER_MAIDEN_NAME , EMAIL_1 , MR_NATIONALITY_CODE , NATIONALITY_COUNTRY_CODE , MR_JOB_POSITION_CODE , f.ATTR_VALUE , SUBQ.ATTR_VALUE , ADDR , AREA_CODE_4 , AREA_CODE_3 , ZIPCODE , AREA_CODE_2 , AREA_CODE_1 , CITY , i.REF_MASTER_NAME  FROM APP_CUST a JOIN app b ON a.APP_ID = b.APP_ID JOIN APP_CUST_PERSONAL c ON a.APP_CUST_ID = c.APP_CUST_ID JOIN APP_CUST_ADDR d ON a.APP_CUST_ID = d.APP_CUST_ID JOIN APP_CUST_PERSONAL_JOB_DATA e ON c.APP_CUST_PERSONAL_ID = e.APP_CUST_PERSONAL_ID JOIN APP_CUST_ATTR_CONTENT f ON f.APP_CUST_ID = a.APP_CUST_ID JOIN REF_MASTER_LOS g ON a.MR_ID_TYPE_CODE = g.REF_MASTER_CODE JOIN REF_MASTER_LOS h ON a.MR_CUST_MODEL_CODE = h.REF_MASTER_CODE JOIN REF_MASTER_LOS i ON d.MR_HOUSE_OWNERSHIP_CODE = i.REF_MASTER_CODE, (SELECT ATTR_VALUE, a.APP_ID FROM APP_CUST_ATTR_CONTENT g JOIN APP_CUST a ON g.APP_CUST_ID = a.APP_CUST_ID JOIN app b ON a.APP_ID = b.APP_ID WHERE APP_NO = '"+ appno +"' AND IS_FAMILY = 1 AND g.REF_ATTR_CODE = 'AUTH_AML') as SUBQ where a.APP_ID = SUBQ.APP_ID AND IS_FAMILY = 1 AND MR_CUST_ADDR_TYPE_CODE = 'LEGAL' AND MR_CUST_TYPE_CODE = 'PERSONAL' AND f.REF_ATTR_CODE = 'AML_CUST_DEPARTMENT' AND g.REF_MASTER_TYPE_CODE = 'ID_TYPE' AND h.REF_MASTER_TYPE_CODE = 'CUST_MODEL' AND i.REF_MASTER_TYPE_CODE = 'BUILDING_OWNERSHIP'"), {  row ->

		instance.eachRow(("SELECT ISNULL(Cust_name,'') AS CUST_NAME, ISNULL(BIRTH_PLACE,'') AS BIRTH_PLACE, ISNULL(ac.MR_ID_TYPE_CODE,'') AS ID_TYPE_CODE, ISNULL(FORMAT(ID_EXPIRED_DT, 'yyyy-MM-dd'),'') AS ID_EXP_DATE, ISNULL(MR_MARITAL_STAT_CODE,'') AS MARITAL_STAT, ISNULL(MOBILE_PHN_NO_1,'') AS MOBILE1, ISNULL(ac.MR_CUST_MODEL_CODE,'') AS CUST_MODEL, ISNULL(MR_GENDER_CODE,'') AS GENDER,ISNULL(FORMAT(BIRTH_DT, 'yyyy-MM-dd'),'') AS BIRTH_DATE, ISNULL(ID_NO,'') AS ID_NO, ISNULL(TAX_ID_NO,'') AS TAX_ID, ISNULL(MOTHER_MAIDEN_NAME,'') AS MOTHERMAIDENNAME, ISNULL(EMAIL_1,'') AS EMAIL1, ISNULL(MR_NATIONALITY_CODE,'') AS NATIONALITY, ISNULL(rc.COUNTRY_NAME,'') AS COUNTRY, ISNULL(ADDR,'') AS ADDR, ISNULL(AREA_CODE_4,'') AS AREA4, ISNULL(AREA_CODE_3,'') AS AREA3, ISNULL(ZIPCODE,'') AS ZIPCODE, ISNULL(AREA_CODE_2,'') AS AREA2, ISNULL(AREA_CODE_1,'') AS AREA1, ISNULL(CITY,'') AS CITY, ISNULL(aca.MR_HOUSE_OWNERSHIP_CODE,'') AS OWNERSHIP, ISNULL(MR_CUST_RELATIONSHIP_CODE,'') AS CUST_RELATIONSHIP, ISNULL(rpl.PROFESSION_NAME,'') AS PROF_NAME, ISNULL(REF_MASTER_NAME,'') AS REF_MASTER_NAME, ISNULL(FORMAT(ESTABLISHMENT_DT,'yyyy-MM-dd'),'') AS EST_DATE FROM APP_CUST ac WITH(NOLOCK) JOIN app a WITH(NOLOCK) ON ac.APP_ID = a.APP_ID JOIN APP_CUST_PERSONAL acp WITH(NOLOCK) ON ac.APP_CUST_ID = acp.APP_CUST_ID JOIN APP_CUST_ADDR aca WITH(NOLOCK) ON ac.APP_CUST_ID = aca.APP_CUST_ID JOIN v_ref_country rc with(nolock) on rc.country_code = CASE WHEN acp.mr_nationality_code = 'FOREIGNER' THEN acp.nationality_country_code ELSE 'INA' END join APP_CUST_PERSONAL_JOB_DATA  acpjd with(nolock) on acpjd.APP_CUST_PERSONAL_ID = acp.APP_CUST_PERSONAL_ID JOIN REF_PROFESSION_LOS rpl on rpl.PROFESSION_CODE = acpjd.MR_PROFESSION_CODE join ref_master_Los rml on rml.REF_MASTER_CODE = acpjd.MR_JOB_POSITION_CODE where APP_NO = '"+ appno +"' AND IS_FAMILY = 1 AND Cust_name = '"+ name +"' and MR_CUST_ADDR_TYPE_CODE = 'legal' and ref_master_type_code = 'JOB_POSITION' AND PROFESSION_NAME = '"+ proffesionname +"' GROUP BY Cust_name , BIRTH_PLACE , ac.MR_ID_TYPE_CODE , FORMAT(ID_EXPIRED_DT, 'yyyy-MM-dd') , MR_MARITAL_STAT_CODE , MOBILE_PHN_NO_1 , ac.MR_CUST_MODEL_CODE , MR_GENDER_CODE ,FORMAT(BIRTH_DT, 'yyyy-MM-dd') , ID_NO , TAX_ID_NO , MOTHER_MAIDEN_NAME , EMAIL_1 , MR_NATIONALITY_CODE , rc.COUNTRY_NAME , ADDR , AREA_CODE_4 , AREA_CODE_3 , ZIPCODE , AREA_CODE_2 , AREA_CODE_1 , CITY , aca.MR_HOUSE_OWNERSHIP_CODE , MR_CUST_RELATIONSHIP_CODE  , rpl.PROFESSION_NAME , REF_MASTER_NAME , FORMAT(ESTABLISHMENT_DT,'yyyy-MM-dd')"), {  row ->

			ResultSetMetaData rsmd = row.getMetaData()
			colmcount = rsmd.getColumnCount()


			for(i = 0 ; i < colmcount ; i++){
				familydata = (row[i])
				listcustomerdata.add(familydata)
			}
		})


		return listcustomerdata

	}

	//get guarantor data personal store db
	@Keyword
	public GuarantorDataStoreDBPersonal (Sql instance, String appno, String name){
		String guarantordata
		ArrayList<String> listcustomerdata = new ArrayList<>()
		//		instance.eachRow(("SELECT MR_CUST_RELATIONSHIP_CODE  ,Cust_name , BIRTH_PLACE , f.REF_MASTER_NAME , FORMAT(ID_EXPIRED_DT, 'MM/dd/yyyy') , MR_MARITAL_STAT_CODE , MOBILE_PHN_NO_1 , g.REF_MASTER_NAME , MR_GENDER_CODE , FORMAT(BIRTH_DT, 'MM/dd/yyyy') , ID_NO , TAX_ID_NO , MOTHER_MAIDEN_NAME , EMAIL_1 , e.ATTR_VALUE , SUBQ.ATTR_VALUE , ADDR , AREA_CODE_4 , AREA_CODE_3 , ZIPCODE , AREA_CODE_2 , AREA_CODE_1 , CITY , h.REF_MASTER_NAME  FROM APP_CUST a JOIN app b ON a.APP_ID = b.APP_ID JOIN APP_CUST_PERSONAL c ON a.APP_CUST_ID = c.APP_CUST_ID JOIN APP_CUST_ADDR d ON a.APP_CUST_ID = d.APP_CUST_ID JOIN APP_CUST_ATTR_CONTENT e ON e.APP_CUST_ID = a.APP_CUST_ID JOIN REF_MASTER_LOS f ON a.MR_ID_TYPE_CODE = f.REF_MASTER_CODE JOIN REF_MASTER_LOS g ON a.MR_CUST_MODEL_CODE = g.REF_MASTER_CODE JOIN REF_MASTER_LOS h ON d.MR_HOUSE_OWNERSHIP_CODE = h.REF_MASTER_CODE, (SELECT ATTR_VALUE, a.APP_ID FROM APP_CUST_ATTR_CONTENT g JOIN APP_CUST a ON g.APP_CUST_ID = a.APP_CUST_ID JOIN app b ON a.APP_ID = b.APP_ID WHERE APP_NO = '"+ appno +"' AND IS_GUARANTOR = 1 AND g.REF_ATTR_CODE = 'AUTH_AML') as SUBQ WHERE APP_NO = '" + appno +"' AND IS_GUARANTOR = 1 AND MR_CUST_ADDR_TYPE_CODE = 'LEGAL' AND MR_CUST_TYPE_CODE = 'PERSONAL' AND e.REF_ATTR_CODE = 'AML_CUST_DEPARTMENT' AND f.REF_MASTER_TYPE_CODE = 'ID_TYPE' AND g.REF_MASTER_TYPE_CODE = 'CUST_MODEL' AND h.REF_MASTER_TYPE_CODE = 'BUILDING_OWNERSHIP'"), {  row ->
		instance.eachRow(("SELECT ISNULL([Relationship],'') AS RELATIONSHIP , ISNULL([CustName],'') AS CUST_NAME, ISNULL([BirthPlace],'') AS BIRTH_PLACE, ISNULL([ID_TYPE],'') AS ID_TYPE ,ISNULL([IDExpiredDate],'') AS ID_EXP_DATE, ISNULL([MaritalStatus],'') AS MARTIAL_STAT, ISNULL([MobilePHN1],'') AS MOBILE1, ISNULL([CUST_MODEL],'') AS CUST_MODEL, ISNULL([Gender],'') AS GENDER, ISNULL([BirthDate],'') AS BIRTH_DATE , ISNULL([Idno],'') AS ID_NO , ISNULL([TaxID],'') AS TAX_ID, ISNULL([MotherMaidenName],'') AS MOTHER_MAIDEN_NAME , ISNULL([Email1],'') AS EMAIL1, ISNULL([Addr],'') AS ADDR, ISNULL([RT],'') AS RT, ISNULL([RW],'') AS RW, ISNULL([Zipcode],'') AS ZIPCODE, ISNULL([Kecamatan],'') AS KECAMATAN , ISNULL([Kelurahan],'') AS KELURAHAN , ISNULL([Kota],'') AS KOTA, ISNULL([BUILDING_OWNERSHIP],'') AS OWNERSHIP FROM (SELECT [Relationship], [CustName], [BirthPlace], [IDExpiredDate], [MaritalStatus], [MobilePHN1], [Gender], [BirthDate], [Idno], [TaxID], [MotherMaidenName], [Email1], [Addr], [RT], [RW], [Zipcode], [Kecamatan], [Kelurahan], [Kota], mastername.Code, REF_MASTER_NAME FROM (select [Relationship], [CustName], [BirthPlace], [IDExpiredDate], [MaritalStatus], [MobilePHN1], [Gender], [BirthDate], [Idno], [TaxID], [MotherMaidenName], [Email1], [Addr], [RT], [RW], [Zipcode], [Kecamatan], [Kelurahan], [Kota], [Code], value FROM (SELECT MR_CUST_RELATIONSHIP_CODE AS [Relationship] ,Cust_name AS [CustName], BIRTH_PLACE AS [BirthPlace], ac.MR_ID_TYPE_CODE AS 'ID_TYPE', FORMAT(ID_EXPIRED_DT, 'MM/dd/yyyy') AS [IDExpiredDate], MR_MARITAL_STAT_CODE AS [MaritalStatus], MOBILE_PHN_NO_1 AS [MobilePHN1], MR_CUST_MODEL_CODE AS 'CUST_MODEL', MR_GENDER_CODE AS [Gender], FORMAT(BIRTH_DT, 'MM/dd/yyyy') AS [BirthDate], ID_NO AS [Idno], TAX_ID_NO AS [TaxID], MOTHER_MAIDEN_NAME AS [MotherMaidenName], EMAIL_1 AS [Email1], ADDR AS [Addr], AREA_CODE_4 AS [RT], AREA_CODE_3 AS [RW], ZIPCODE AS [Zipcode], AREA_CODE_2 AS [Kecamatan], AREA_CODE_1 AS [Kelurahan], CITY AS [Kota], aca.MR_HOUSE_OWNERSHIP_CODE AS 'BUILDING_OWNERSHIP' FROM APP_CUST ac WITH(NOLOCK) JOIN app a WITH(NOLOCK) ON ac.APP_ID = a.APP_ID JOIN APP_CUST_PERSONAL acp WITH(NOLOCK) ON ac.APP_CUST_ID = acp.APP_CUST_ID JOIN APP_CUST_ADDR aca WITH(NOLOCK) ON ac.APP_CUST_ID = aca.APP_CUST_ID where APP_NO = '"+ appno +"' AND IS_GUARANTOR = 1 AND Cust_name = '"+ name +"' AND aca.MR_CUST_ADDR_TYPE_CODE = 'LEGAL') as Orig unpivot (value for [Code] in ([ID_TYPE],[CUST_MODEL],[BUILDING_OWNERSHIP]))as unpiv) as mastername JOIN REF_MASTER_LOS rf WITH(NOLOCK) ON rf.REF_MASTER_Code = mastername.value WHERE rf.IS_ACTIVE = '1') AS ref PIVOT (MAX(ref.REF_MASTER_NAME) for [Code] in ([ID_TYPE],[CUST_MODEL],[BUILDING_OWNERSHIP])) as piv"), {  row ->
			ResultSetMetaData rsmd = row.getMetaData()
			colmcount = rsmd.getColumnCount()


			for(int i = 0 ; i < colmcount ; i++){
				guarantordata = (row[i])
				listcustomerdata.add(guarantordata)
			}
		})
		return listcustomerdata
	}

	//get guarantor data personal store db lookup
	@Keyword
	public GuarantorDataStoreDBPersonalLookUp (Sql instance, String appno, String name){
		String guarantordata
		ArrayList<String> listcustomerdata = new ArrayList<>()
		//		instance.eachRow(("SELECT MR_CUST_RELATIONSHIP_CODE  ,Cust_name , BIRTH_PLACE , f.REF_MASTER_NAME , FORMAT(ID_EXPIRED_DT, 'MM/dd/yyyy') , MR_MARITAL_STAT_CODE , MOBILE_PHN_NO_1 , g.REF_MASTER_NAME , MR_GENDER_CODE , FORMAT(BIRTH_DT, 'MM/dd/yyyy') , ID_NO , TAX_ID_NO , MOTHER_MAIDEN_NAME , EMAIL_1 , e.ATTR_VALUE , SUBQ.ATTR_VALUE , ADDR , AREA_CODE_4 , AREA_CODE_3 , ZIPCODE , AREA_CODE_2 , AREA_CODE_1 , CITY , h.REF_MASTER_NAME  FROM APP_CUST a JOIN app b ON a.APP_ID = b.APP_ID JOIN APP_CUST_PERSONAL c ON a.APP_CUST_ID = c.APP_CUST_ID JOIN APP_CUST_ADDR d ON a.APP_CUST_ID = d.APP_CUST_ID JOIN APP_CUST_ATTR_CONTENT e ON e.APP_CUST_ID = a.APP_CUST_ID JOIN REF_MASTER_LOS f ON a.MR_ID_TYPE_CODE = f.REF_MASTER_CODE JOIN REF_MASTER_LOS g ON a.MR_CUST_MODEL_CODE = g.REF_MASTER_CODE JOIN REF_MASTER_LOS h ON d.MR_HOUSE_OWNERSHIP_CODE = h.REF_MASTER_CODE, (SELECT ATTR_VALUE, a.APP_ID FROM APP_CUST_ATTR_CONTENT g JOIN APP_CUST a ON g.APP_CUST_ID = a.APP_CUST_ID JOIN app b ON a.APP_ID = b.APP_ID WHERE APP_NO = '"+ appno +"' AND IS_GUARANTOR = 1 AND g.REF_ATTR_CODE = 'AUTH_AML') as SUBQ WHERE APP_NO = '" + appno +"' AND IS_GUARANTOR = 1 AND MR_CUST_ADDR_TYPE_CODE = 'LEGAL' AND MR_CUST_TYPE_CODE = 'PERSONAL' AND e.REF_ATTR_CODE = 'AML_CUST_DEPARTMENT' AND f.REF_MASTER_TYPE_CODE = 'ID_TYPE' AND g.REF_MASTER_TYPE_CODE = 'CUST_MODEL' AND h.REF_MASTER_TYPE_CODE = 'BUILDING_OWNERSHIP'"), {  row ->
		instance.eachRow(("SELECT ISNULL(Cust_name,'') as CUST_NAME, ISNULL(BIRTH_PLACE,'') AS BIRTH_PLACE, ISNULL(FORMAT(ID_EXPIRED_DT, 'yyyy-MM-dd'),'') AS ID_EXP_DATE, ISNULL(MOBILE_PHN_NO_1,'') AS MOBILE1, ISNULL(FORMAT(BIRTH_DT, 'yyyy-MM-dd'),'') AS BIRTH_DATE, ISNULL(ID_NO,'') AS ID_NO, ISNULL(TAX_ID_NO,'') AS TAX_ID_NO, ISNULL(MOTHER_MAIDEN_NAME,'') AS MOTHERMAIDENNAME, ISNULL(EMAIL_1,'') AS EMAIL1, ISNULL(ADDR,'') AS ADDR, ISNULL(AREA_CODE_4,'') AS AREA4, ISNULL(AREA_CODE_3,'') AS AREA3, ISNULL(ZIPCODE,'') AS ZIPCODE, ISNULL(AREA_CODE_2,'') AS AREA2, ISNULL(AREA_CODE_1,'') AS AREA1, ISNULL(CITY,'') AS CITY, ISNULL(ac.MR_ID_TYPE_CODE,'') AS ID_TYPE_CODE,ISNULL(MR_MARITAL_STAT_CODE,'') AS MARITAL_STAT, ISNULL(MR_CUST_MODEL_CODE,'') AS CUST_MODEL, ISNULL(MR_GENDER_CODE,'') AS GENDER,ISNULL(aca.MR_HOUSE_OWNERSHIP_CODE,'') AS OWNERHSHIP, ISNULL(MR_CUST_RELATIONSHIP_CODE,'') AS CUST_RELATIONSHIP FROM APP_CUST ac WITH(NOLOCK) JOIN app a WITH(NOLOCK) ON ac.APP_ID = a.APP_ID JOIN APP_CUST_PERSONAL acp WITH(NOLOCK) ON ac.APP_CUST_ID = acp.APP_CUST_ID JOIN APP_CUST_ADDR aca WITH(NOLOCK) ON ac.APP_CUST_ID = aca.APP_CUST_ID where APP_NO = '"+appno+"' AND IS_GUARANTOR = 1 AND Cust_name = '"+name+"' AND aca.MR_CUST_ADDR_TYPE_CODE = 'LEGAL'"), {  row ->
			ResultSetMetaData rsmd = row.getMetaData()
			colmcount = rsmd.getColumnCount()


			for(i = 0 ; i < colmcount ; i++){
				guarantordata = (row[i])
				listcustomerdata.add(guarantordata)
			}

		})
		return listcustomerdata
	}

	//get guarantor data company store db
	@Keyword
	public GuarantorDataStoreDBCompany (Sql instance, String appno, String name){
		String guarantordata
		ArrayList<String> listcustomerdata = new ArrayList<>()
		instance.eachRow(("SELECT ISNULL([RELATIONSHIP],'') AS RELATIONSHIP, ISNULL(CUST_NAME,'') AS CUST_NAME ,ISNULL(TAX_ID_NO,'') AS TAX_ID_NO  , ISNULL(MR_COMPANY_TYPE_CODE,'') AS COMPANY_TYPE_CODE, ISNULL([CUST_MODEL],'') AS CUST_MODEL, ISNULL(ADDR,'') AS ADDR, ISNULL(AREA_CODE_4,'') AS AREAP4,  ISNULL(AREA_CODE_3,'') AS AREA3 ,ISNULL(ZIPCODE,'') AS ZIPCODE, ISNULL(AREA_CODE_2,'') AS AREA2, ISNULL(AREA_CODE_1,'') AS AREA1, ISNULL(CITY,'') AS CITY, ISNULL([BUILDING],'') AS BUILDING FROM (SELECT CUST_NAME ,TAX_ID_NO  , ADDR , AREA_CODE_4,  AREA_CODE_3 ,ZIPCODE , AREA_CODE_2 , AREA_CODE_1 , CITY , MR_COMPANY_TYPE_CODE, mastername.Code, REF_MASTER_NAME FROM (SELECT CUST_NAME ,TAX_ID_NO  , ADDR , AREA_CODE_4,  AREA_CODE_3 ,ZIPCODE , AREA_CODE_2 , AREA_CODE_1 , CITY , MR_COMPANY_TYPE_CODE, [Code],value FROM (SELECT ac.CUST_NAME,TAX_ID_NO  , ADDR , AREA_CODE_4,  AREA_CODE_3 , ZIPCODE , AREA_CODE_2 , AREA_CODE_1 , CITY , MR_COMPANY_TYPE_CODE , MR_HOUSE_OWNERSHIP_CODE as [BUILDING], MR_CUST_RELATIONSHIP_CODE AS [RELATIONSHIP], MR_CUST_MODEL_CODE AS [CUST_MODEL] FROM APP_CUST ac WITH(NOLOCK) JOIN app a WITH(NOLOCK) ON ac.APP_ID = a.APP_ID JOIN APP_CUST_COMPANY acc WITH(NOLOCK) ON ac.APP_CUST_ID = acc.APP_CUST_ID JOIN APP_CUST_ADDR aca WITH(NOLOCK) ON ac.APP_CUST_ID = aca.APP_CUST_ID where a.APP_NO = '"+ appno +"' AND ac.CUST_NAME = '"+ name +"' AND ac.IS_GUARANTOR = 1 AND aca.MR_CUST_ADDR_TYPE_CODE = 'LEGAL' AND MR_CUST_TYPE_CODE = 'COMPANY') AS orig unpivot (value for [Code] in ([BUILDING],[RELATIONSHIP],[CUST_MODEL]) ) as unpiv ) as mastername JOIN REF_MASTER_LOS rf WITH(NOLOCK) ON rf.REF_MASTER_Code = mastername.value where rf.ref_master_type_code in ('BUILDING_OWNERSHIP','GUAR_COMPANY_RELATIONSHIP','CUST_MODEL')) AS ref  PIVOT (MAX(ref.REF_MASTER_NAME) for [Code] in ([BUILDING],[RELATIONSHIP],[CUST_MODEL])) as piv"), {  row ->
			ResultSetMetaData rsmd = row.getMetaData()
			colmcount = rsmd.getColumnCount()


			for(i = 0 ; i < colmcount ; i++){
				guarantordata = (row[i])
				listcustomerdata.add(guarantordata)
			}
		})
		return listcustomerdata
	}

	//get guarantor data company store db lookup
	@Keyword
	public GuarantorDataStoreDBCompanyLookUp (Sql instance, String appno, String name){
		String guarantordata
		ArrayList<String> listcustomerdata = new ArrayList<>()
		instance.eachRow(("SELECT ISNULL(ac.CUST_NAME,'') AS CUST_NAME,ISNULL(TAX_ID_NO,'') AS TAX_ID_NO, ISNULL(ADDR,'') AS ADDR, ISNULL(AREA_CODE_4,'') AS AREA4,  ISNULL(AREA_CODE_3,'') AS AREA3, ISNULL(ZIPCODE,'') AS ZIPCODE, ISNULL(AREA_CODE_2,'') AS AREA2, ISNULL(AREA_CODE_1,'') AS AREA1, ISNULL(CITY,'') AS CITY , ISNULL(MR_COMPANY_TYPE_CODE,'') AS COMPANY_TYPE_CODE, ISNULL(MR_CUST_MODEL_CODE,'') AS CUST_MODEL, ISNULL(MR_HOUSE_OWNERSHIP_CODE,'') AS OWNERSHIP , ISNULL(MR_CUST_RELATIONSHIP_CODE,'') AS RELATIONSHIP_CUST FROM APP_CUST ac WITH(NOLOCK) JOIN app a WITH(NOLOCK) ON ac.APP_ID = a.APP_ID JOIN APP_CUST_COMPANY acc WITH(NOLOCK) ON ac.APP_CUST_ID = acc.APP_CUST_ID JOIN APP_CUST_ADDR aca WITH(NOLOCK) ON ac.APP_CUST_ID = aca.APP_CUST_ID where a.APP_NO = '"+ appno +"' AND ac.CUST_NAME = '"+ name +"' AND ac.IS_GUARANTOR = 1 AND aca.MR_CUST_ADDR_TYPE_CODE = 'LEGAL' AND MR_CUST_TYPE_CODE = 'COMPANY'"), {  row ->
			ResultSetMetaData rsmd = row.getMetaData()
			colmcount = rsmd.getColumnCount()


			for(i = 0 ; i < colmcount ; i++){
				guarantordata = (row[i])
				listcustomerdata.add(guarantordata)
			}
		})
		return listcustomerdata
	}

	//get customer data company store db
	@Keyword
	public CustomerDataStoreDBCompany (Sql instance, String appno, String name){
		String customerdata
		ArrayList<String> listcustomerdata = new ArrayList<>()
		instance.eachRow(("SELECT ISNULL(ac.CUST_NAME,'') AS CUST_NAME,ISNULL(TAX_ID_NO,'') AS TAX_ID_NO ,ISNULL(MR_COMPANY_TYPE_CODE,'') AS COMPANY_TYPE_CODE ,ISNULL(SUBQ.REF_MASTER_NAME,'') AS CUST_MODEL_CODE , ISNULL(ADDR,'') AS ADDR, ISNULL(AREA_CODE_4,'') AS AREA4, ISNULL(AREA_CODE_3,'') AS AREA3, ISNULL(ZIPCODE,'') AS ZIPCODE, ISNULL(AREA_CODE_2,'') AS AREA2, ISNULL(AREA_CODE_1,'') AS AREA1, ISNULL(CITY,'') AS CITY, ISNULL(rml.REF_MASTER_NAME,'') AS OWNERSHIP FROM APP_CUST ac WITH(NOLOCK) JOIN app a WITH(NOLOCK) ON ac.APP_ID = a.APP_ID JOIN APP_CUST_COMPANY acc WITH(NOLOCK) ON ac.APP_CUST_ID = acc.APP_CUST_ID JOIN APP_CUST_ADDR aca WITH(NOLOCK) ON ac.APP_CUST_ID = aca.APP_CUST_ID JOIN REF_MASTER_LOS rml WITH(NOLOCK) ON rml.REF_MASTER_CODE = aca.MR_HOUSE_OWNERSHIP_CODE,(SELECT rml.REF_MASTER_NAME, a.APP_NO, ac.CUST_NAME FROM APP_CUST_COMPANY acc WITH(NOLOCK) JOIN APP_CUST ac WITH(NOLOCK) ON acc.APP_CUST_ID = ac.APP_CUST_ID JOIN APP a WITH(NOLOCK) ON a.APP_ID = ac.APP_ID JOIN REF_MASTER_LOS rml WITH(NOLOCK) ON rml.REF_MASTER_CODE = ac.MR_CUST_MODEL_CODE WHERE REF_MASTER_TYPE_CODE = 'CUST_MODEL') as SUBQ where a.APP_NO = '"+ appno +"' AND ac.CUST_NAME = '"+ name +"' AND ac.IS_CUSTOMER = 1 AND aca.MR_CUST_ADDR_TYPE_CODE = 'LEGAL' AND MR_CUST_TYPE_CODE = 'COMPANY' AND rml.REF_MASTER_TYPE_CODE = 'BUILDING_OWNERSHIP' AND SUBQ.APP_NO = a.APP_NO AND SUBQ.CUST_NAME = ac.CUST_NAME AND rml.IS_ACTIVE = '1'"), {  row ->

			ResultSetMetaData rsmd = row.getMetaData()
			colmcount = rsmd.getColumnCount()


			for(i = 0 ; i < colmcount ; i++){
				customerdata = (row[i])
				listcustomerdata.add(customerdata)
			}
		})
		return listcustomerdata
	}

	//get customer data company store db lookup
	@Keyword
	public CustomerDataStoreDBCompanyLookUp (Sql instance, String appno, String name){
		String customerdata
		ArrayList<String> listcustomerdata = new ArrayList<>()
		instance.eachRow(("SELECT ISNULL(ac.CUST_NAME,'') AS CUST_NAME , ISNULL(ac.TAX_ID_NO,'') AS TAX_ID_NO , ISNULL(aca.ADDR,'') AS ADDR , ISNULL(aca.AREA_CODE_4,'') AS AREA4, ISNULL(aca.AREA_CODE_3,'') AS AREA3, ISNULL(aca.ZIPCODE,'') AS ZIPCODE, ISNULL(aca.AREA_CODE_2,'') AS AREA2, ISNULL(aca.AREA_CODE_1,'') AS AREA1, ISNULL(aca.CITY,'') AS CITY, ISNULL(acc.MR_COMPANY_TYPE_CODE,'') AS COMPANY_TYPE , ISNULL(SUBQ.REF_MASTER_NAME,'') AS REFMASTER1, ISNULL(rml.REF_MASTER_NAME,'') AS REFMASTER2 FROM APP_CUST ac WITH(NOLOCK) JOIN app a WITH(NOLOCK) ON ac.APP_ID = a.APP_ID JOIN APP_CUST_COMPANY acc WITH(NOLOCK) ON ac.APP_CUST_ID = acc.APP_CUST_ID JOIN APP_CUST_ADDR aca WITH(NOLOCK) ON ac.APP_CUST_ID = aca.APP_CUST_ID JOIN REF_MASTER_LOS rml WITH(NOLOCK) ON rml.REF_MASTER_CODE = aca.MR_HOUSE_OWNERSHIP_CODE, (SELECT rml.REF_MASTER_NAME, a.APP_NO, ac.CUST_NAME FROM APP_CUST_COMPANY acc WITH(NOLOCK) JOIN APP_CUST ac WITH(NOLOCK) ON acc.APP_CUST_ID = ac.APP_CUST_ID JOIN APP a WITH(NOLOCK) ON a.APP_ID = ac.APP_ID JOIN REF_MASTER_LOS rml WITH(NOLOCK) ON rml.REF_MASTER_CODE = ac.MR_CUST_MODEL_CODE WHERE REF_MASTER_TYPE_CODE = 'CUST_MODEL') as SUBQ where a.APP_NO = '"+ appno +"' AND ac.CUST_NAME = '"+ name +"' AND ac.IS_CUSTOMER = 1 AND aca.MR_CUST_ADDR_TYPE_CODE = 'LEGAL' AND MR_CUST_TYPE_CODE = 'COMPANY' AND rml.REF_MASTER_TYPE_CODE = 'BUILDING_OWNERSHIP' AND SUBQ.APP_NO = a.APP_NO AND SUBQ.CUST_NAME = ac.CUST_NAME AND rml.IS_ACTIVE = '1'"), {  row ->

			ResultSetMetaData rsmd = row.getMetaData()
			colmcount = rsmd.getColumnCount()


			for(i = 0 ; i < colmcount ; i++){
				customerdata = (row[i])
				listcustomerdata.add(customerdata)
			}
		})
		return listcustomerdata
	}

	//get MS data company store db
	@Keyword
	public MSDataStoreDBCompany (Sql instance, String appno, String name){
		String MSdata
		ArrayList<String> listcustomerdata = new ArrayList<>()
		instance.eachRow(("SELECT ISNULL(CUST_NAME,'') AS CUST_NAME ,ISNULL(TAX_ID_NO,'') AS TAX_ID , ISNULL([POSITION_SLIK],'') AS POS_SLIK, ISNULL(MR_COMPANY_TYPE_CODE,'') AS COMPANY_TYPE, ISNULL([CUST_MODEL],'') AS CUST_MODEL, ISNULL([SHARE_PRCNT],'') AS SHARE_PERCENT, Case when [ISACTIVE] = 1 Then 'Yes' when [ISACTIVE] = 0 Then 'No' END AS ISACTIVE, Case when IS_OWNER = 1 Then 'Yes' when IS_OWNER = 0 Then 'No' END AS ISOWNER, ISNULL(ADDR,'') AS ADDR, ISNULL(AREA_CODE_4,'') AS AREA4,  ISNULL(AREA_CODE_3,'') AS AREA3 ,ISNULL(ZIPCODE,'') AS ZIPCODE, ISNULL(AREA_CODE_2,'') AS AREA2, ISNULL(AREA_CODE_1,'') AS AREA1, ISNULL(CITY,'') AS CITY, ISNULL([BUILDING],'') AS BUILDING FROM (SELECT CUST_NAME ,TAX_ID_NO  , ADDR , AREA_CODE_4,  AREA_CODE_3 ,ZIPCODE , AREA_CODE_2 , AREA_CODE_1 , CITY , MR_COMPANY_TYPE_CODE, [SHARE_PRCNT], [ISACTIVE], IS_OWNER, mastername.Code, REF_MASTER_NAME FROM (SELECT CUST_NAME ,TAX_ID_NO  , ADDR , AREA_CODE_4,  AREA_CODE_3 ,ZIPCODE , AREA_CODE_2 , AREA_CODE_1 , CITY , MR_COMPANY_TYPE_CODE, [SHARE_PRCNT], [ISACTIVE], IS_OWNER, [Code],value FROM (SELECT ac.CUST_NAME,TAX_ID_NO  , ADDR , AREA_CODE_4, AREA_CODE_3 , ZIPCODE , AREA_CODE_2 , AREA_CODE_1 , CITY , MR_COMPANY_TYPE_CODE , MR_HOUSE_OWNERSHIP_CODE as [BUILDING], CAST(MR_POSITION_SLIK_CODE as nvarchar(50)) AS [POSITION_SLIK], MR_CUST_MODEL_CODE AS [CUST_MODEL], FORMAT(SHARE_PRCNT, 'N0') AS [SHARE_PRCNT], accMS.IS_ACTIVE AS [ISACTIVE], IS_OWNER FROM APP_CUST ac WITH(NOLOCK) JOIN app a WITH(NOLOCK) ON ac.APP_ID = a.APP_ID JOIN APP_CUST_COMPANY acc WITH(NOLOCK) ON ac.APP_CUST_ID = acc.APP_CUST_ID JOIN APP_CUST_ADDR aca WITH(NOLOCK) ON ac.APP_CUST_ID = aca.APP_CUST_ID JOIN APP_CUST_COMPANY_MGMNT_SHRHOLDER accMS WITH(NOLOCK) ON ac.APP_CUST_ID = accMS.APP_CUST_ID WHERE a.APP_NO = '"+ appno +"' AND CUST_NAME = '"+ name +"' AND IS_SHAREHOLDER = 1 AND MR_CUST_ADDR_TYPE_CODE = 'LEGAL' AND MR_CUST_TYPE_CODE = 'COMPANY') AS orig unpivot (value for [Code] in ([BUILDING],[POSITION_SLIK],[CUST_MODEL])) as unpiv ) as mastername JOIN REF_MASTER_LOS rf WITH(NOLOCK) ON rf.REF_MASTER_Code = mastername.value where rf.ref_master_type_code in ('BUILDING_OWNERSHIP','CUST_MODEL','POSITION_SLIK')) AS ref  PIVOT (MAX(ref.REF_MASTER_NAME) for [Code] in ([BUILDING],[POSITION_SLIK],[CUST_MODEL])) as piv"), {  row ->

			ResultSetMetaData rsmd = row.getMetaData()
			colmcount = rsmd.getColumnCount()


			for(i = 0 ; i < colmcount ; i++){
				MSdata = (row[i])
				listcustomerdata.add(MSdata)
			}
		})
		return listcustomerdata
	}

	//get MS data company store db lookup
	@Keyword
	public MSDataStoreDBCompanyLookUp (Sql instance, String appno, String name){
		String MSdata
		ArrayList<String> listcustomerdata = new ArrayList<>()
		instance.eachRow(("SELECT ISNULL(CUST_NAME,'') AS CUST_NAME ,ISNULL(TAX_ID_NO,'') AS TAX_ID , ISNULL(ADDR,'') AS ADDR, ISNULL(AREA_CODE_4,'') AS AREA4,  ISNULL(AREA_CODE_3,'') AS AREA3 ,ISNULL(ZIPCODE,'') AS ZIPCODE , ISNULL(AREA_CODE_2,'') AS AREA2, ISNULL(AREA_CODE_1,'') AS AREA1, ISNULL(CITY,'') AS CITY, ISNULL([POSITION_SLIK],'') AS POS_SLIK, ISNULL(MR_COMPANY_TYPE_CODE,'') AS COMPANY_TYPE, ISNULL([CUST_MODEL],'') AS CUST_MODEL, ISNULL([SHARE_PRCNT],'') AS SHARE_PERCENT, Case when [ISACTIVE] = 1 Then 'Yes' when [ISACTIVE] = 0 Then 'No' END AS ISACTIVE, Case when IS_OWNER = 1 Then 'Yes' when IS_OWNER = 0 Then 'No' END AS ISOWNER, ISNULL([BUILDING],'') AS BUILDING FROM (SELECT CUST_NAME ,TAX_ID_NO  , ADDR , AREA_CODE_4,  AREA_CODE_3 ,ZIPCODE , AREA_CODE_2 , AREA_CODE_1 , CITY , MR_COMPANY_TYPE_CODE, [SHARE_PRCNT], [ISACTIVE], IS_OWNER, mastername.Code, REF_MASTER_NAME FROM (SELECT CUST_NAME ,TAX_ID_NO  , ADDR , AREA_CODE_4,  AREA_CODE_3 ,ZIPCODE , AREA_CODE_2 , AREA_CODE_1 , CITY , MR_COMPANY_TYPE_CODE, [SHARE_PRCNT], [ISACTIVE], IS_OWNER, [Code],value FROM (SELECT ac.CUST_NAME,TAX_ID_NO  , ADDR , AREA_CODE_4, AREA_CODE_3 , ZIPCODE , AREA_CODE_2 , AREA_CODE_1 , CITY , MR_COMPANY_TYPE_CODE , MR_HOUSE_OWNERSHIP_CODE as [BUILDING], CAST(MR_POSITION_SLIK_CODE as nvarchar(50)) AS [POSITION_SLIK], MR_CUST_MODEL_CODE AS [CUST_MODEL], SHARE_PRCNT AS [SHARE_PRCNT], accMS.IS_ACTIVE AS [ISACTIVE], IS_OWNER FROM APP_CUST ac WITH(NOLOCK) JOIN app a WITH(NOLOCK) ON ac.APP_ID = a.APP_ID JOIN APP_CUST_COMPANY acc WITH(NOLOCK) ON ac.APP_CUST_ID = acc.APP_CUST_ID JOIN APP_CUST_ADDR aca WITH(NOLOCK) ON ac.APP_CUST_ID = aca.APP_CUST_ID JOIN APP_CUST_COMPANY_MGMNT_SHRHOLDER accMS WITH(NOLOCK) ON ac.APP_CUST_ID = accMS.APP_CUST_ID WHERE a.APP_NO = '"+ appno +"' AND CUST_NAME = '"+ name +"' AND IS_SHAREHOLDER = 1 AND MR_CUST_ADDR_TYPE_CODE = 'LEGAL' AND MR_CUST_TYPE_CODE = 'COMPANY') AS orig unpivot (value for [Code] in ([BUILDING],[POSITION_SLIK],[CUST_MODEL])) as unpiv ) as mastername JOIN REF_MASTER_LOS rf WITH(NOLOCK) ON rf.REF_MASTER_Code = mastername.value where rf.ref_master_type_code in ('BUILDING_OWNERSHIP','CUST_MODEL','POSITION_SLIK')) AS ref  PIVOT (MAX(ref.REF_MASTER_NAME) for [Code] in ([BUILDING],[POSITION_SLIK],[CUST_MODEL])) as piv"), {  row ->

			ResultSetMetaData rsmd = row.getMetaData()
			colmcount = rsmd.getColumnCount()


			for(i = 0 ; i < colmcount ; i++){
				MSdata = (row[i])
				listcustomerdata.add(MSdata)
			}
		})
		return listcustomerdata
	}

	//get MS data personal store db
	@Keyword
	public MSDataStoreDBPersonal (Sql instance, String appno, String name){
		String MSdata
		ArrayList<String> listcustomerdata = new ArrayList<>()
		instance.eachRow(("SELECT ISNULL([CustName],'') AS CUST_NAME, ISNULL([BirthPlace],'') AS BIRTH_PLACE, ISNULL([ID_TYPE],'') AS IDTYPE,ISNULL([IDExpiredDate],'') AS ID_EXP_DATE, ISNULL([MaritalStatus],'') AS MARITALSTAT, ISNULL([MobilePHN1],'') AS MOBILE1, ISNULL([CUST_MODEL],'') AS CUST_MODEL, ISNULL([ProffesionCode],'') AS PROF_CODE, ISNULL([POSITION_SLIK],'') AS POS_SLIK, ISNULL([EstablishmentDate],'') AS EST_DATE, ISNULL([Gender],'') AS GENDER, ISNULL([BirthDate],'') AS BIRTH_DATE, ISNULL([Idno],'') AS ID_NO, ISNULL([TaxID],'') AS TAX_ID, ISNULL([MotherMaidenName],'') AS MOTHER_MAIDEN_NAME, ISNULL([Email1],'') AS EMAIL1, ISNULL([JOB_POSITION],'') AS JOB_POS, ISNULL([Sharepercent],'') AS SHAREPERCENT, Case when [isOwner] = 1 Then 'Yes' when [isOwner] = 0 Then 'No' END AS ISOWNER, Case when [isActive] = 1 Then 'Yes' when [isActive] = 0 Then 'No' END AS ISACTIVE, Case when [isSigner] = 1 Then 'Yes' when [isSigner] = 0 Then 'No' END AS ISSIGNER, ISNULL([DEPARTMENT_AML],'') AS DEPT_AML, ISNULL([AuthAML],'') AS AUTH_AML, ISNULL([Addr],'') AS ADDR, ISNULL([RT],'') AS RT, ISNULL([RW],'') AS RW, ISNULL([Zipcode],'') AS ZIPCODE, ISNULL([Kecamatan],'') AS KECAMATAN, ISNULL([Kelurahan],'') AS KELURAHAN, ISNULL([Kota],'') AS KOTA, ISNULL([BUILDING_OWNERSHIP],'') AS OWNERSHIP FROM (SELECT [CustName], [BirthPlace], [IDExpiredDate], [MaritalStatus], [MobilePHN1], [ProffesionCode], [Gender], [EstablishmentDate], [BirthDate], [Idno], [TaxID], [MotherMaidenName], [Email1], [Sharepercent], [isOwner], [isActive], [isSigner], [AuthAML], [Addr], [RT], [RW], [Zipcode], [Kecamatan], [Kelurahan], [Kota], mastername.Code, REF_MASTER_NAME FROM (select [CustName], [BirthPlace], [IDExpiredDate], [MaritalStatus], [MobilePHN1], [ProffesionCode], [Gender], [EstablishmentDate], [BirthDate], [Idno], [TaxID], [MotherMaidenName], [Email1], [Sharepercent], [isOwner], [isActive], [isSigner], [AuthAML], [Addr], [RT], [RW], [Zipcode], [Kecamatan], [Kelurahan], [Kota], [Code], value FROM (SELECT Cust_name AS [CustName], BIRTH_PLACE AS [BirthPlace], ac.MR_ID_TYPE_CODE AS 'ID_TYPE', FORMAT(ID_EXPIRED_DT, 'MM/dd/yyyy') AS [IDExpiredDate], MR_MARITAL_STAT_CODE AS [MaritalStatus], MOBILE_PHN_NO_1 AS [MobilePHN1], ac.MR_CUST_MODEL_CODE AS 'CUST_MODEL', rpl.PROFESSION_NAME AS [ProffesionCode], CAST(MR_POSITION_SLIK_CODE as nvarchar(50)) AS 'POSITION_SLIK', FORMAT(accMS.ESTABLISHMENT_DT, 'MM/dd/yyyy') AS [EstablishmentDate], MR_GENDER_CODE AS [Gender], FORMAT(BIRTH_DT, 'MM/dd/yyyy') AS [BirthDate], ID_NO AS [Idno], TAX_ID_NO AS [TaxID], MOTHER_MAIDEN_NAME AS [MotherMaidenName], EMAIL_1 AS [Email1],  accMS.MR_JOB_POSITION_CODE AS 'JOB_POSITION', FORMAT(SHARE_PRCNT, 'N0') AS [Sharepercent], IS_OWNER AS [isOwner], accMS.IS_ACTIVE AS [isActive], IS_SIGNER AS [isSigner], CAST(attr.ATTR_VALUE as nvarchar(50)) AS 'DEPARTMENT_AML', SUBQ.ATTR_VALUE AS [AuthAML], ADDR AS [Addr], AREA_CODE_4 AS [RT], AREA_CODE_3 AS [RW], ZIPCODE AS [Zipcode], AREA_CODE_2 AS [Kecamatan], AREA_CODE_1 AS [Kelurahan], CITY AS [Kota], aca.MR_HOUSE_OWNERSHIP_CODE AS 'BUILDING_OWNERSHIP' FROM APP_CUST ac WITH(NOLOCK) JOIN app a WITH(NOLOCK) ON ac.APP_ID = a.APP_ID JOIN APP_CUST_PERSONAL acp WITH(NOLOCK) ON ac.APP_CUST_ID = acp.APP_CUST_ID JOIN APP_CUST_ADDR aca WITH(NOLOCK) ON ac.APP_CUST_ID = aca.APP_CUST_ID JOIN APP_CUST_COMPANY_MGMNT_SHRHOLDER accMS WITH(NOLOCK) ON acp.APP_CUST_ID = accMS.APP_CUST_ID JOIN APP_CUST_PERSONAL_JOB_DATA acpjd WITH(NOLOCK) ON acpjd.APP_CUST_PERSONAL_ID = acp.APP_CUST_PERSONAL_ID JOIN APP_CUST_ATTR_CONTENT attr WITH(NOLOCK) ON attr.APP_CUST_ID = ac.APP_CUST_ID JOIN REF_PROFESSION_LOS rpl WITH(NOLOCK) ON rpl.PROFESSION_CODE = acpjd.MR_PROFESSION_CODE, (SELECT ATTR_VALUE, ac.APP_ID FROM APP_CUST_ATTR_CONTENT acat JOIN APP_CUST ac WITH(NOLOCK) ON acat.APP_CUST_ID = ac.APP_CUST_ID JOIN app a WITH(NOLOCK) ON ac.APP_ID = a.APP_ID WHERE APP_NO = '"+ appno +"' AND IS_SHAREHOLDER = 1 AND acat.REF_ATTR_CODE = 'AUTH_AML') as SUBQ where ac.APP_ID = SUBQ.APP_ID AND MR_CUST_ADDR_TYPE_CODE = 'LEGAL' AND accMS.MR_SHRHOLDER_TYPE_CODE = 'PERSONAL' AND CUST_NAME = '"+ name +"' AND attr.REF_ATTR_CODE = 'AML_CUST_DEPARTMENT') as Orig unpivot (value for [Code] in ([ID_TYPE],[CUST_MODEL],[BUILDING_OWNERSHIP],[JOB_POSITION],[DEPARTMENT_AML],[POSITION_SLIK]))as unpiv) as mastername JOIN REF_MASTER_LOS rf WITH(NOLOCK) ON rf.REF_MASTER_Code = mastername.value WHERE rf.IS_ACTIVE = '1') AS ref PIVOT (MAX(ref.REF_MASTER_NAME) for [Code] in ([ID_TYPE],[CUST_MODEL],[BUILDING_OWNERSHIP],[JOB_POSITION],[DEPARTMENT_AML],[POSITION_SLIK])) as piv"), {  row ->

			ResultSetMetaData rsmd = row.getMetaData()
			colmcount = rsmd.getColumnCount()


			for(i = 0 ; i < colmcount ; i++){
				MSdata = (row[i])
				listcustomerdata.add(MSdata)
			}
		})
		return listcustomerdata
	}


	//get MS data personal store db lookup
	@Keyword
	public MSDataStoreDBPersonalLookUp (Sql instance, String appno, String name, String proffesionname){
		String MSdata
		ArrayList<String> listcustomerdata = new ArrayList<>()
		println("TEST3")
		instance.eachRow(("SELECT ISNULL([CustName],'') AS CUST_NAME, ISNULL([BirthPlace],'') AS BIRTH_PLACE, ISNULL([IDExpiredDate],'') AS ID_EXP_DATE, ISNULL([MobilePHN1],'') AS MOBILE1, ISNULL([BirthDate],'') AS BIRTH_DATE, ISNULL([Idno],'') AS IDNO, ISNULL([TaxID],'') AS TAXID, ISNULL([MotherMaidenName],'') AS MOTHER_MAIDEN_NAME, ISNULL([Email1],'') AS EMAIL1, ISNULL([Addr],'') AS ADDR, ISNULL([RT],'') AS RT, ISNULL([RW],'') AS RW, ISNULL([Zipcode],'') AS ZIPCODE, ISNULL([Kecamatan],'') AS KECAMATAN, ISNULL([Kelurahan],'') AS KELURAHAN, ISNULL([Kota],'') AS KOTA, ISNULL([ID_TYPE],'') AS IDTYPE, ISNULL([MaritalStatus],'') AS MARITAL_STAT, ISNULL([CUST_MODEL],'') AS CUST_MODEL, ISNULL([ProffesionCode],'') AS PROF_CODE, ISNULL([POSITION_SLIK],'') AS POS_SLIK, ISNULL([EstablishmentDate],'') AS EST_DATE, ISNULL([Gender],'') AS GENDER, ISNULL([JOB_POSITION],'') AS JOB_POS, ISNULL([Sharepercent],'') AS SHAREPERCENT, Case when [isOwner] = 1 Then 'Yes' when [isOwner] = 0 Then 'No' END AS ISOWNER, Case when [isActive] = 1 Then 'Yes' when [isActive] = 0 Then 'No' END AS ISACTIVE, Case when [isSigner] = 1 Then 'Yes' when [isSigner] = 0 Then 'No' END AS ISSIGNER, ISNULL([DEPARTMENT_AML],'') AS DEPT_AML, ISNULL([AuthAML],'') AS AUTH_AML, ISNULL([BUILDING_OWNERSHIP],'') AS OWNERSHIP FROM (SELECT [CustName], [BirthPlace], [IDExpiredDate], [MaritalStatus], [MobilePHN1], [ProffesionCode], [Gender], [EstablishmentDate], [BirthDate], [Idno], [TaxID], [MotherMaidenName], [Email1], [Sharepercent], [isOwner], [isActive], [isSigner], [AuthAML], [Addr], [RT], [RW], [Zipcode], [Kecamatan], [Kelurahan], [Kota], mastername.Code, REF_MASTER_NAME FROM (select [CustName], [BirthPlace], [IDExpiredDate], [MaritalStatus], [MobilePHN1], [ProffesionCode], [Gender], [EstablishmentDate], [BirthDate], [Idno], [TaxID], [MotherMaidenName], [Email1], [Sharepercent], [isOwner], [isActive], [isSigner], [AuthAML], [Addr], [RT], [RW], [Zipcode], [Kecamatan], [Kelurahan], [Kota], [Code], value FROM (SELECT Cust_name AS [CustName], BIRTH_PLACE AS [BirthPlace], ac.MR_ID_TYPE_CODE AS 'ID_TYPE', FORMAT(ID_EXPIRED_DT, 'yyyy-MM-dd') AS [IDExpiredDate], MR_MARITAL_STAT_CODE AS [MaritalStatus], MOBILE_PHN_NO_1 AS [MobilePHN1], ac.MR_CUST_MODEL_CODE AS 'CUST_MODEL', rpl.PROFESSION_NAME AS [ProffesionCode], CAST(MR_POSITION_SLIK_CODE as nvarchar(50)) AS 'POSITION_SLIK', FORMAT(accMS.ESTABLISHMENT_DT, 'yyyy-MM-dd') AS [EstablishmentDate], MR_GENDER_CODE AS [Gender], FORMAT(BIRTH_DT, 'yyyy-MM-dd') AS [BirthDate], ID_NO AS [Idno], TAX_ID_NO AS [TaxID], MOTHER_MAIDEN_NAME AS [MotherMaidenName], EMAIL_1 AS [Email1],  accMS.MR_JOB_POSITION_CODE AS 'JOB_POSITION', SHARE_PRCNT AS [Sharepercent], IS_OWNER AS [isOwner], accMS.IS_ACTIVE AS [isActive], IS_SIGNER AS [isSigner], CAST(attr.ATTR_VALUE as nvarchar(50)) AS 'DEPARTMENT_AML', SUBQ.ATTR_VALUE AS [AuthAML], ADDR AS [Addr], AREA_CODE_4 AS [RT], AREA_CODE_3 AS [RW], ZIPCODE AS [Zipcode], AREA_CODE_2 AS [Kecamatan], AREA_CODE_1 AS [Kelurahan], CITY AS [Kota], aca.MR_HOUSE_OWNERSHIP_CODE AS 'BUILDING_OWNERSHIP' FROM APP_CUST ac WITH(NOLOCK) JOIN app a WITH(NOLOCK) ON ac.APP_ID = a.APP_ID JOIN APP_CUST_PERSONAL acp WITH(NOLOCK) ON ac.APP_CUST_ID = acp.APP_CUST_ID JOIN APP_CUST_ADDR aca WITH(NOLOCK) ON ac.APP_CUST_ID = aca.APP_CUST_ID JOIN APP_CUST_COMPANY_MGMNT_SHRHOLDER accMS WITH(NOLOCK) ON acp.APP_CUST_ID = accMS.APP_CUST_ID JOIN APP_CUST_PERSONAL_JOB_DATA acpjd WITH(NOLOCK) ON acpjd.APP_CUST_PERSONAL_ID = acp.APP_CUST_PERSONAL_ID JOIN APP_CUST_ATTR_CONTENT attr WITH(NOLOCK) ON attr.APP_CUST_ID = ac.APP_CUST_ID JOIN REF_PROFESSION_LOS rpl WITH(NOLOCK) ON rpl.PROFESSION_CODE = acpjd.MR_PROFESSION_CODE, (SELECT ATTR_VALUE, ac.APP_ID FROM APP_CUST_ATTR_CONTENT acat JOIN APP_CUST ac WITH(NOLOCK) ON acat.APP_CUST_ID = ac.APP_CUST_ID JOIN app a WITH(NOLOCK) ON ac.APP_ID = a.APP_ID WHERE APP_NO = '"+ appno +"' AND IS_SHAREHOLDER = 1 AND acat.REF_ATTR_CODE = 'AUTH_AML') as SUBQ where ac.APP_ID = SUBQ.APP_ID AND MR_CUST_ADDR_TYPE_CODE = 'LEGAL' AND accMS.MR_SHRHOLDER_TYPE_CODE = 'PERSONAL' AND CUST_NAME = '"+ name +"' AND attr.REF_ATTR_CODE = 'AML_CUST_DEPARTMENT' AND PROFESSION_NAME = '"+ proffesionname +"') as Orig unpivot (value for [Code] in ([ID_TYPE],[CUST_MODEL],[BUILDING_OWNERSHIP],[JOB_POSITION],[DEPARTMENT_AML],[POSITION_SLIK]))as unpiv) as mastername JOIN REF_MASTER_LOS rf WITH(NOLOCK) ON rf.REF_MASTER_Code = mastername.value WHERE rf.IS_ACTIVE = '1') AS ref PIVOT (MAX(ref.REF_MASTER_NAME) for [Code] in ([ID_TYPE],[CUST_MODEL],[BUILDING_OWNERSHIP],[JOB_POSITION],[DEPARTMENT_AML],[POSITION_SLIK])) as piv"), {  row ->
			println("TEST2")
			ResultSetMetaData rsmd = row.getMetaData()
			colmcount = rsmd.getColumnCount()

			for(i = 0 ; i < colmcount ; i++){
				println(row[i])
				MSdata = (row[i])
				listcustomerdata.add(MSdata)
			}
		})
		println("TEST")
		return listcustomerdata
	}

	//get tab referantor data store db
	@Keyword
	public NAP2TabReferantorStoreDB (Sql instance, String appno, String name){
		String referantordata
		ArrayList <String> listreferantor = new ArrayList<String>()
		instance.eachRow(("SELECT CASE WHEN MR_REFERANTOR_TYPE = 'AGENCY_PERSONAL' OR MR_REFERANTOR_TYPE = 'AGENCY_COMPANY' THEN 'AGENCY' WHEN MR_REFERANTOR_TYPE = 'CUSTOMER_PERSONAL' OR MR_REFERANTOR_TYPE = 'CUSTOMER_COMPANY' THEN 'CUSTOMER' ELSE 'MULTIFINANCE EMPLOYEE' END, ar.REFERANTOR_NAME, d.BANK_NAME, ar.BANK_ACC_NAME, ar.BANK_ACC_NO, ar.MR_TAX_CALC_METHOD FROM APP_CUST ac WITH(NOLOCK) JOIN APP a WITH(NOLOCK) ON ac.APP_ID = a.APP_ID JOIN APP_REFERANTOR ar WITH(NOLOCK) ON a.APP_ID = ar.APP_ID JOIN REF_BANK_LOS d WITH(NOLOCK) ON ar.REF_BANK_CODE = d.BANK_CODE WHERE APP_NO = '"+ appno +"' AND CUST_NAME = '"+ name +"' order by ar.APP_REFERANTOR_ID"), {  row ->

			ResultSetMetaData rsmd = row.getMetaData()
			colmcount = rsmd.getColumnCount()


			for(i = 0 ; i < colmcount ; i++){
				referantordata = (row[i])
				listreferantor.add(referantordata)
			}

		})
		return listreferantor
	}

	//get tab application data store db
	@Keyword
	public NAP2TabApplicationStoreDB (Sql instance, String appno, String name){
		String applicationdata
		ArrayList<String> listapplicationdata = new ArrayList<>()
		instance.eachRow(("SELECT ISNULL([APPSRCNAME],'') AS APPSOURCE, ISNULL([FIRST_INST_TYPE],'') AS FIRST_INST_TYPE, ISNULL([PAYMENTFREQ],'') AS PAYMENT_FREQ, ISNULL([TENOR],'') AS TENOR, ISNULL([DPSRCPAYMENT],'') AS DP_SRC_PAYMENT, ISNULL([INTRSTTYPE],'') AS INTEREST_TYPE, ISNULL([FLOATINGPERIOD],'') AS FLOATING_PERIOD, ISNULL([INST_SCHM],'') AS INST_SCHM, ISNULL([WOP],'') AS WAY_OF_PAYMENT, ISNULL([CUST_NOTIF_OPT],'') AS CUST_NOTIF, ISNULL([CSP_USL_AML],'') AS CSP_USL_AML, ISNULL([ADDR],'') AS ADDR, ISNULL([RT],'') AS RT, ISNULL([RW],'') AS RW, ISNULL([ZIPCODE],'') AS ZIPCODE, ISNULL([KECAMATAN],'') AS KECAMATAN, ISNULL([KELURAHAN],'') AS KELURAHAN, ISNULL([KOTA],'') AS KOTA, ISNULL([PHN1AREA],'') AS PHN1AREA, ISNULL([PHN1],'') AS PHN1, ISNULL([PHN1EXT],'') AS PHN1EXT, ISNULL([PHN2AREA],'') AS PHN2AREA, ISNULL([PHN2],'') AS PHN2, ISNULL([PHN2EXT],'') AS PHN2EXT, ISNULL([PHN3AREA],'') AS PHN3AREA, ISNULL([PHN3],'') AS PHN3, ISNULL([PHN3EXT],'') AS PHN3EXT, ISNULL([FAXAREA],'') AS FAXAREA, ISNULL([FAX],'') AS FAX, ISNULL([CHARACTERISTIC_OF_CREDIT],'') AS COC, ISNULL([PREVAGGRMT],'') AS PREV_AGRMNT, ISNULL([WAY_OF_RESTRUCTURE],'') AS WOR, ISNULL([SLIKECO],'') AS SLIK_ECO FROM (SELECT [APPSRCNAME], [PAYMENTFREQ], [TENOR], [FLOATINGPERIOD], [ADDR], [RT], [RW], [ZIPCODE], [KECAMATAN], [KELURAHAN], [KOTA], [PHN1AREA], [PHN1], [PHN1EXT], [PHN2AREA], [PHN2], [PHN2EXT], [PHN3AREA], [PHN3], [PHN3EXT], [FAXAREA], [FAX], [PREVAGGRMT], [SLIKECO],  mastername.Code, REF_MASTER_NAME FROM ( select [APPSRCNAME], [PAYMENTFREQ], [TENOR], [FLOATINGPERIOD], [ADDR], [RT], [RW], [ZIPCODE], [KECAMATAN], [KELURAHAN], [KOTA], [PHN1AREA], [PHN1], [PHN1EXT], [PHN2AREA], [PHN2], [PHN2EXT], [PHN3AREA], [PHN3], [PHN3EXT], [FAXAREA], [FAX], [PREVAGGRMT], [SLIKECO], [Code], value FROM ( SELECT ras.APP_SRC_NAME AS [APPSRCNAME], MR_FIRST_INST_TYPE_CODE AS 'FIRST_INST_TYPE', PAY_FREQ_CODE AS [PAYMENTFREQ], TENOR AS [TENOR], MR_DP_SRC_PAYMENT_CODE AS 'DPSRCPAYMENT', CAST(acpfd.INTEREST_TYPE as nvarchar(50)) AS 'INTRSTTYPE', FLOATING_PERIOD_CODE AS [FLOATINGPERIOD], MR_INST_SCHEME_CODE AS 'INST_SCHM', MR_WOP_CODE AS 'WOP', MR_CUST_NOTIFY_OPT_CODE AS 'CUST_NOTIF_OPT', MR_INST_SRC_PAYMENT_CODE AS 'CSP_USL_AML', aca.ADDR AS [ADDR], aca.AREA_CODE_3 AS [RT], aca.AREA_CODE_4 AS [RW], aca.ZIPCODE AS [ZIPCODE], aca.AREA_CODE_2 AS [KECAMATAN], aca.AREA_CODE_1 AS [KELURAHAN], aca.CITY AS [KOTA], aca.PHN_AREA_1 AS [PHN1AREA], aca.PHN_1 AS [PHN1], aca.PHN_EXT_1 AS [PHN1EXT], aca.PHN_AREA_2 AS [PHN2AREA], aca.PHN_2 AS [PHN2], aca.PHN_EXT_2 AS [PHN2EXT] , aca.PHN_AREA_3 AS [PHN3AREA], aca.PHN_3 AS [PHN3], aca.PHN_EXT_3 AS [PHN3EXT], aca.FAX_AREA AS [FAXAREA], aca.FAX AS [FAX], MR_CHARACTERISTIC_OF_CREDIT_CODE AS 'CHARACTERISTIC_OF_CREDIT', agr.PREV_AGRMNT_NO AS [PREVAGGRMT], MR_WAY_OF_RESTRUCTURE_CODE AS 'WAY_OF_RESTRUCTURE', agr.MR_SLIK_SEC_ECO_CODE AS [SLIKECO] FROM APP a WITH(NOLOCK) JOIN APP_CUST ac WITH(NOLOCK) ON a.APP_ID = ac.APP_ID JOIN APP_CUST_ADDR aca WITH(NOLOCK) ON ac.APP_CUST_ID = aca.APP_CUST_ID JOIN APP_GOVERMENT_REGULATION agr WITH(NOLOCK) ON agr.APP_ID = a.APP_ID JOIN APP_FIN_DATA acpfd WITH(NOLOCK) ON acpfd.APP_ID = ac.APP_ID JOIN REF_APP_SRC ras WITH(NOLOCK) ON ras.APP_SRC_CODE = a.MR_APP_SOURCE_CODE WHERE APP_NO = '"+appno+"' AND aca.MR_CUST_ADDR_TYPE_CODE = 'MAILING' AND CUST_NAME = '"+name+"') as Orig unpivot (value for [Code] in ([FIRST_INST_TYPE],[WOP],[DPSRCPAYMENT],[INTRSTTYPE],[INST_SCHM],[CUST_NOTIF_OPT],[CSP_USL_AML],[CHARACTERISTIC_OF_CREDIT],[WAY_OF_RESTRUCTURE]) )as unpiv) as mastername JOIN REF_MASTER_LOS rf WITH(NOLOCK) ON rf.REF_MASTER_Code = mastername.value WHERE rf.IS_ACTIVE = '1' AND rf.REF_MASTER_TYPE_CODE IN ('FIRST_INST_TYPE','WOP','INTRSTTYPE','INST_SCHM','CUST_NOTIF_OPT','CSP_USL_AML','CHARACTERISTIC_OF_CREDIT','WAY_OF_RESTRUCTURE')) AS ref PIVOT(MAX(ref.REF_MASTER_NAME) for [Code] in ([FIRST_INST_TYPE],[WOP],[DPSRCPAYMENT],[INTRSTTYPE],[INST_SCHM],[CUST_NOTIF_OPT],[CSP_USL_AML],[CHARACTERISTIC_OF_CREDIT],[WAY_OF_RESTRUCTURE])) as piv"), {  row ->

			ResultSetMetaData rsmd = row.getMetaData()
			colmcount = rsmd.getColumnCount()


			for(i = 0 ; i < colmcount ; i++){
				applicationdata = (row[i])
				listapplicationdata.add(applicationdata)
			}
		})
		return listapplicationdata
	}

	//get tab application bank acc data store db
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

	//get tab application attribute data store db
	@Keyword
	public NAP2TabApplicationAttrStoreDB (Sql instance, String appno){
		String applicationdata
		ArrayList <String> listapplicationattr = new ArrayList<>()
		instance.eachRow(("SELECT ISNULL(ATTR_VALUE,'') AS APP_ATTR_VAL FROM APP_ATTR_CONTENT aac WITH(NOLOCK) JOIN APP a WITH(NOLOCK) ON a.APP_ID = aac.APP_ID WHERE APP_NO = '"+ appno +"'"), {  row ->

			ResultSetMetaData rsmd = row.getMetaData()
			colmcount = rsmd.getColumnCount()


			for(i = 0 ; i < colmcount ; i++){
				applicationdata = (row[i])
				listapplicationattr.add(applicationdata)
			}

		})
		return listapplicationattr
	}

	//get tab asset supplier info data store db
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

	//get tab asset data store db
	@Keyword
	public NAP2TabAssetStoreDB (Sql instance, String appno){
		String assetinfo
		ArrayList<String> listassetinfo = new ArrayList<>()
		instance.eachRow(("SELECT ISNULL([ASSETCODE], '') AS ASSETCODE, ISNULL([ASSETCONDITION], '') AS ASSETCONDITION, ISNULL([ASSETPRICE], '') AS ASSETPRICE, ISNULL([ASSET_USAGE], '') AS ASSETUSAGE, ISNULL([ASSETCOLOR], '') AS ASSETCOLOR, ISNULL([BPKB], '') AS BPKB, ISNULL([BPKBISSUEDATE], '') AS BPKB_DATE_ISSUE, ISNULL([ASSETNOTES], '') AS NOTES, ISNULL([MANYEAR], '') AS MANU_YEAR, ISNULL([DPPERCENT], '') AS DPPRCNT, ISNULL([DPAMOUNT], '') AS DPAMT, ISNULL([SERIALNO1], '') AS SERIAL1, ISNULL([SERIALNO2], '') AS SERIAL2, ISNULL([SERIALNO3], '') AS SERIAL3, ISNULL([SERIALNO4], '') AS SERIAL4, ISNULL([SERIALNO5], '') AS SERIAL5, ISNULL([REGION1], '') AS REGION, ISNULL([USERNAME], '') AS USERNAME, ISNULL( [CUST_PERSONAL_RELATIONSHIP], '') AS RELATION_PERSONAL, ISNULL([OWNERTYPE], '') AS OWNERTYPE, ISNULL([OWNERNAME], '') AS OWNERNAME, ISNULL([OWNERRELATIONSHIP], '') AS OWNER_RELATION, CASE WHEN OWNERTYPE = 'PERSONAL' THEN PROFNAME WHEN OWNERTYPE = 'COMPANY' THEN OWNERPROF END AS OWNER_PROF, ISNULL([ID_TYPE], '') AS IDTYPE, ISNULL([OWNERID], '') AS IDONWER, ISNULL([OWNERMOBILE], '') AS OWNER_MOBILE, ISNULL([OWNERADDR], '') AS OWNERADDR, ISNULL([OWNERRT], '') AS OWNERRT, ISNULL([OWNERRW], '') AS OWNERRW, ISNULL([OWNERZIPCODE], '') AS OWNERZIPCODE, ISNULL([OWNERKECAMATAN], '') AS OWNERKECAMATAN, ISNULL([OWNERKELURAHAN], '') AS OWNERKELURAHAN, ISNULL([OWNERKOTA], '') AS OWNERKOTA, ISNULL([LOCATIONADDR], '') AS LOCADDR, ISNULL([LOCATIONRT], '') AS LOCRT, ISNULL([LOCATIONRW], '') AS LOCRW, ISNULL([LOCATIONZIPCODE], '') AS LOCZIPCODE, ISNULL([LOCATIONKECAMATAN], '') AS LOCKECAMATAN, ISNULL([LOCATIONKELURAHAN], '') AS LOCKELURAHAN, ISNULL([LOCATIONKOTA], '') AS LOCKOTA FROM( SELECT [ASSETCODE], [ASSETCONDITION], [ASSETPRICE], [ASSETCOLOR], [BPKB], [BPKBISSUEDATE], [ASSETNOTES], [MANYEAR], [DPPERCENT], [DPAMOUNT], [SERIALNO1], [SERIALNO2], [SERIALNO3], [SERIALNO4], [SERIALNO5], [REGION1], [USERNAME], [OWNERTYPE], [OWNERNAME], [OWNERPROF], [PROFNAME], [OWNERID], [OWNERMOBILE], [OWNERADDR], [OWNERRT], [OWNERRW], [OWNERZIPCODE], [OWNERKECAMATAN], [OWNERKELURAHAN], [OWNERKOTA], [LOCATIONADDR], [LOCATIONRT], [LOCATIONRW], [LOCATIONZIPCODE], [LOCATIONKECAMATAN], [LOCATIONKELURAHAN], [LOCATIONKOTA], mastername.Code, REF_MASTER_NAME FROM ( select [ASSETCODE], [ASSETCONDITION], [ASSETPRICE], [ASSETCOLOR], [BPKB], [BPKBISSUEDATE], [ASSETNOTES], [MANYEAR], [DPPERCENT], [DPAMOUNT], [SERIALNO1], [SERIALNO2], [SERIALNO3], [SERIALNO4], [SERIALNO5], [REGION1], [USERNAME], [OWNERTYPE], [OWNERNAME], [OWNERPROF], [PROFNAME], [OWNERID], [OWNERMOBILE], [OWNERADDR], [OWNERRT], [OWNERRW], [OWNERZIPCODE], [OWNERKECAMATAN], [OWNERKELURAHAN], [OWNERKOTA], [LOCATIONADDR], [LOCATIONRT], [LOCATIONRW], [LOCATIONZIPCODE], [LOCATIONKECAMATAN], [LOCATIONKELURAHAN], [LOCATIONKOTA], [Code], value FROM ( SELECT aa.FULL_ASSET_CODE AS [ASSETCODE], MR_ASSET_CONDITION_CODE AS [ASSETCONDITION], CAST(ASSET_PRICE_AMT as INT) AS [ASSETPRICE], aa.MR_ASSET_USAGE_CODE AS 'ASSET_USAGE', aa.COLOR AS [ASSETCOLOR], aa.TAX_CITY_ISSUER AS [BPKB], FORMAT(aa.TAX_ISSUE_DT, 'MM/dd/yyy') AS [BPKBISSUEDATE], aa.ASSET_NOTES AS [ASSETNOTES], aa.MANUFACTURING_YEAR AS [MANYEAR], CONVERT(INT, DOWN_PAYMENT_PRCNT) AS [DPPERCENT], CONVERT(INT, DOWN_PAYMENT_AMT) AS [DPAMOUNT], aa.SERIAL_NO_1 AS [SERIALNO1], aa.SERIAL_NO_2 AS [SERIALNO2], aa.SERIAL_NO_3 AS [SERIALNO3], aa.SERIAL_NO_4 AS [SERIALNO4], aa.SERIAL_NO_5 AS [SERIALNO5], aattr.ATTR_VALUE AS [REGION1], acr.USER_NAME AS [USERNAME], acr.MR_USER_RELATIONSHIP_CODE AS 'CUST_PERSONAL_RELATIONSHIP', acr.MR_OWNER_TYPE_CODE AS [OWNERTYPE], acr.OWNER_NAME AS [OWNERNAME], acr.MR_OWNER_RELATIONSHIP_CODE AS [OWNERRELATIONSHIP], acr.OWNER_PROFESSION_CODE AS [OWNERPROF], rpl.PROFESSION_NAME AS [PROFNAME], acr.MR_ID_TYPE_CODE AS 'ID_TYPE', acr.OWNER_ID_NO AS [OWNERID], acr.OWNER_MOBILE_PHN_NO AS [OWNERMOBILE], acr.OWNER_ADDR AS [OWNERADDR], acr.OWNER_AREA_CODE_4 AS [OWNERRT], acr.OWNER_AREA_CODE_3 AS [OWNERRW], acr.OWNER_ZIPCODE AS [OWNERZIPCODE], acr.OWNER_AREA_CODE_2 AS [OWNERKECAMATAN], acr.OWNER_AREA_CODE_1 AS [OWNERKELURAHAN], acr.OWNER_CITY AS [OWNERKOTA], acr.LOCATION_ADDR AS [LOCATIONADDR], acr.LOCATION_AREA_CODE_4 AS [LOCATIONRT], acr.LOCATION_AREA_CODE_3 AS [LOCATIONRW], acr.LOCATION_ZIPCODE AS [LOCATIONZIPCODE], acr.LOCATION_AREA_CODE_2 AS [LOCATIONKECAMATAN], acr.LOCATION_AREA_CODE_1 AS [LOCATIONKELURAHAN], acr.LOCATION_CITY AS [LOCATIONKOTA] FROM APP_ASSET aa WITH(NOLOCK) JOIN APP a WITH(NOLOCK) ON aa.APP_ID = a.APP_ID JOIN APP_ASSET_ATTR aattr WITH(NOLOCK) ON aa.APP_ASSET_ID = aattr.APP_ASSET_ID JOIN APP_COLLATERAL col WITH(NOLOCK) ON aa.APP_ID = col.APP_ID JOIN APP_COLLATERAL_REGISTRATION acr WITH(NOLOCK) ON col.APP_COLLATERAL_ID = acr.APP_COLLATERAL_ID LEFT JOIN REF_PROFESSION_LOS rpl with(nolock) on rpl.PROFESSION_CODE = acr.OWNER_PROFESSION_CODE WHERE APP_NO = '"+ appno +"' AND aattr.ASSET_ATTR_NAME = 'ASSET REGION') as Orig unpivot (value for [Code] in ([ASSET_USAGE], [CUST_PERSONAL_RELATIONSHIP], [ID_TYPE], [OWNERRELATIONSHIP])) as unpiv) as mastername JOIN REF_MASTER_LOS rf WITH(NOLOCK) ON rf.REF_MASTER_Code = mastername.value WHERE rf.IS_ACTIVE = '1' AND rf.REF_MASTER_TYPE_CODE IN('CUST_PERSONAL_RELATIONSHIP', 'CUST_COMPANY_RELATIONSHIP','ID_TYPE','ASSET_USAGE')) AS ref PIVOT (MAX(ref.REF_MASTER_NAME) for [Code] in ([ASSET_USAGE], [CUST_PERSONAL_RELATIONSHIP], [ID_TYPE], [OWNERRELATIONSHIP])) as piv"), {  row ->

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
	public NAP2AssetDocumentStoreDB(Sql instance, String appno){
		String assetDocData
		ArrayList <String> listAssetDoc = new ArrayList<String>()
		instance.eachRow(("SELECT CASE WHEN IS_RECEIVED = 1 THEN 'YES' WHEN IS_RECEIVED = 0 THEN 'NO' END AS IS_RECEIVED, DOC_NO, ISNULL(FORMAT(EXPIRED_DT,'MM/dd/yyyy'),'') AS EXPIRED_DT, DOC_NOTES FROM app_collateral_doc acd with(nolock) join APP_COLLATERAL ac with(nolock) on acd.APP_COLLATERAL_ID = ac.APP_COLLATERAL_ID join app a with(nolock) on ac.app_id = a.app_id where app_no = '"+appno+"'"), {  row ->

			ResultSetMetaData rsmd = row.getMetaData()
			colmcount = rsmd.getColumnCount()


			for(i = 0 ; i < colmcount ; i++){
				assetDocData = (row[i])
				listAssetDoc.add(assetDocData)
			}
		})
		return listAssetDoc
	}

	//get tab asset accessories data store db
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

	//get tab insurance data store db
	@Keyword
	public NAP2InsuranceCustStoreDB (Sql instance, String appno){
		String insurancedata
		ArrayList<String> insurancelist = new ArrayList<>()
		instance.eachRow(("SELECT ISNULL(aio.CUST_INSCO_BRANCH_NAME,'') AS INSCO_BRANCH_NAME , ISNULL(CONVERT(INT , aio.CUST_CVG_AMT),'') AS CUST_CVG_AMT , ISNULL(aio.INS_POLICY_NO,'') AS POLICY_NO, ISNULL(aio.INS_POLICY_NAME,'') AS POLICY_NAME, ISNULL(FORMAT(aio.CUST_COVER_START_DT, 'MM/dd/yyyy'),'') AS CUST_COVER_START_DT, ISNULL(FORMAT(aio.END_DT, 'MM/dd/yyyy'),'') AS END_DT, ISNULL(aio.CUST_NOTES,'') AS NOTES FROM APP_INS ai WITH(NOLOCK) JOIN APP a WITH(NOLOCK) ON ai.APP_ID = a.APP_ID JOIN APP_INS_OBJ aio WITH(NOLOCK) ON a.APP_ID = aio.APP_ID WHERE APP_NO = '"+ appno +"'"), {  row ->

			ResultSetMetaData rsmd = row.getMetaData()
			colmcount = rsmd.getColumnCount()


			for(i = 0 ; i < colmcount ; i++){
				insurancedata = (row[i])
				insurancelist.add(insurancedata)
			}
		})
		return insurancelist
	}

	//get tab insurance custMF data store db
	@Keyword
	public NAP2InsuranceCustMFStoreDB (Sql instance, String appno){
		String insurancedata
		ArrayList<String> insurancelist = new ArrayList<>()
		instance.eachRow(("SELECT ISNULL(aio.CUST_INSCO_BRANCH_NAME,'') AS INSCO_BRANCH_NAME, ISNULL(CONVERT(INT , aio.CUST_CVG_AMT),'') AS CUST_CVG_AMT, ISNULL(aio.INS_POLICY_NO,'') AS POLICY_NO, ISNULL(aio.INS_POLICY_NAME,'') AS POLICY_NAME, ISNULL(FORMAT(aio.CUST_COVER_START_DT, 'MM/dd/yyyy'),'') AS CUST_CVR_START_DT, ISNULL(FORMAT(aio.START_DT, 'MM/dd/yyyy'),'') AS START_DT, ISNULL(aio.CUST_NOTES,'') AS NOTES, ISNULL(FORMAT(aio.END_DT, 'MM/dd/yyyy'),'') AS END_DT FROM APP_INS ai WITH(NOLOCK) JOIN APP a WITH(NOLOCK) ON ai.APP_ID = a.APP_ID JOIN APP_INS_OBJ aio WITH(NOLOCK) ON a.APP_ID = aio.APP_ID WHERE APP_NO = '"+ appno +"'"), {  row ->

			ResultSetMetaData rsmd = row.getMetaData()
			colmcount = rsmd.getColumnCount()


			for(i = 0 ; i < colmcount ; i++){
				insurancedata = (row[i])
				insurancelist.add(insurancedata)
			}
		})
		return insurancelist
	}

	//get tab insurance MF data store db
	@Keyword
	public NAP2InsuranceMFStoreDB (Sql instance, String appno){
		String insurancedata
		ArrayList<String> insurancelist = new ArrayList<>()
		instance.eachRow(("SELECT ISNULL(aio.INS_ASSET_REGION,'') AS ASSET_REG , ISNULL(CONVERT(INT , aio.CVG_AMT),'') AS CVG_AMT, ISNULL(rml.REF_MASTER_NAME,'') AS REF1, ISNULL(SUBQ.REF_MASTER_NAME,'') AS REF2, ISNULL(aio.INSCO_BRANCH_NAME,'') AS INSCO_BRANCH_NAME, ISNULL(aio.NOTES,'') AS NOTES, ISNULL(INS_LENGTH,'') AS INS_LENGTH , ISNULL(CAST(CUST_ADMIN_FEE_AMT as int),'') AS ADMIN_FEE_CUST, ISNULL(CAST(CUST_STAMP_DUTY_FEE as INT),'') AS STAMP_DUTY_FEE_CUST FROM APP_INS ai WITH(NOLOCK) JOIN APP a WITH(NOLOCK) ON ai.APP_ID = a.APP_ID JOIN APP_INS_OBJ aio WITH(NOLOCK) ON a.APP_ID = aio.APP_ID JOIN REF_MASTER_LOS rml WITH(NOLOCK) ON rml.REF_MASTER_CODE = aio.INS_ASSET_COVER_PERIOD, (SELECT rml.REF_MASTER_NAME, APP_NO FROM APP_INS_OBJ aio WITH(NOLOCK) JOIN APP a WITH(NOLOCK) ON aio.APP_ID = a.APP_ID JOIN REF_MASTER_LOS rml WITH(NOLOCK) ON rml.REF_MASTER_CODE = aio.PAY_PERIOD_TO_INSCO) as SUBQ WHERE a.APP_NO = '"+ appno +"' AND a.APP_NO = SUBQ.APP_NO"), {  row ->

			ResultSetMetaData rsmd = row.getMetaData()
			colmcount = rsmd.getColumnCount()


			for(i = 0 ; i < colmcount ; i++){
				insurancedata = (row[i])
				insurancelist.add(insurancedata)
			}

		})
		return insurancelist
	}


	//get NAP2 insurance Main CVG Store DB
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

	//get NAP2 insurance Add CVG Store DB
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

	//get NAP2 insurance Main Premi Rate Store DB
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

	//get NAP2 insurance Multi Main CVG Store DB
	@Keyword
	public NAP2InsuranceMultiMainCVGtoreDB (Sql instance, String appno){
		String insurancedata
		ArrayList<String> insurancelist = new ArrayList<>()
		instance.eachRow(("SELECT ISNULL([YEAR],'') AS YEAR, CASE WHEN IS_CAPITALIZED = 1 THEN 'Yes' WHEN IS_CAPITALIZED = 0 THEN 'No' END AS IS_CPTLZ, ISNULL([PAID],'') AS PAIDBY,  ISNULL([SUMINSURED],'') AS SUMINSURED, ISNULL([MAINCVG],'') AS MAINCVG FROM (SELECT [YEAR], test.Code, REF_MASTER_NAME, IS_CAPITALIZED,  [SUMINSURED] FROM (select [YEAR], [Code], value, IS_CAPITALIZED,  [SUMINSURED] from (SELECT YEAR_NO AS [YEAR], IS_CAPITALIZED , MR_INS_PAID_BY_CODE as [PAID], FORMAT(aimc.SUM_INSURED_PRCNT, 'N0') as [SUMINSURED], MR_MAIN_CVG_TYPE_CODE as [MAINCVG] FROM APP_INS_MAIN_CVG aimc JOIN APP_INS_OBJ aio WITH(NOLOCK) ON aio.APP_INS_OBJ_ID = aimc.APP_INS_OBJ_ID JOIN APP a WITH(NOLOCK) ON a.APP_ID = aio.APP_ID WHERE a.APP_NO = '"+ appno +"') as Orig unpivot(value for [Code] in ([PAID],[MAINCVG]) )as unpiv) as test JOIN REF_MASTER_LOS rf on rf.REF_MASTER_Code = test.value) AS A PIVOT (MAX(A.REF_MASTER_NAME) for [Code] in ([PAID],[MAINCVG])) as piv"), {  row ->

			ResultSetMetaData rsmd = row.getMetaData()
			colmcount = rsmd.getColumnCount()


			for(i = 0 ; i < colmcount ; i++){
				insurancedata = (row[i])
				insurancelist.add(insurancedata)
			}
		})
		return insurancelist
	}

	//get NAP2 insurance Partial capitalized Store DB
	@Keyword
	public NAP2InsurancePartialCaptilizeStoreDB (Sql instance, String appno){
		String insurancedata
		ArrayList<String> insurancelist = new ArrayList<>()
		instance.eachRow(("SELECT INS_CPLTZ_AMT FROM APP_INS_MAIN_CVG aimc JOIN APP_INS_OBJ aio WITH(NOLOCK) ON aio.APP_INS_OBJ_ID = aimc.APP_INS_OBJ_ID JOIN APP a WITH(NOLOCK) ON a.APP_ID = aio.APP_ID WHERE a.APP_NO = '"+ appno +"'"), {  row ->

			ResultSetMetaData rsmd = row.getMetaData()
			colmcount = rsmd.getColumnCount()


			for(i = 0 ; i < colmcount ; i++){
				insurancedata = (row[i])
				insurancelist.add(insurancedata)
			}
		})
		return insurancelist
	}

	//get NAP2 insurance multi add cvg Store DB
	@Keyword
	public NAP2InsuranceMultiAddCVGtoreDB (Sql instance, String appno){
		String insurancedata
		ArrayList<String> insurancelist = new ArrayList<>()
		instance.eachRow(("SELECT ISNULL(YEAR_NO,'') AS YEARNO,ISNULL(rml.REF_MASTER_NAME,'') AS ADD_CVG, ISNULL(CASE WHEN(rml.REF_MASTER_NAME = 'TPL' OR rml.REF_MASTER_NAME = 'Tanggung Jawab Hukum Terhadap Penumpang' OR rml.REF_MASTER_NAME = 'Kecelakaan Diri Untuk Penumpang') THEN CONVERT(VARCHAR,CUST_ADD_PREMI_AMT) ELSE CONVERT(VARCHAR,CUST_ADD_PREMI_RATE) END,'') AS RATE FROM APP_INS_MAIN_CVG aimc JOIN APP_INS_OBJ aio WITH(NOLOCK) ON aio.APP_INS_OBJ_ID = aimc.APP_INS_OBJ_ID JOIN APP a WITH(NOLOCK) ON a.APP_ID = aio.APP_ID JOIN APP_INS_ADD_CVG aiac WITH(NOLOCK) ON aiac.APP_INS_MAIN_CVG_ID = aimc.APP_INS_MAIN_CVG_ID JOIN REF_MASTER_LOS rml WITH(NOLOCK) ON rml.REF_MASTER_CODE = aiac.MR_ADD_CVG_TYPE_CODE WHERE APP_NO = '"+ appno +"' ORDER BY YEAR_NO,APP_INS_ADD_CVG_ID"), {  row ->
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

	//get NAP2 insurance CVG Store DB
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

	//get NAP2 life insurance Store DB
	@Keyword
	public NAP2LifeInsuranceStoreDB (Sql instance, String appno){
		String lifeinsurancedata
		ArrayList <String> listlifeinsurance = new ArrayList<String>()
		instance.eachRow(("SELECT ISNULL(alih.LIFE_INSCO_BRANCH_NAME,'') AS LIFE_INSCO_BRANCH_NAME, ISNULL(rml.REF_MASTER_NAME,'') AS PAID_METHOD, ISNULL(100 - CONVERT(int, alih.PAID_IN_ADV_PRCNT),'') AS PAID_IN_ADV_PERCENT, ISNULL(NEW_COVER_NOTES,'') AS NOTES, Case when IS_CUST_COVER = 1 Then 'Yes' When IS_CUST_COVER = 0 Then 'No' END AS IS_CUST_CVR, Case when IS_GUARANTOR_COVER = 1 Then 'Yes' When IS_GUARANTOR_COVER = 0 Then 'No' END AS IS_GUAR_CVR, Case when IS_SPOUSE_COVER = 1 Then 'Yes' When IS_SPOUSE_COVER = 0 Then 'No' END AS IS_SPOUSE_CVR, ISNULL(CONVERT(INT, CUST_ADMIN_FEE_AMT),'') AS ADMIN_FEE_CUST FROM APP_LIFE_INS_D alid WITH(NOLOCK) JOIN APP_LIFE_INS_H alih WITH(NOLOCK) ON alid.APP_LIFE_INS_H_ID = alih.APP_LIFE_INS_H_ID JOIN APP a WITH(NOLOCK) ON alih.APP_ID = a.APP_ID JOIN REF_MASTER_LOS rml WITH(NOLOCK) ON rml.REF_MASTER_CODE = alih.MR_LIFE_INS_PAID_METHOD_CODE WHERE APP_NO = '"+ appno +"'"), {  row ->

			ResultSetMetaData rsmd = row.getMetaData()
			colmcount = rsmd.getColumnCount()


			for(i = 0 ; i < colmcount ; i++){
				lifeinsurancedata = (row[i])
				listlifeinsurance.add(lifeinsurancedata)
			}
		})
		return listlifeinsurance
	}

	//get NAP2 financial Store DB
	@Keyword
	public NAP2FinancialStoreDB (Sql instance, String appno){
		String financialdata
		ArrayList <String> listfinancialdata = new ArrayList<String>()
		instance.eachRow(("SELECT ISNULL(CONVERT(INT, TOTAL_FEE_AMT),'') AS TOTAL_FEE, ISNULL(CONVERT(INT, TOTAL_FEE_CPTLZ_AMT),'') AS TOTAL_FEE_CPTLZ, ISNULL(rml.REF_MASTER_NAME,'') AS PROV_FEE_CALC_METHOD, ISNULL(EFFECTIVE_RATE_PRCNT,'') AS EF_RATE, ISNULL(FLAT_RATE_PRCNT,'') AS FLAT_RATE, ISNULL(GRACE_PERIOD,'') AS GRACE_PERIOD, ISNULL(afd.MR_GRACE_PERIOD_TYPE_CODE,'') AS GRACE_PERIOD_TYPE, ISNULL(CONVERT(INT,TDP_PAID_COY_AMT),'') AS TDP_PAID_COY_AMT FROM APP_FIN_DATA afd WITH(NOLOCK) JOIN APP a WITH(NOLOCK) ON a.APP_ID = afd.APP_ID JOIN REF_MASTER_LOS rml ON rml.REF_MASTER_CODE = afd.MR_PROVISION_FEE_CALC_METHOD_CODE WHERE APP_NO = '"+ appno +"'"), {  row ->

			ResultSetMetaData rsmd = row.getMetaData()
			colmcount = rsmd.getColumnCount()


			for(i = 0 ; i < colmcount ; i++){
				financialdata = (row[i])
				listfinancialdata.add(financialdata)
			}
		})
		return listfinancialdata
	}

	//get NAP2 financial fee Store DB
	@Keyword
	public NAP2FinancialFeeStoreDB (Sql instance, String appno){
		String financialfeedata
		ArrayList <String> listfinancialfeedata = new ArrayList<String>()
		instance.eachRow(("SELECT ISNULL(CONVERT(INT,app_fee_amt),'') AS APP_FEE_AMT, ISNULL(app_fee_prcnt, '') AS APP_FEE_PERCENT,ISNULL(CASE WHEN is_cptlz = 1 THEN 'Yes' ELSE 'No' END,'') AS IS_CPTLZ, ISNULL(CONVERT(INT,cptlz_amt),'') AS CPTLZ_AMT FROM APP_FEE af with(nolock) join app a with(nolock) on af.app_id = a.app_id where app_no = '"+appno+"'"), {  row ->

			ResultSetMetaData rsmd = row.getMetaData()
			colmcount = rsmd.getColumnCount()


			for(i = 0 ; i < colmcount ; i++){
				financialfeedata = (row[i])
				listfinancialfeedata.add(financialfeedata)
			}
		})
		return listfinancialfeedata
	}

	//get NAP2 subsidy Store DB
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

	//get NAP2 Term condition Store DB
	@Keyword
	public NAP2TermConditionStoreDB (Sql instance, String appno){
		String termandcondition
		ArrayList <String> listTC = new ArrayList<String>()
		instance.eachRow(("SELECT ISNULL(rtl.TC_NAME,'') AS TCNAME, ISNULL(at.IS_MANDATORY,'') AS ISMANDATORY, ISNULL(IS_CHECKED,'') AS ISCHECKED, ISNULL(FORMAT(PROMISED_DT, 'MM/dd/yyy'),'') AS PROM_DATE, ISNULL(FORMAT(EXPIRED_DT, 'MM/dd/yyyy'),'') AS EXP_DATE, ISNULL(IS_WAIVED,'') AS ISWAIVED FROM APP_TC at WITH(NOLOCK)JOIN APP a WITH(NOLOCK) ON a.APP_ID = at.APP_ID JOIN REF_TC_LOS rtl WITH(NOLOCK) ON rtl.TC_CODE = at.TC_CODE WHERE APP_NO = '"+ appno +"'"), {  row ->

			ResultSetMetaData rsmd = row.getMetaData()
			colmcount = rsmd.getColumnCount()


			for(i = 0 ; i < colmcount ; i++){
				termandcondition = (row[i])
				listTC.add(termandcondition)
			}
		})
		return listTC
	}

	//get NAP3 commission data Store DB
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
			instance.eachRow(("SELECT COMMISSION_RECIPIENT_REF_NO, MR_COMMISSION_SOURCE_CODE, ISNULL((COMMISSION_AMT/NULLIF(REFUND_AMT, 0))*100,0) AS COMPERCENT, REFUND_AMT FROM APP_COMMISSION_H ach WITH(NOLOCK) JOIN APP_COMMISSION_D acd WITH(NOLOCK) ON acd.APP_COMMISSION_H_ID = ach.APP_COMMISSION_H_ID WHERE APP_id = (Select app_id from app WITH(NOLOCK) where app_no = '"+appno+"') and MR_COMMISSION_RECIPIENT_TYPE_CODE = 'SUPPLIER'"), {  row ->

				if(row[3]==0.00){
					resultSupp.add("-1")
				}
				else{
					resultSupp.add(row[2])
				}
			})
			instance.eachRow(("SELECT COMMISSION_RECIPIENT_REF_NO, MR_COMMISSION_SOURCE_CODE, ISNULL((COMMISSION_AMT/NULLIF(REFUND_AMT, 0))*100,0) AS COMPERCENT, REFUND_AMT FROM APP_COMMISSION_H ach WITH(NOLOCK) JOIN APP_COMMISSION_D acd WITH(NOLOCK) ON acd.APP_COMMISSION_H_ID = ach.APP_COMMISSION_H_ID WHERE APP_id = (Select app_id from app WITH(NOLOCK) where app_no = '"+appno+"') and MR_COMMISSION_RECIPIENT_TYPE_CODE = 'SUPPLIER_EMP'"), {  row ->

				if(row[3]==0.00){
					resultSuppEmp.add("-1")
				}
				else{
					resultSuppEmp.add(row[2])
				}
			})
			instance.eachRow(("SELECT COMMISSION_RECIPIENT_REF_NO, MR_COMMISSION_SOURCE_CODE, ISNULL((COMMISSION_AMT/NULLIF(REFUND_AMT, 0))*100,0) AS COMPERCENT, REFUND_AMT FROM APP_COMMISSION_H ach WITH(NOLOCK) JOIN APP_COMMISSION_D acd WITH(NOLOCK) ON acd.APP_COMMISSION_H_ID = ach.APP_COMMISSION_H_ID WHERE APP_id = (Select app_id from app WITH(NOLOCK) where app_no = '"+appno+"') and MR_COMMISSION_RECIPIENT_TYPE_CODE = 'REFERANTOR'"), {  row ->

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

	//get NAP3 count commission recipient data Store DB
	@Keyword
	public countCommissionRecipientDB(Sql instance, String appno){
		ArrayList<Integer> listcount = new ArrayList<>()
		instance.eachRow(("SELECT 'SUPPLIER' , COUNT(*)  FROM APP_COMMISSION_H WITH(NOLOCK) WHERE APP_id = (Select app_id from app where app_no = '"+appno+"') AND MR_COMMISSION_RECIPIENT_TYPE_CODE = 'SUPPLIER' UNION SELECT 'SUPPLIER_EMP' , COUNT(*)  FROM APP_COMMISSION_H WITH(NOLOCK) WHERE APP_id = (Select app_id from app where app_no = '"+appno+"') AND MR_COMMISSION_RECIPIENT_TYPE_CODE = 'SUPPLIER_EMP' UNION SELECT 'REFERANTOR' , COUNT(*)  FROM APP_COMMISSION_H WHERE APP_id = (Select app_id from app where app_no = '"+appno+"') AND MR_COMMISSION_RECIPIENT_TYPE_CODE = 'REFERANTOR'"), {  row ->
			listcount.add(row[1])
		})
		return listcount
	}

	//get NAP3 count commission source supplier data Store DB
	@Keyword
	public countCommissionSourceSupplierDB(Sql instance, String appno){
		Integer countCS
		instance.eachRow(("SELECT ach.APP_COMMISSION_H_ID, COMMISSION_RECIPIENT_REF_NO, COUNT(*)  FROM APP_COMMISSION_H ach WITH (NOLOCK) JOIN APP_COMMISSION_D acd WITH(NOLOCK) ON acd.APP_COMMISSION_H_ID = ach.APP_COMMISSION_H_ID WHERE APP_id = (Select app_id from app WITH(NOLOCK) where app_no = '"+appno+"') and MR_COMMISSION_RECIPIENT_TYPE_CODE = 'SUPPLIER' GROUP BY COMMISSION_RECIPIENT_REF_NO, ach.APP_COMMISSION_H_ID ORDER BY ach.APP_COMMISSION_H_ID"), {  row ->
			countCS = row[2]
		})
		return countCS
	}

	//get NAP3 count commission source supplier emp data Store DB
	@Keyword
	public countCommissionSourceSupplierEmpDB(Sql instance, String appno){
		ArrayList<Integer> countCS = new ArrayList<>()
		instance.eachRow(("SELECT ach.APP_COMMISSION_H_ID, COMMISSION_RECIPIENT_REF_NO, COUNT(MR_COMMISSION_SOURCE_CODE)  FROM APP_COMMISSION_H ach WITH(NOLOCK) JOIN APP_COMMISSION_D acd WITH(NOLOCK) ON acd.APP_COMMISSION_H_ID = ach.APP_COMMISSION_H_ID WHERE APP_id = (Select app_id from app WITH(NOLOCK) where app_no = '"+appno+"') and MR_COMMISSION_RECIPIENT_TYPE_CODE = 'SUPPLIER_EMP' GROUP BY COMMISSION_RECIPIENT_REF_NO, ach.APP_COMMISSION_H_ID ORDER BY ach.APP_COMMISSION_H_ID"), {  row ->
			println(row[2])
			countCS.add(row[2])
		})
		return countCS
	}

	//get NAP3 count commission source referantor data Store DB
	@Keyword
	public countCommissionSourceReferantorDB(Sql instance, String appno){
		ArrayList<Integer> countCS = new ArrayList<>()
		instance.eachRow(("SELECT ach.APP_COMMISSION_H_ID, COMMISSION_RECIPIENT_REF_NO, COUNT(MR_COMMISSION_SOURCE_CODE)  FROM APP_COMMISSION_H ach WITH(NOLOCK) JOIN APP_COMMISSION_D acd WITH(NOLOCK) ON acd.APP_COMMISSION_H_ID = ach.APP_COMMISSION_H_ID WHERE APP_id = (Select app_id from app WITH(NOLOCK) where app_no = '"+appno+"') and MR_COMMISSION_RECIPIENT_TYPE_CODE = 'REFERANTOR' GROUP BY COMMISSION_RECIPIENT_REF_NO, ach.APP_COMMISSION_H_ID ORDER BY ach.APP_COMMISSION_H_ID"), {  row ->
			countCS.add(row[2])
		})
		return countCS
	}

	//get NAP3 reserved fund data Store DB
	@Keyword
	public NAP3ReservedFundDataStoreDB(Sql instance, String appno){
		ArrayList<String> rsvAmt = new ArrayList<>()
		instance.eachRow(("select reserved_fund_amt from app_reserved_fund WITH(NOLOCK) where app_id = (select app_id from app where app_no='"+appno+"')"), {  row ->
			rsvAmt.add(row[0])
		})
		return rsvAmt
	}

	//get NAP4 customer detail personal Store DB
	@Keyword
	public NAP4CustomerDetailPersonalStoreData (Sql instance, String appno, String name){
		String customerdetail
		ArrayList<String> listcust = new ArrayList<>()
		instance.eachRow(("SELECT ISNULL([Custprefix],'') AS CUST_PREF, ISNULL([no_of_dependent],'') AS NO_OF_DEP, ISNULL([no_of_residence],'') AS NO_OF_RES , ISNULL([family_card_no],'') AS FAM_CARD, Case when [IS_RIP] = 1 Then 'Yes' when [IS_RIP] = 0 Then 'No' End AS IS_RIP, Case when [IS_VIP] = 1 Then 'Yes' when [IS_VIP] = 0 Then 'No' End AS IS_VIP, Case when [IS_AFF_MF] = 1 Then 'Yes' when [IS_AFF_MF] = 0 Then 'No' End AS IS_AFF_MF, ISNULL([SALUTATION],'') AS SALUTATION, ISNULL([NICKNAME],'') AS NICKNAME, ISNULL([Custsuffix],'') AS CUST_SUFFIX, ISNULL([NATIONALITY],'') AS NATIONALITY, ISNULL([Countryname],'') AS COUNTRY, ISNULL([EDUCATION],'') AS EDUCATION, ISNULL([RELIGION],'') AS RELIGION, ISNULL([VIPNOTES],'') AS VIP_NOTES FROM (SELECT [Custprefix],  [no_of_dependent], [no_of_residence], [family_card_no], [IS_RIP], [IS_VIP], [IS_AFF_MF], [NICKNAME], [Custsuffix], [Countryname], [VIPNOTES], mastername.Code, REF_MASTER_NAME FROM (select [Custprefix], [no_of_dependent], [no_of_residence], [family_card_no], [IS_RIP], [IS_VIP], [IS_AFF_MF], [NICKNAME], [Custsuffix], [Countryname], [VIPNOTES], [Code], value FROM (SELECT MR_EDUCATION_CODE AS 'EDUCATION', MR_NATIONALITY_CODE as 'NATIONALITY', MR_RELIGION_CODE as 'RELIGION', MR_SALUTATION_CODE as 'SALUTATION', acp.CUST_PREFIX_NAME as [Custprefix], acp.NO_OF_DEPENDENTS as [no_of_dependent] , acp.NO_OF_RESIDENCE as [no_of_residence], acp.FAMILY_CARD_NO as [family_card_no], acp.IS_REST_IN_PEACE as [IS_RIP], ac.IS_VIP as [IS_VIP], ac.IS_AFFILIATE_WITH_MF as [IS_AFF_MF], acp.NICK_NAME as [NICKNAME], acp.CUST_SUFFIX_NAME as [Custsuffix], rc.COUNTRY_NAME as [Countryname], ac.VIP_NOTES as [VIPNOTES] FROM APP_CUST_PERSONAL acp WITH(NOLOCK) join app_cust ac WITH(NOLOCK) ON ac.app_cust_id = acp.APP_CUST_ID JOIN APP a WITH(NOLOCK) ON ac.APP_ID = a.APP_ID JOIN APP_CUST_GRP acg WITH(NOLOCK) ON acg.APP_CUST_ID = ac.APP_CUST_ID JOIN V_REF_COUNTRY rc WITH(NOLOCK) ON rc.COUNTRY_CODE = acp.NATIONALITY_COUNTRY_CODE WHERE ac.cust_name='"+ name +"' and app_no ='"+ appno +"') as Orig unpivot (value for [Code] in ([EDUCATION],[NATIONALITY],[RELIGION],[SALUTATION]) )as unpiv) as mastername JOIN REF_MASTER_LOS rf WITH(NOLOCK) ON rf.REF_MASTER_Code = mastername.value WHERE rf.IS_ACTIVE = '1') AS ref PIVOT (MAX(ref.REF_MASTER_NAME) for [Code] in ([EDUCATION],[NATIONALITY],[RELIGION],[SALUTATION])) as piv"), {  row ->

			ResultSetMetaData rsmd = row.getMetaData()
			colmcount = rsmd.getColumnCount()


			for(i = 0 ; i < colmcount ; i++){
				customerdetail = (row[i])
				listcust.add(customerdetail)
			}
		})
		return listcust
	}

	//get NAP4 address information personal Store DB
	@Keyword
	public NAP4AddressStoreData (Sql instance, String appno, String name, String addresstype){
		String address
		ArrayList<String> listaddr = new ArrayList<>()
		instance.eachRow(("SELECT ISNULL([CUST_ADDR_TYPE],'') AS CUST_ADDR_TYPE, ISNULL([ADDR],'') AS ADDR , ISNULL([RT],'') AS RT, ISNULL([RW],'') AS RW, ISNULL([ZIPCODE],'') AS ZIPCODE, ISNULL([KECAMATAN],'') AS KECAMATAN, ISNULL([KELURAHAN],'') AS KELURAHAN, ISNULL([KOTA],'') AS KOTA, ISNULL([PHN1AREA],'') AS PHN1AREA, ISNULL([PHN1],'') AS PHN1, ISNULL([PHN1EXT],'') AS PHN1EXT, ISNULL([PHN2AREA],'') AS PHN2AREA, ISNULL([PHN2],'') AS PHN2, ISNULL([PHN2EXT],'') AS PHN2EXT, ISNULL([PHN3AREA],'') AS PHN3AREA, ISNULL([PHN3],'') AS PHN3, ISNULL([PHN3EXT],'') AS PHN3EXT, ISNULL([FAXAREA],'') AS FAXAREA, ISNULL([FAX],'') AS FAX, ISNULL([BUILDING_OWNERSHIP],'') AS OWNERSHIP FROM (SELECT [ADDR], [RT], [RW], [ZIPCODE], [KECAMATAN], [KELURAHAN], [KOTA], [PHN1AREA], [PHN1], [PHN1EXT], [PHN2AREA], [PHN2], [PHN2EXT], [PHN3AREA], [PHN3], [PHN3EXT], [FAXAREA], [FAX], mastername.Code, REF_MASTER_NAME FROM (select [ADDR], [RT], [RW], [ZIPCODE], [KECAMATAN], [KELURAHAN], [KOTA], [PHN1AREA], [PHN1], [PHN1EXT], [PHN2AREA], [PHN2], [PHN2EXT], [PHN3AREA], [PHN3], [PHN3EXT], [FAXAREA], [FAX], [Code], value FROM (SELECT MR_CUST_ADDR_TYPE_CODE AS 'CUST_ADDR_TYPE', aca.ADDR AS [ADDR], aca.AREA_CODE_4 AS [RT], aca.AREA_CODE_3 AS [RW], aca.ZIPCODE AS [ZIPCODE], aca.AREA_CODE_1 AS [KECAMATAN], aca.AREA_CODE_2 AS [KELURAHAN], aca.CITY AS [KOTA], aca.PHN_AREA_1 AS [PHN1AREA], aca.PHN_1 AS [PHN1], aca.PHN_EXT_1 AS [PHN1EXT], aca.PHN_AREA_2 AS [PHN2AREA], aca.PHN_2 AS [PHN2], aca.PHN_EXT_2 AS [PHN2EXT], aca.PHN_AREA_3 AS [PHN3AREA], aca.PHN_3 AS [PHN3], aca.PHN_EXT_3 AS [PHN3EXT], aca.FAX_AREA AS [FAXAREA], aca.FAX AS [FAX], MR_HOUSE_OWNERSHIP_CODE AS 'BUILDING_OWNERSHIP' FROM APP_CUST_ADDR aca WITH(NOLOCK) JOIN APP_CUST ac WITH(NOLOCK) ON aca.APP_CUST_ID = ac.APP_CUST_ID JOIN APP a WITH(NOLOCK) ON a.APP_ID = ac.APP_ID JOIN REF_MASTER_LOS rml WITH(NOLOCK) ON rml.REF_MASTER_CODE = aca.MR_CUST_ADDR_TYPE_CODE WHERE a.APP_NO = '"+ appno +"'  AND ac.CUST_NAME = '"+ name +"' AND rml.REF_MASTER_NAME = '"+ addresstype +"') as orig unpivot (value for [Code] in ([CUST_ADDR_TYPE],[BUILDING_OWNERSHIP]) )as unpiv) as mastername JOIN REF_MASTER_LOS rf WITH(NOLOCK) ON rf.REF_MASTER_Code = mastername.value WHERE rf.IS_ACTIVE = '1') AS ref PIVOT (MAX(ref.REF_MASTER_NAME) for [Code] in ([CUST_ADDR_TYPE],[BUILDING_OWNERSHIP])) as piv"), {  row ->

			ResultSetMetaData rsmd = row.getMetaData()
			colmcount = rsmd.getColumnCount()


			for(i = 0 ; i < colmcount ; i++){
				address = (row[i])
				listaddr.add(address)
			}
		})
		return listaddr
	}

	//get NAP4 jobdata employee personal Store DB
	@Keyword
	public NAP4JobdataEmployeePersonalStoreData (Sql instance, String appno, String name){
		String Jobdata
		ArrayList<String> listjob = new ArrayList<>()
		instance.eachRow(("SELECT ISNULL([PROFCODE],'') AS PROFCODE , ISNULL([JOB_POSITION],'') AS JOBPOS, ISNULL([JOB_STAT],'') AS JOBSTAT, case when CONVERT(VARCHAR,ISMFEMP) = 1 then 'Yes' when CONVERT(VARCHAR,ISMFEMP) = 0 then 'No' ELSE ISNULL(CONVERT(VARCHAR,ISMFEMP),'') END AS ISMFEMP, case when CONVERT(varchar,[ISWELLKNOWNCOY]) = 1 Then 'Yes' when convert(varchar,[ISWELLKNOWNCOY]) = 0 Then 'No' else ISNULL(convert(varchar,[ISWELLKNOWNCOY]),'') end AS ISWELLKNOWNCOY, ISNULL([COYNAME],'') AS COYNAME, ISNULL([INDUSTRYTYPE],'') AS IND_TYPE, ISNULL([COY_SCALE],'') AS COYSCALE , ISNULL([ESTABLISHMENTDATE],'') AS EST_DATE, ISNULL([JOBTITLE],'') AS JOBTITLE , ISNULL([NUMOFEMP],'') AS NUMOFEMP FROM (SELECT [PROFCODE], [ISMFEMP], [ISWELLKNOWNCOY], [COYNAME], [INDUSTRYTYPE], [ESTABLISHMENTDATE], [JOBTITLE], [NUMOFEMP], mastername.Code, REF_MASTER_NAME FROM (select [PROFCODE], [ISMFEMP], [ISWELLKNOWNCOY], [COYNAME], [INDUSTRYTYPE], [ESTABLISHMENTDATE], [JOBTITLE], [NUMOFEMP], [Code], value FROM (SELECT MR_PROFESSION_CODE AS [PROFCODE], MR_JOB_POSITION_CODE AS 'JOB_POSITION', MR_JOB_STAT_CODE AS  'JOB_STAT', acpjd.IS_MF_EMP [ISMFEMP], acpjd.IS_WELLKNOWN_COY AS [ISWELLKNOWNCOY], acpjd.COY_NAME AS [COYNAME], INDUSTRY_TYPE_CODE AS [INDUSTRYTYPE], MR_COY_SCALE_CODE AS 'COY_SCALE', FORMAT(acpjd.EMPLOYMENT_ESTABLISHMENT_DT, 'MM/dd/yyyy') AS [ESTABLISHMENTDATE], JOB_TITLE_NAME AS [JOBTITLE], NUM_OF_EMPLOYEE AS [NUMOFEMP] FROM APP_CUST_PERSONAL_JOB_DATA acpjd WITH(NOLOCK) JOIN APP_CUST_PERSONAL acp WITH(NOLOCK) ON acp.APP_CUST_PERSONAL_ID = acpjd.APP_CUST_PERSONAL_ID JOIN APP_CUST ac WITH(NOLOCK) ON ac.APP_CUST_ID = acp.APP_CUST_ID JOIN APP a WITH(NOLOCK) ON a.APP_ID = ac.APP_ID WHERE a.APP_NO = '"+ appno +"' AND ac.CUST_NAME = '"+ name +"' ) as orig unpivot(value for [Code] in ([JOB_STAT],[COY_SCALE],[JOB_POSITION]))as unpiv) as mastername JOIN REF_MASTER_LOS rf WITH(NOLOCK) ON rf.REF_MASTER_Code = mastername.value WHERE rf.IS_ACTIVE = '1') AS ref PIVOT (MAX(ref.REF_MASTER_NAME) for [Code] in ([JOB_STAT],[COY_SCALE], [JOB_POSITION])) as piv"), {  row ->

			ResultSetMetaData rsmd = row.getMetaData()
			colmcount = rsmd.getColumnCount()


			for(i = 0 ; i < colmcount ; i++){
				Jobdata = (row[i])
				listjob.add(Jobdata)
			}
		})
		return listjob
	}

	//get NAP4 jobdata professional personal Store DB
	@Keyword
	public NAP4JobdataProfPersonalStoreData (Sql instance, String appno, String name){
		String Jobdata
		ArrayList<String> listjob = new ArrayList<>()
		instance.eachRow(("SELECT ISNULL(MR_PROFESSION_CODE,'') AS PROFCODE, ISNULL(MR_JOB_POSITION_CODE,'') AS JOBPOS, case when CONVERT(varchar,acpjd.IS_WELLKNOWN_COY) = 1 Then 'Yes' when convert(varchar,acpjd.IS_WELLKNOWN_COY) = 0 Then 'No' else ISNULL(convert(varchar,acpjd.IS_WELLKNOWN_COY),'') END AS ISWELLKNOWNCOY, ISNULL(acpjd.COY_NAME,'') AS COYNAME, ISNULL(INDUSTRY_TYPE_CODE,'') AS INDTYPE, ISNULL(MR_COY_SCALE_CODE,'') AS COYSCALE, ISNULL(FORMAT(acpjd.EMPLOYMENT_ESTABLISHMENT_DT, 'MM/dd/yyyy'),'') AS EMP_EST_DATE, ISNULL(JOB_TITLE_NAME,'') AS JOBTITLE, ISNULL(NUM_OF_EMPLOYEE,'') AS NUMOFEMP, ISNULL(MR_INVESTMENT_TYPE_CODE,'') AS INV_TYPE FROM APP_CUST_PERSONAL_JOB_DATA acpjd WITH(NOLOCK) JOIN APP_CUST_PERSONAL acp WITH(NOLOCK) ON acp.APP_CUST_PERSONAL_ID = acpjd.APP_CUST_PERSONAL_ID JOIN APP_CUST ac WITH(NOLOCK) ON ac.APP_CUST_ID = acp.APP_CUST_ID JOIN APP a WITH(NOLOCK) ON a.APP_ID = ac.APP_ID WHERE a.APP_NO = '"+ appno +"' AND ac.CUST_NAME = '"+ name +"'"), {  row ->

			ResultSetMetaData rsmd = row.getMetaData()
			colmcount = rsmd.getColumnCount()


			for(i = 0 ; i < colmcount ; i++){
				Jobdata = (row[i])
				listjob.add(Jobdata)
			}
		})
		return listjob
	}

	//get NAP4 jobdata non-professional personal Store DB
	@Keyword
	public NAP4JobdataNonProfPersonalStoreData (Sql instance, String appno, String name){
		String Jobdata
		ArrayList<String> listjob = new ArrayList<>()
		instance.eachRow(("SELECT ISNULL(acpjd.MR_PROFESSION_CODE,'') AS PROFCODE, ISNULL(acpjd.JOB_TITLE_NAME,'') AS JOBTITLE FROM APP_CUST_PERSONAL_JOB_DATA acpjd WITH(NOLOCK) JOIN APP_CUST_PERSONAL acp ON acp.APP_CUST_PERSONAL_ID = acpjd.APP_CUST_PERSONAL_ID JOIN APP_CUST ac WITH(NOLOCK) ON ac.APP_CUST_ID = acp.APP_CUST_ID JOIN APP a WITH(NOLOCK) ON a.APP_ID = ac.APP_ID WHERE a.APP_NO = '"+ appno +"' AND ac.CUST_NAME = '"+ name +"'"), {  row ->

			ResultSetMetaData rsmd = row.getMetaData()
			colmcount = rsmd.getColumnCount()


			for(i = 0 ; i < colmcount ; i++){
				Jobdata = (row[i])
				listjob.add(Jobdata)
			}
		})
		return listjob
	}

	//get NAP4 jobdata Small medium enterprise personal Store DB
	@Keyword
	public NAP4JobdataSMEPersonalStoreData (Sql instance, String appno, String name){
		String Jobdata
		ArrayList<String> listjob = new ArrayList<>()
		instance.eachRow(("SELECT ISNULL([PROFCODE],'') AS PROFCODE, ISNULL([JOB_POSITION],'') AS JOBPOS, case when convert(varchar, [ISWELLKNOWNCOY]) = 1 Then 'Yes' when convert(varchar, [ISWELLKNOWNCOY]) = 0 Then 'No' else ISNULL(convert(varchar,[ISWELLKNOWNCOY]),'') end AS ISWELLKNOWNCOY, ISNULL([COYNAME],'') AS COYNAME, ISNULL([INDUSTRYTYPE],'') AS INDTYPE, ISNULL([COY_SCALE],'') AS COYSCALE, ISNULL([ESTABLISHMENTDATE],'') AS EST_DATE, ISNULL([JOBTITLE],'') AS JOBTITLE, ISNULL([NUMOFEMP],'') AS NUMOFEMP, ISNULL([INVESTMENT_TYPE],'') AS INV_TYPE FROM (SELECT [PROFCODE], [ISWELLKNOWNCOY], [COYNAME], [INDUSTRYTYPE], [ESTABLISHMENTDATE], [JOBTITLE], [NUMOFEMP], mastername.Code, REF_MASTER_NAME FROM (select [PROFCODE], [ISWELLKNOWNCOY], [COYNAME], [INDUSTRYTYPE], [ESTABLISHMENTDATE], [JOBTITLE], [NUMOFEMP], [Code], value FROM (SELECT MR_PROFESSION_CODE AS [PROFCODE], MR_JOB_POSITION_CODE AS 'JOB_POSITION', acpjd.IS_WELLKNOWN_COY AS [ISWELLKNOWNCOY], acpjd.COY_NAME AS [COYNAME], INDUSTRY_TYPE_CODE AS [INDUSTRYTYPE], MR_COY_SCALE_CODE AS 'COY_SCALE', FORMAT(acpjd.EMPLOYMENT_ESTABLISHMENT_DT, 'MM/dd/yyyy') AS [ESTABLISHMENTDATE], JOB_TITLE_NAME AS [JOBTITLE], NUM_OF_EMPLOYEE AS [NUMOFEMP], MR_INVESTMENT_TYPE_CODE AS 'INVESTMENT_TYPE' FROM APP_CUST_PERSONAL_JOB_DATA acpjd WITH(NOLOCK) JOIN APP_CUST_PERSONAL acp ON acp.APP_CUST_PERSONAL_ID = acpjd.APP_CUST_PERSONAL_ID JOIN APP_CUST ac WITH(NOLOCK) ON ac.APP_CUST_ID = acp.APP_CUST_ID JOIN APP a WITH(NOLOCK) ON a.APP_ID = ac.APP_ID WHERE a.APP_NO = '"+ appno +"' AND ac.CUST_NAME = '"+ name +"' ) as orig unpivot (value for [Code] in ([COY_SCALE],[JOB_POSITION],[INVESTMENT_TYPE]))as unpiv) as mastername JOIN REF_MASTER_LOS rf on rf.REF_MASTER_Code = mastername.value WHERE rf.IS_ACTIVE = '1') AS ref PIVOT (MAX(ref.REF_MASTER_NAME) for [Code] in ([COY_SCALE], [JOB_POSITION], [INVESTMENT_TYPE])) as piv"), {  row ->

			ResultSetMetaData rsmd = row.getMetaData()
			colmcount = rsmd.getColumnCount()


			for(i = 0 ; i < colmcount ; i++){
				Jobdata = (row[i])
				listjob.add(Jobdata)
			}
		})
		return listjob
	}

	//get NAP4 jobdata address personal Store DB
	@Keyword
	public NAP4JobdataAddressStoreData (Sql instance, String appno, String name){
		String Jobdata
		ArrayList<String> listjob = new ArrayList<>()
		instance.eachRow(("SELECT ISNULL(ADDR,'') AS ADDR, ISNULL(AREA_CODE_4,'') AS AREA4, ISNULL(AREA_CODE_3,'') AS AREA3, ISNULL(ZIPCODE,'') AS ZIPCODE, ISNULL(AREA_CODE_2,'') AS AREA2, ISNULL(AREA_CODE_1,'') AS AREA1, ISNULL(CITY,'') AS CITY, ISNULL(NOTES,'') AS NOTES, ISNULL(PHN_AREA_1,'') AS PHNAREA1, ISNULL(PHN_1,'') AS PHN1, ISNULL(PHN_EXT_1,'') AS PHNEXT1, ISNULL(PHN_AREA_2,'') AS PHNAREA2, ISNULL(PHN_2,'') AS PHN2, ISNULL(PHN_EXT_2,'') AS PHNEXT2, ISNULL(PHN_AREA_3,'') AS PHNAREA3, ISNULL(PHN_3,'') AS PHN3, ISNULL(PHN_EXT_3,'') AS PHNEXT3, ISNULL(FAX_AREA,'') AS FAXAREA, ISNULL(FAX,'') AS FAX FROM APP_CUST_PERSONAL_JOB_DATA acpjd WITH(NOLOCK) JOIN APP_CUST_PERSONAL acp WITH(NOLOCK) ON acp.APP_CUST_PERSONAL_ID = acpjd.APP_CUST_PERSONAL_ID JOIN APP_CUST ac WITH(NOLOCK) ON ac.APP_CUST_ID = acp.APP_CUST_ID JOIN APP a WITH(NOLOCK) ON a.APP_ID = ac.APP_ID JOIN APP_CUST_ADDR aca WITH(NOLOCK) ON aca.APP_CUST_ID = acp.APP_CUST_ID WHERE a.APP_NO = '"+ appno +"' AND ac.CUST_NAME = '"+ name +"' AND aca.MR_CUST_ADDR_TYPE_CODE = 'JOB'"), {  row ->

			ResultSetMetaData rsmd = row.getMetaData()
			colmcount = rsmd.getColumnCount()


			for(i = 0 ; i < colmcount ; i++){
				Jobdata = (row[i])
				listjob.add(Jobdata)
			}
		})
		return listjob
	}

	//get NAP4 jobdata prev job address personal Store DB
	@Keyword
	public NAP4PrevJobAddressStoreData (Sql instance, String appno, String name){
		String Jobdata
		ArrayList<String> listjob = new ArrayList<>()
		instance.eachRow(("SELECT ISNULL(PREV_COY_NAME,'') AS PREVCOYNAME, ISNULL(FORMAT(PREV_EMPLOYMENT_DT, 'MM/dd/yyy'),'') AS PREVEMPDATE, ISNULL(ADDR,'') AS ADDR, ISNULL(AREA_CODE_4,'') AS AREA4, ISNULL(AREA_CODE_3,'') AS AREA3, ISNULL(ZIPCODE,'') AS ZIPCODE, ISNULL(AREA_CODE_2,'') AS AREA2, ISNULL(AREA_CODE_1,'') AS AREA1, ISNULL(CITY,'') AS CITY, ISNULL(NOTES,'') AS NOTES, ISNULL(PHN_AREA_1,'') AS PHNAR1, ISNULL(PHN_1,'') AS PHN1, ISNULL(PHN_EXT_1,'') AS PHNEXT1 , ISNULL(PHN_AREA_2,'') AS PHNAR2 , ISNULL(PHN_2,'') AS PHN2, ISNULL(PHN_EXT_2,'') AS PHNEXT2, ISNULL(PHN_AREA_3,'') AS PHNAR3, ISNULL(PHN_3,'') AS PHN3, ISNULL(PHN_EXT_3,'') AS PHNEXT3, ISNULL(FAX_AREA,'') AS FAXAREA, ISNULL(FAX,'') AS FAX FROM APP_CUST_PERSONAL_JOB_DATA acpjd WITH(NOLOCK) JOIN APP_CUST_PERSONAL acp WITH(NOLOCK) ON acp.APP_CUST_PERSONAL_ID = acpjd.APP_CUST_PERSONAL_ID JOIN APP_CUST ac WITH(NOLOCK) ON ac.APP_CUST_ID = acp.APP_CUST_ID JOIN APP a WITH(NOLOCK) ON a.APP_ID = ac.APP_ID JOIN APP_CUST_ADDR aca WITH(NOLOCK) ON aca.APP_CUST_ID = acp.APP_CUST_ID WHERE a.APP_NO = '"+ appno +"' AND ac.CUST_NAME = '"+ name +"' AND aca.MR_CUST_ADDR_TYPE_CODE = 'PREV_JOB'"), {  row ->

			ResultSetMetaData rsmd = row.getMetaData()
			colmcount = rsmd.getColumnCount()


			for(i = 0 ; i < colmcount ; i++){
				Jobdata = (row[i])
				listjob.add(Jobdata)
			}
		})
		return listjob
	}

	//get NAP4 jobdata other biz address personal Store DB
	@Keyword
	public NAP4OtherBizAddressStoredata (Sql instance, String appno, String name){
		String Jobdata
		ArrayList<String> listjob = new ArrayList<>()
		instance.eachRow(("SELECT ISNULL(OTH_BIZ_NAME,'') AS OTH_BIZ_NAME, ISNULL(OTH_BIZ_TYPE,'') AS OTH_BIZ_TYPE, ISNULL(OTH_BIZ_INDUSTRY_TYPE_CODE,'') AS OTH_BIZ_IND_TYPE , ISNULL(OTH_BIZ_JOB_POSITION,'') AS OTH_BIZ_JOB_POS, ISNULL(FORMAT(OTH_BIZ_ESTABLISHMENT_DT, 'MM/dd/yyy'),'') AS OTH_BIZ_EST_DT , ISNULL(ADDR,'') AS ADDR, ISNULL(AREA_CODE_4,'') AS AREA4, ISNULL(AREA_CODE_3,'') AS AREA3, ISNULL(ZIPCODE,'') AS ZIPCODE, ISNULL(AREA_CODE_2,'') AS AREA2, ISNULL(AREA_CODE_1,'') AS AREA1, ISNULL(CITY,'') AS CITY, ISNULL(NOTES,'') AS NOTES, ISNULL(PHN_AREA_1,'') AS PHNAR1, ISNULL(PHN_1,'') AS PHN1, ISNULL(PHN_EXT_1,'') AS PHNEXT1 , ISNULL(PHN_AREA_2,'') AS PHNAR2 , ISNULL(PHN_2,'') AS PHN2, ISNULL(PHN_EXT_2,'') AS PHNEXT2, ISNULL(PHN_AREA_3,'') AS PHNAR3, ISNULL(PHN_3,'') AS PHN3, ISNULL(PHN_EXT_3,'') AS PHNEXT3, ISNULL(FAX_AREA,'') AS FAXAREA, ISNULL(FAX,'') AS FAX FROM APP_CUST_PERSONAL_JOB_DATA acpjd WITH(NOLOCK) JOIN APP_CUST_PERSONAL acp WITH(NOLOCK) ON acp.APP_CUST_PERSONAL_ID = acpjd.APP_CUST_PERSONAL_ID JOIN APP_CUST ac WITH(NOLOCK) ON ac.APP_CUST_ID = acp.APP_CUST_ID JOIN APP a WITH(NOLOCK) ON a.APP_ID = ac.APP_ID JOIN APP_CUST_ADDR aca WITH(NOLOCK) ON aca.APP_CUST_ID = acp.APP_CUST_ID WHERE a.APP_NO = '"+ appno +"' AND ac.CUST_NAME = '"+ name +"' AND aca.MR_CUST_ADDR_TYPE_CODE = 'OTH_BIZ'"), {  row ->

			ResultSetMetaData rsmd = row.getMetaData()
			colmcount = rsmd.getColumnCount()


			for(i = 0 ; i < colmcount ; i++){
				Jobdata = (row[i])
				listjob.add(Jobdata)
			}
		})
		return listjob
	}

	//get NAP4 emergency contacy personal Store DB
	@Keyword
	public NAP4EmergencyContactStoreData (Sql instance, String appno, String name){
		String emergencycontact
		ArrayList <String> listemergencycontact = new ArrayList<String>()
		instance.eachRow(("SELECT ISNULL(acec.CONTACT_PERSON_NAME,'') AS CONPER_NAME,  ISNULL(rml.REF_MASTER_NAME,'') AS IDTYPE,  ISNULL(acec.ID_NO,'') AS IDNO,  ISNULL(FORMAT(acec.ID_EXPIRED_DT, 'MM/dd/yyy'),'') AS IDEXPDATE, ISNULL(acec.MR_CUST_RELATIONSHIP_CODE,'') AS CUST_RELATION, ISNULL(acec.EMAIL,'') AS EMAIL,  ISNULL(acec.MR_GENDER_CODE,'') AS GENDER,  ISNULL(acec.BIRTH_PLACE,'') AS BIRTHPLACE,  ISNULL(FORMAT(acec.BIRTH_DT, 'MM/dd/yyyy'),'') AS BIRTHDATE,  ISNULL(acec.MOBILE_PHN_NO_1,'') AS MOBILE1,  ISNULL(acec.MOBILE_PHN_NO_2,'') AS MOBILE2,  ISNULL(acec.ADDR,'') AS ADDR,  ISNULL(acec.AREA_CODE_4,'') AS AREA4,  ISNULL(acec.AREA_CODE_3,'') AS AREA3,  ISNULL(acec.ZIPCODE,'') AS ZIPCODE,  ISNULL(acec.AREA_CODE_1,'') AS AREA1,  ISNULL(acec.AREA_CODE_2,'') AS AREA2,  ISNULL(acec.CITY,'') AS CITY,  ISNULL(acec.PHN_AREA_1,'') AS PHNAR1,  ISNULL(acec.PHN_1,'') AS PHN1,  ISNULL(acec.PHN_EXT_1,'') AS PHNEXT1,  ISNULL(acec.PHN_AREA_2,'') AS PHNAR2,  ISNULL(acec.PHN_2,'') AS PHN2,  ISNULL(acec.PHN_EXT_2,'') AS PHNEXT2,  ISNULL(acec.PHN_AREA_3,'') AS PHNAR3,  ISNULL(acec.PHN_3,'') AS PHN3,  ISNULL(acec.PHN_EXT_3,'') AS PHNEXT3 FROM APP_CUST_EMRGNC_CNTCT acec WITH(NOLOCK) JOIN APP_CUST ac WITH(NOLOCK) ON ac.APP_CUST_ID = acec.APP_CUST_ID JOIN APP a WITH(NOLOCK) ON a.APP_ID = ac.APP_ID JOIN REF_MASTER_LOS rml WITH(NOLOCK) ON rml.REF_MASTER_CODE = acec.MR_ID_TYPE_CODE WHERE a.APP_NO = '"+ appno +"' AND ac.CUST_NAME = '"+ name +"' AND rml.REF_MASTER_TYPE_CODE = 'ID_TYPE' AND rml.IS_ACTIVE = '1'"), {  row ->

			ResultSetMetaData rsmd = row.getMetaData()
			colmcount = rsmd.getColumnCount()


			for(i = 0 ; i < colmcount ; i++){
				emergencycontact = (row[i])
				listemergencycontact.add(emergencycontact)
			}
		})
		return listemergencycontact


	}

	//get NAP4 emergency contact personal Store DB lookup
	@Keyword
	public NAP4EmergencyContactStoreDataLookUp (Sql instance, String appno, String name){
		String emergencycontact
		ArrayList <String> listemergencycontact = new ArrayList<String>()
		int i
		instance.eachRow(("SELECT ISNULL(acec.CONTACT_PERSON_NAME,'') AS CONPERNAME, ISNULL(acec.MR_ID_TYPE_CODE,'') AS IDTYPE , ISNULL(acec.ID_NO,'') AS IDNO, ISNULL(acec.ID_EXPIRED_DT,'') AS IDEXPDATE, ISNULL(acec.MOBILE_PHN_NO_1,'') AS MOBILE1, ISNULL(acec.EMAIL,'') AS EMAIL, ISNULL(acec.MR_GENDER_CODE,'') AS GENDER, ISNULL(acec.BIRTH_PLACE,'') AS BIRTHPLACE, ISNULL(FORMAT(acec.BIRTH_DT, 'yyyy-MM-dd'),'') AS BIRTHDATE, ISNULL(acec.MOBILE_PHN_NO_2,'') AS MOBILE2, ISNULL(acec.ADDR,'') AS ADDR, ISNULL(acec.AREA_CODE_4,'') AS AREA4, ISNULL(acec.AREA_CODE_3,'') AS AREA3, ISNULL(acec.ZIPCODE,'') AS ZIPCODE, ISNULL(acec.AREA_CODE_2,'') AS AREA2, ISNULL(acec.AREA_CODE_1,'') AS AREA1, ISNULL(acec.CITY,'') AS CITY,  ISNULL(acec.PHN_AREA_1,'') AS PHN1AR, ISNULL(acec.PHN_1,'') AS PHN1,  ISNULL(acec.PHN_EXT_1,'') AS PHNEXT1,  ISNULL(acec.PHN_AREA_2,'') AS PHNAR2,  ISNULL(acec.PHN_2,'') AS PHN2,  ISNULL(acec.PHN_EXT_2,'') AS PHNEXT2,  ISNULL(acec.PHN_AREA_3,'') AS PHNAR3,  ISNULL(acec.PHN_3,'') AS PHN3,  ISNULL(acec.PHN_EXT_3,'') AS PHNEXT3, ISNULL(acec.MR_CUST_RELATIONSHIP_CODE,'') AS CUST_RELATION FROM APP_CUST_EMRGNC_CNTCT acec WITH(NOLOCK) JOIN APP_CUST ac WITH(NOLOCK) ON ac.APP_CUST_ID = acec.APP_CUST_ID JOIN APP a WITH(NOLOCK) ON a.APP_ID = ac.APP_ID WHERE a.APP_NO = '"+ appno +"' AND ac.CUST_NAME = '"+ name +"'"), {  row ->

			for(i = 0 ; i < 27 ; i++){
				emergencycontact = (row[i])
				listemergencycontact.add(emergencycontact)
			}
		})
		return listemergencycontact
	}

	//get NAP4 emergency contacy personal lookup data verif
	@Keyword
	public NAP4EmergencyContactDataVerif (Sql instance, String name, String idno){
		String emergencycontact
		ArrayList <String> listemergencycontact = new ArrayList<String>()
		int i
		instance.eachRow(("SELECT ISNULL(Cust_name,'') AS CUST_NAME, ISNULL(MR_ID_TYPE_CODE,'') AS IDTYPE, ISNULL(ID_NO,'') AS IDNO, ISNULL(ID_EXPIRED_DT,'') AS IDEXPDATE, ISNULL(MOBILE_PHN_NO_1,'') AS MOBILE1, ISNULL(EMAIL_1,'') AS EMAIL, ISNULL(MR_GENDER_CODE,'') AS GENDER, ISNULL(BIRTH_PLACE,'') AS BIRTHPLACE, ISNULL(FORMAT(BIRTH_DT, 'yyyy-MM-dd'),'') AS BIRTHDATE, ISNULL(MOBILE_PHN_NO_2,'') AS MOBILE2, ISNULL(ADDR,'') AS ADDR, ISNULL(AREA_CODE_4,'') AS AREA4, ISNULL(AREA_CODE_3,'') AS AREA3, ISNULL(ZIPCODE,'') AS ZIPCODE, ISNULL(AREA_CODE_2,'') AS AREA2, ISNULL(AREA_CODE_1,'') AS AREA1, ISNULL(CITY,'') AS CITY,  ISNULL(PHN_AREA_1,'') AS PHNAR1, ISNULL(PHN_1,'') AS PHN1,  ISNULL(PHN_EXT_1,'') AS PHNEXT1,  ISNULL(PHN_AREA_2,'') AS PHNAR2,  ISNULL(PHN_2,'') AS PHN2,  ISNULL(PHN_EXT_2,'') AS PHNEXT2,  ISNULL(PHN_AREA_3,'') AS PHNAR3,  ISNULL(PHN_3,'') AS PHN3,  ISNULL(PHN_EXT_3,'') AS PHNEXT3 from CUST c WITH(NOLOCK) join cust_addr addr WITH(NOLOCK) on c.CUST_ID = addr.CUST_ID JOIN cust_personal cp WITH(NOLOCK) on c.cust_ID = cp.CUST_ID where c.cust_name = '"+ name +"' and ID_NO = '"+ idno +"' AND MR_CUST_ADDR_TYPE_CODE = 'legal'"), {  row ->

			for(i = 0 ; i < 26 ; i++){
				emergencycontact = (row[i])
				listemergencycontact.add(emergencycontact)
			}
		})
		return listemergencycontact
	}

	//get NAP4 financial data personal Store DB
	@Keyword
	public NAP4FinancialDataPersonalStoreData (Sql instance, String appno, String name, String date){
		String financialdata
		ArrayList <String> listfinancialdata = new ArrayList<String>()

		instance.eachRow(("SELECT ISNULL(FORMAT(MONTHLY_INCOME_AMT, 'N0'),'') AS MONINCOME, ISNULL(FORMAT(OTHER_INCOME_AMT, 'N0'),'') AS OTHINCOME, ISNULL(rml.REF_MASTER_NAME,'') AS SOURCEINCOME, ISNULL(FORMAT(MONTHLY_INSTALLMENT_AMT, 'N0'),'') AS MONTHINST, ISNULL(FORMAT(MONTHLY_EXPENSE_AMT, 'N0'),'') AS MONEXPN, ISNULL(FORMAT(acpfd.DATE_AS_OF , 'MM/dd/yyy'),'') AS DATEASOF FROM APP_CUST_PERSONAL_FIN_DATA acpfd WITH(NOLOCK) JOIN APP_CUST_PERSONAL acp WITH(NOLOCK) ON acp.APP_CUST_PERSONAL_ID = acpfd.APP_CUST_PERSONAL_ID JOIN APP_CUST ac WITH(NOLOCK) ON ac.APP_CUST_ID = acp.APP_CUST_ID JOIN APP a WITH(NOLOCK) ON a.APP_ID = ac.APP_ID JOIN REF_MASTER_LOS rml WITH(NOLOCK) ON rml.REF_MASTER_CODE = acpfd.MR_SOURCE_OF_INCOME_TYPE_CODE WHERE a.APP_NO = '"+ appno +"' AND ac.CUST_NAME = '"+ name +"' AND FORMAT(acpfd.DATE_AS_OF , 'MM/dd/yyy') = '"+ date +"'"), {  row ->

			ResultSetMetaData rsmd = row.getMetaData()
			colmcount = rsmd.getColumnCount()


			for(i = 0 ; i < colmcount ; i++){
				financialdata = (row[i])
				listfinancialdata.add(financialdata)
			}
		})
		return listfinancialdata
	}

	//get NAP4 financial attr personal Store DB
	@Keyword
	public NAP4FinancialAttrStoreData (Sql instance, String appno, String name){
		String financialattr
		ArrayList <String> listfinancialattr = new ArrayList<String>()
		instance.eachRow(("SELECT ISNULL(ATTR_VALUE,'') AS FIN_ATTR FROM APP_CUST_FIN_DATA_ATTR_CONTENT acpfd WITH(NOLOCK) JOIN APP_CUST ac WITH(NOLOCK) ON ac.APP_CUST_ID = acpfd.APP_CUST_ID JOIN APP a WITH(NOLOCK) ON a.APP_ID = ac.APP_ID WHERE a.APP_NO = '"+ appno +"' AND ac.CUST_NAME = '"+ name +"'"), {  row ->

			ResultSetMetaData rsmd = row.getMetaData()
			colmcount = rsmd.getColumnCount()


			for(i = 0 ; i < colmcount ; i++){
				financialattr = (row[i])
				listfinancialattr.add(financialattr)
			}
		})
		return listfinancialattr
	}

	//get NAP4 bank acc personal Store DB
	@Keyword
	public NAP4FinDataBankAccStoreData (Sql instance, String appno, String name, String bankno){
		String bankacc
		ArrayList <String> listbankacc = new ArrayList<String>()
		instance.eachRow(("SELECT ISNULL(acba.BANK_CODE,'') AS CODE, ISNULL(acba.BANK_BRANCH,'') AS BRANCH, ISNULL(acba.BANK_ACC_NAME,'') AS ACCNAME, ISNULL(acba.BANK_ACC_NO,'') AS ACCNO, CASE WHEN acba.IS_DEFAULT = 1 THEN 'YES' ELSE 'NO' END AS ISDEF, CASE WHEN acba.IS_ACTIVE = 1 THEN 'YES' ELSE 'NO' END AS ISACT, ISNULL(FORMAT(acba.BEG_BALANCE_AMT, 'N0'),'') AS BEG_BAL_AMT FROM APP_CUST_BANK_ACC acba WITH(NOLOCK) JOIN APP_CUST_PERSONAL acp WITH(NOLOCK) ON acba.APP_CUST_ID = acp.APP_CUST_ID JOIN APP_CUST ac WITH(NOLOCK) ON ac.APP_CUST_ID = acba.APP_CUST_ID JOIN APP a WITH(NOLOCK) ON a.APP_ID = ac.APP_ID WHERE a.APP_NO = '"+ appno +"' AND ac.CUST_NAME = '"+ name +"' AND acba.BANK_ACC_NO = '"+ bankno +"' GROUP BY acba.BANK_CODE, acba.BANK_BRANCH, acba.BANK_ACC_NAME, acba.BANK_ACC_NO, acba.IS_DEFAULT, acba.IS_ACTIVE, acba.BEG_BALANCE_AMT"), {  row ->

			ResultSetMetaData rsmd = row.getMetaData()
			colmcount = rsmd.getColumnCount()


			for(i = 0 ; i < colmcount ; i++){
				bankacc = (row[i])
				listbankacc.add(bankacc)
			}


		})
		return listbankacc
	}

	//get NAP4 check bank statement Store DB
	@Keyword
	public NAP4FinancialCheckBankStatStoreData (Sql instance, String appno, String name, String accno){
		String bankstat
		instance.eachRow(("SELECT ISNULL(acba.IS_BANK_STMNT,'') AS IS_BANK_STMNT FROM APP_CUST_BANK_ACC acba WITH(NOLOCK) JOIN APP_CUST_PERSONAL acp WITH(NOLOCK) ON acba.APP_CUST_ID = acp.APP_CUST_ID JOIN APP_CUST ac WITH(NOLOCK) ON ac.APP_CUST_ID = acba.APP_CUST_ID JOIN APP a WITH(NOLOCK) ON a.APP_ID = ac.APP_ID JOIN APP_CUST_BANK_STMNT acbs WITH(NOLOCK) ON acba.APP_CUST_BANK_ACC_ID = acbs.APP_CUST_BANK_ACC_ID WHERE a.APP_NO = '"+ appno +"' AND ac.CUST_NAME = '"+ name +"' AND acba.BANK_ACC_NO = '"+ accno +"' GROUP BY acba.IS_BANK_STMNT"), {  row ->

			bankstat = (row[0])

		})
		return bankstat
	}

	//get NAP4 bank statement information personal Store DB
	@Keyword
	public NAP4BankStatDataStoreData (Sql instance, String appno, String name, String accno){
		String bankstatdata
		ArrayList <String> listbankstatdata = new ArrayList<String>()
		instance.eachRow(("SELECT ISNULL(acbs.MONTH,'') AS MONTH ,ISNULL(acbs.YEAR,'') AS YEAR , ISNULL(acbs.DEBIT_TRX_COUNT,'') AS DEBTRXCOUNT , ISNULL(FORMAT(acbs.DEBIT_AMT, 'N0'),'') AS DEBAMT, ISNULL(acbs.CREDIT_TRX_COUNT,'') AS CREDTRXCOUNT, ISNULL(FORMAT(acbs.CREDIT_AMT, 'N0'),'') AS CREDAMT FROM APP_CUST_BANK_ACC acba WITH(NOLOCK) JOIN APP_CUST_PERSONAL acp WITH(NOLOCK) ON acba.APP_CUST_ID = acp.APP_CUST_ID JOIN APP_CUST ac WITH(NOLOCK) ON ac.APP_CUST_ID = acba.APP_CUST_ID JOIN APP a WITH(NOLOCK) ON a.APP_ID = ac.APP_ID JOIN APP_CUST_BANK_STMNT acbs WITH(NOLOCK) ON acba.APP_CUST_BANK_ACC_ID = acbs.APP_CUST_BANK_ACC_ID WHERE a.APP_NO = '"+ appno +"' AND ac.CUST_NAME = '"+ name +"' AND acba.BANK_ACC_NO = '"+ accno +"' ORDER BY YEAR"), {  row ->

			ResultSetMetaData rsmd = row.getMetaData()
			colmcount = rsmd.getColumnCount()


			for(i = 0 ; i < colmcount ; i++){
				bankstatdata = (row[i])
				listbankstatdata.add(bankstatdata)
			}


		})
		return listbankstatdata
	}

	//get NAP4 customer asset data personal Store DB
	@Keyword
	public NAP4CustomerAssetDataStoreData (Sql instance, String appno, String name){
		String customerasset
		ArrayList <String> listcustomerasset = new ArrayList<String>()
		instance.eachRow(("SELECT ISNULL(rml.REF_MASTER_NAME,'') AS ASSETTYPE, ISNULL(aca.ASSET_DESCR,'') AS DESCR, ISNULL(FORMAT(aca.ASSET_VALUE, 'N0'),'') AS VAL, ISNULL(aca.ASSET_QTY,'') AS QTY FROM APP_CUST_ASSET aca WITH(NOLOCK) JOIN APP_CUST ac WITH(NOLOCK) ON ac.APP_CUST_ID = aca.APP_CUST_ID JOIN  APP a WITH(NOLOCK) ON a.APP_ID = ac.APP_ID JOIN REF_MASTER_LOS rml WITH(NOLOCK) ON rml.REF_MASTER_CODE = aca.MR_CUST_ASSET_TYPE_CODE WHERE a.APP_NO = '"+ appno +"' AND ac.CUST_NAME = '"+ name +"' ORDER BY aca.APP_CUST_ASSET_ID"), {  row ->

			ResultSetMetaData rsmd = row.getMetaData()
			colmcount = rsmd.getColumnCount()

			for(i = 0 ; i < colmcount ; i++){
				customerasset = (row[i])
				listcustomerasset.add(customerasset)
			}
		})
		return listcustomerasset
	}

	//get NAP4 other attr info Store DB
	@Keyword
	public NAP4OtherAttrInfoStoreData (Sql instance, String appno, String name){
		String Otherattrinfo
		ArrayList<String> listotherinfo = new ArrayList<>()
		instance.eachRow(("SELECT ISNULL(oth.LBPPMS_DEBT_GRP_LBPP_DESCR,'') AS LBPPMS_DEBT_GRP_LBPP_DESCR, ISNULL(oth.LBPPMS_BIZ_SCL_LBPP_DESCR,'') AS LBPPMS_BIZ_SCL_LBPP_DESCR, ISNULL(oth.LBPPMS_CNTRPRT_LBPP_DESCR,'') AS LBPPMS_CNTRPRT_LBPP_DESCR, ISNULL(oth.LBPPMS_BIZ_SUSTAIN_LBPP_DESCR,'') AS LBPPMS_BIZ_SUSTAIN_LBPP_DESCR FROM APP_CUST_OTHER_INFO oth WITH(NOLOCK) JOIN APP_CUST ac WITH(NOLOCK) ON ac.APP_CUST_ID = oth.APP_CUST_ID JOIN APP a WITH(NOLOCK) ON a.APP_ID = ac.APP_ID WHERE a.APP_NO = '"+ appno +"' AND ac.CUST_NAME = '"+ name +"'"), {  row ->

			ResultSetMetaData rsmd = row.getMetaData()
			colmcount = rsmd.getColumnCount()

			for(i = 0 ; i < colmcount ; i++){
				Otherattrinfo = (row[i])
				listotherinfo.add(Otherattrinfo)
			}
		})
		return listotherinfo
	}

	//get NAP4 other attr Store DB
	@Keyword
	public NAP4OtherAttrStoreData (Sql instance, String appno, String name){
		String OtherAttr
		ArrayList <String> listOtherAttr = new ArrayList<String>()
		instance.eachRow(("SELECT ISNULL(ATTR_VALUE,'') AS OTHER_ATTR FROM APP_CUST_ATTR_CONTENT acac WITH (NOLOCK) JOIN APP_CUST ac WITH (NOLOCK) ON ac.APP_CUST_ID = acac.APP_CUST_ID JOIN APP a WITH (NOLOCK) ON a.APP_ID = ac.APP_ID WHERE a.APP_NO = '"+ appno +"' AND ac.CUST_NAME = '"+ name +"'"), {  row ->

			ResultSetMetaData rsmd = row.getMetaData()
			colmcount = rsmd.getColumnCount()

			for(i = 0 ; i < colmcount ; i++){
				OtherAttr = (row[i])
				listOtherAttr.add(OtherAttr)
			}

		})
		return listOtherAttr
	}

	//get NAP4 customer detail company Store DB
	@Keyword
	public NAP4CustomerDetailCompanyStoreData (Sql instance, String appno, String name){
		String customerdetail
		ArrayList<String> listcust = new ArrayList<>()
		instance.eachRow(("SELECT ISNULL(FORMAT(acc.ESTABLISHMENT_DT, 'MM/dd/yyyy'),'') AS EST_DATE, ISNULL(acc.NUM_OF_EMP,'') AS NUMOFEMP, case when ac.IS_VIP = 1 then 'Yes' when ac.IS_VIP = 0 then 'No' end AS ISVIP, case when ac.IS_AFFILIATE_WITH_MF = 1 then 'Yes' when ac.IS_AFFILIATE_WITH_MF = 0 then 'No' end AS ISAFFMF, ISNULL(acc.INDUSTRY_TYPE_CODE,'') AS INDTYPE, ISNULL(ac.MR_CUST_MODEL_CODE,'') AS CUST_MODEL, ISNULL(ac.VIP_NOTES,'') AS VIP_NOTES FROM APP_CUST ac WITH(NOLOCK) JOIN APP a WITH(NOLOCK) ON a.APP_ID = ac.APP_ID JOIN APP_CUST_COMPANY acc WITH(NOLOCK) ON acc.APP_CUST_ID = ac.APP_CUST_ID WHERE a.APP_NO = '"+ appno +"' AND ac.CUST_NAME = '"+ name +"'"), {  row ->

			ResultSetMetaData rsmd = row.getMetaData()
			colmcount = rsmd.getColumnCount()


			for(i = 0 ; i < colmcount ; i++){
				customerdetail = (row[i])
				listcust.add(customerdetail)
			}
		})
		return listcust
	}

	//get NAP4 contact information company Store DB
	@Keyword
	public NAP4ContactInformationStoreData (Sql instance, String appno, String name){
		String contactinfo
		ArrayList<String> listcontact = new ArrayList<>()
		instance.eachRow(("SELECT TOP 1 ISNULL([ContactPersonName],'') AS CONPERNAME, ISNULL([JobTitle],'') AS JOBTITLE, ISNULL([ID_TYPE],'') AS IDTYPE, ISNULL([ContactIDNo],'') AS IDNO, ISNULL([ExpiredDate],'') AS EXPDATE, ISNULL([MobilePhn1],'') AS MOBILE1, ISNULL([Email1],'') AS EMAIL1, ISNULL([GENDER],'') AS GENDER, ISNULL([JOB_POSITION],'') AS JOBPOS, ISNULL([BirthPlace],'') AS BIRTHPLACE, ISNULL([BirthDate],'') AS BIRTHDATE, ISNULL([CUST_COMPANY_RELATIONSHIP],'') AS COMPANY_RELATION, ISNULL([Mobilephn2],'') AS MOBILE2, ISNULL([Email2],'') AS EMAIL2, ISNULL([Addr],'') AS ADDR, ISNULL([RT],'') AS RT, ISNULL([RW],'') AS RW, ISNULL([Zipcode],'') AS ZIPCODE, ISNULL([Kecamatan],'') AS KECAMATAN, ISNULL([Kelurahan],'') AS KELURAHAN, ISNULL([Kota],'') AS KOTA, ISNULL([Phone1Area],'') AS PHN1AR, ISNULL([Phone1],'') AS PHN1, ISNULL([Phone1Ext],'') AS PHN1EXT, ISNULL([Phone2Area],'') AS PHN2AR, ISNULL([Phone2],'') AS PHN2, ISNULL([Phone2Ext],'') AS PHN2EXT, ISNULL([FaxArea],'') AS FAXAREA, ISNULL([Fax],'') AS FAX FROM (SELECT [ContactPersonName], [JobTitle], [ContactIDNo], [ExpiredDate], [MobilePhn1], [Email1], [BirthPlace], [BirthDate], [Mobilephn2], [Email2], [Addr], [RT], [RW], [Zipcode], [Kecamatan], [Kelurahan], [Kota], [Phone1Area], [Phone1], [Phone1Ext], [Phone2Area], [Phone2], [Phone2Ext], [FaxArea], [Fax], mastername.Code, REF_MASTER_NAME FROM (select [ContactPersonName], [JobTitle], [ContactIDNo], [ExpiredDate], [MobilePhn1], [Email1], [BirthPlace], [BirthDate], [Mobilephn2], [Email2], [Addr], [RT], [RW], [Zipcode], [Kecamatan], [Kelurahan], [Kota], [Phone1Area], [Phone1], [Phone1Ext], [Phone2Area], [Phone2], [Phone2Ext], [FaxArea], [Fax], [Code], value FROM (SELECT acccp.CONTACT_PERSON_NAME as [ContactPersonName], acccp.JOB_TITLE_NAME as [JobTitle], acccp.MR_ID_TYPE_CODE as 'ID_TYPE', acccp.ID_NO as [ContactIDNo], FORMAT(acccp.ID_EXPIRED_DT, 'MM/dd/yyyy') as [ExpiredDate], acccp.MOBILE_PHN_NO_1 as [MobilePhn1], acccp.EMAIL_1 as [Email1], acccp.MR_JOB_POSITION_CODE as 'JOB_POSITION', acccp.BIRTH_PLACE as [BirthPlace], FORMAT(acccp.BIRTH_DT, 'MM/dd/yyyy') as [BirthDate], acccp.MR_CUST_RELATIONSHIP_CODE as 'CUST_COMPANY_RELATIONSHIP', acccp.MOBILE_PHN_NO_2 as [Mobilephn2], acccp.EMAIL_2 as [Email2], aca.ADDR as [Addr], aca.AREA_CODE_4 as [RT], aca.AREA_CODE_3 as [RW], aca.ZIPCODE as [Zipcode], aca.AREA_CODE_1 as [Kecamatan], aca.AREA_CODE_2 as [Kelurahan], aca.CITY as [Kota], aca.PHN_AREA_1 as [Phone1Area], aca.PHN_1 as [Phone1], aca.PHN_EXT_1 as [Phone1Ext], aca.PHN_AREA_2 as [Phone2Area], aca.PHN_2 as [Phone2], aca.PHN_EXT_2 as [Phone2Ext], aca.FAX_AREA as [FaxArea], aca.FAX as [Fax], MR_GENDER_CODE as 'GENDER' FROM APP_CUST_COMPANY_CONTACT_PERSON acccp WITH(NOLOCK) JOIN APP_CUST_COMPANY acc WITH(NOLOCK) ON acc.APP_CUST_COMPANY_ID = acccp.APP_CUST_COMPANY_ID JOIN APP_CUST ac WITH(NOLOCK) ON ac.APP_CUST_ID = acc.APP_CUST_ID JOIN APP a WITH(NOLOCK) ON a.APP_ID = ac.APP_ID JOIN APP_CUST_ADDR aca ON aca.APP_CUST_ID = ac.APP_CUST_ID WHERE a.APP_NO = '"+ appno +"' AND ac.CUST_NAME = '"+ name +"' AND aca.MR_CUST_ADDR_TYPE_CODE = 'COMPANY') as orig unpivot (value for [Code] in ([ID_TYPE],[CUST_COMPANY_RELATIONSHIP],[JOB_POSITION],[GENDER]) )as unpiv) as mastername JOIN REF_MASTER_LOS rf on rf.REF_MASTER_Code = mastername.value WHERE rf.IS_ACTIVE = '1') AS ref PIVOT (MAX(ref.REF_MASTER_NAME) for [Code] in ([ID_TYPE],[CUST_COMPANY_RELATIONSHIP],[JOB_POSITION],[GENDER])) as piv"), {  row ->

			ResultSetMetaData rsmd = row.getMetaData()
			colmcount = rsmd.getColumnCount()


			for(i = 0 ; i < colmcount ; i++){
				contactinfo = (row[i])
				listcontact.add(contactinfo)
			}
		})
		return listcontact
	}

	//get NAP4 financial data company Store DB
	@Keyword
	public NAP4FinancialDataCompanyStoreData (Sql instance, String appno, String name, String date){
		String financialdata
		ArrayList<String> listfindata = new ArrayList<>()
		instance.eachRow(("SELECT ISNULL(FORMAT(GROSS_MONTHLY_INCOME_AMT, 'N0'),'') AS GROSSMONINCOME, ISNULL(FORMAT(GROSS_MONTHLY_EXPENSE_AMT, 'N0'),'') AS GROSSMONEXPENSE, ISNULL(FORMAT(RETURN_OF_INVESTMENT_PRCNT, 'N2'),'') AS RETURNOFINV, ISNULL(FORMAT(RETURN_OF_EQUITY_PRCNT, 'N2'),'') AS RETURNOFEQ, ISNULL(FORMAT(RETURN_OF_ASSET_PRCNT, 'N2'),'') AS RETURNOFASSET, ISNULL(FORMAT(PROFIT_MARGIN_PRCNT, 'N2'),'') AS PROFITMARGIN, ISNULL(FORMAT(CURRENT_RATIO_PRCNT, 'N2'),'') AS CURR_RATIO, ISNULL(FORMAT(DEBT_EQUITY_RATIO_PRCNT, 'N2'),'') AS DEBTEQRAT, ISNULL(FORMAT(INV_TURN_OVER_PRCNT, 'N2'),'') AS INVTURNOVER, ISNULL(FORMAT(AR_TURN_OVER_PRCNT, 'N2'),'') AS ARTURNOVER, ISNULL(FORMAT(GROWTH_PRCNT, 'N2'),'') AS GROWTH, ISNULL(FORMAT(WORKING_CAPITAL_AMT, 'N0'),'') AS WORK_CPTL, ISNULL(FORMAT(OTH_MONTHLY_INST_AMT, 'N0'),'') AS OTHMONINST, ISNULL(FORMAT(REVENUE, 'N0'),'') AS REV, ISNULL(FORMAT(OPR_COST, 'N0'),'') AS OPRCOST, ISNULL(FORMAT(PROFIT_BEFORE_TAX, 'N0'),'') AS PROFBFRTAX, ISNULL(FORMAT(CURR_ASSET, 'N0'),'') AS CURRASSET, ISNULL(FORMAT(NET_FIXED_ASSET, 'N0'),'') AS NETFIXEDASSET, ISNULL(FORMAT(TOTAL_ASSET, 'N0'),'') AS TOTALASSET, ISNULL(FORMAT(CURR_LIABLTS, 'N0'),'') AS CURRLIAB, ISNULL(FORMAT(LONG_TERM_LIABLTS, 'N0'),'') AS LONGTERMLIAB, ISNULL(FORMAT(SHAREHOLDER_EQUITY, 'N0'),'') AS SHRHLDREQ , ISNULL(FORMAT(CURR_RATIO, 'N0'),'') AS CURRRATIO, ISNULL(FORMAT(DATE_AS_OF, 'MM/dd/yyyy'),'') AS DATEASOF FROM APP_CUST_COMPANY_FIN_DATA accfd WITH(NOLOCK) JOIN APP_CUST ac WITH(NOLOCK) ON ac.APP_CUST_ID = accfd.APP_CUST_ID JOIN APP a WITH(NOLOCK) ON a.APP_ID = ac.APP_ID WHERE a.APP_NO = '"+ appno +"' AND ac.CUST_NAME = '"+ name +"' AND FORMAT(DATE_AS_OF , 'MM/dd/yyy') = '"+ date +"'"), {  row ->

			ResultSetMetaData rsmd = row.getMetaData()
			colmcount = rsmd.getColumnCount()


			for(i = 0 ; i < colmcount ; i++){
				financialdata = (row[i])
				listfindata.add(financialdata)
			}
		})
		return listfindata
	}

	//get NAP4 financial attr company Store DB
	@Keyword
	public NAP4FinancialAttrCompanyStoreData (Sql instance, String appno, String name){
		String financialattr
		ArrayList <String> listfinancialattr = new ArrayList<String>()
		instance.eachRow(("SELECT ISNULL(ATTR_VALUE,'') AS ATTR_FIN FROM APP_CUST_FIN_DATA_ATTR_CONTENT acfdac WITH(NOLOCK) JOIN APP_CUST ac WITH(NOLOCK) ON ac.APP_CUST_ID = acfdac.APP_CUST_ID JOIN APP a WITH(NOLOCK) ON a.APP_ID = ac.APP_ID WHERE a.APP_NO = '"+ appno +"' AND ac.CUST_NAME = '"+ name +"'"), {  row ->

			ResultSetMetaData rsmd = row.getMetaData()
			colmcount = rsmd.getColumnCount()


			for(i = 0 ; i < colmcount ; i++){
				financialattr = (row[i])
				listfinancialattr.add(financialattr)
			}
		})
		return listfinancialattr
	}

	//get NAP4 bank acc company Store DB
	@Keyword
	public NAP4FinDataBankAccCompanyStoreData (Sql instance, String appno, String name, String bankno){
		String bankacc
		ArrayList <String> listbankacc = new ArrayList<String>()
		instance.eachRow(("SELECT ISNULL(acba.BANK_CODE,'') AS CODE, ISNULL(acba.BANK_BRANCH,'') AS BRANCH, ISNULL(acba.BANK_ACC_NAME,'') AS ACCNAME, ISNULL(acba.BANK_ACC_NO,'') AS ACCNO, CASE WHEN acba.IS_DEFAULT = 1 THEN 'YES' ELSE 'NO' END AS ISDEF, CASE WHEN acba.IS_ACTIVE = 1 THEN 'YES' ELSE 'NO' END AS ISACT, ISNULL(FORMAT(acba.BEG_BALANCE_AMT, 'N0'),'') AS BEG_BAL_AMT FROM APP_CUST_BANK_ACC acba WITH(NOLOCK) JOIN APP_CUST_COMPANY acp WITH(NOLOCK) ON acba.APP_CUST_ID = acp.APP_CUST_ID JOIN APP_CUST ac WITH(NOLOCK) ON ac.APP_CUST_ID = acba.APP_CUST_ID JOIN APP a WITH(NOLOCK) ON a.APP_ID = ac.APP_ID WHERE a.APP_NO = '"+ appno +"' AND ac.CUST_NAME = '"+ name +"' AND acba.BANK_ACC_NO = '"+ bankno +"' GROUP BY acba.BANK_CODE, acba.BANK_BRANCH, acba.BANK_ACC_NAME, acba.BANK_ACC_NO, acba.IS_DEFAULT, acba.IS_ACTIVE, acba.BEG_BALANCE_AMT"), {  row ->

			ResultSetMetaData rsmd = row.getMetaData()
			colmcount = rsmd.getColumnCount()


			for(i = 0 ; i < colmcount ; i++){
				bankacc = (row[i])
				listbankacc.add(bankacc)
			}


		})
		return listbankacc
	}

	//get NAP4 bank statement company store db
	@Keyword
	public NAP4FinancialCheckBankStatCompanyStoreData (Sql instance, String appno, String name, String accno){
		String bankstat
		instance.eachRow(("SELECT ISNULL(acba.IS_BANK_STMNT,'') AS IS_BANK_STMNT FROM APP_CUST_BANK_ACC acba WITH(NOLOCK) JOIN APP_CUST_COMPANY acp WITH(NOLOCK) ON acba.APP_CUST_ID = acp.APP_CUST_ID JOIN APP_CUST ac WITH(NOLOCK) ON ac.APP_CUST_ID = acba.APP_CUST_ID JOIN APP a WITH(NOLOCK) ON a.APP_ID = ac.APP_ID JOIN APP_CUST_BANK_STMNT bs WITH(NOLOCK) ON acba.APP_CUST_BANK_ACC_ID = bs.APP_CUST_BANK_ACC_ID WHERE a.APP_NO = '"+ appno +"' AND ac.CUST_NAME = '"+ name +"' AND acba.BANK_ACC_NO = '"+ accno +"' GROUP BY acba.IS_BANK_STMNT"), {  row ->

			bankstat = (row[0])

		})
		return bankstat
	}

	//get NAP4 bank statement info Store DB
	@Keyword
	public NAP4BankStatDataCompanyStoreData (Sql instance, String appno, String name, String accno){
		String bankstatdata
		ArrayList <String> listbankstatdata = new ArrayList<String>()
		instance.eachRow(("SELECT ISNULL(acbs.MONTH,'') AS MONTH ,ISNULL(acbs.YEAR,'') AS YEAR, ISNULL(acbs.DEBIT_TRX_COUNT,'') AS DEBTRXCOUNT, ISNULL(FORMAT(acbs.DEBIT_AMT, 'N0'),'') AS DEBAMT, ISNULL(acbs.CREDIT_TRX_COUNT,'') AS CREDTRXCOUNT, ISNULL(FORMAT(acbs.CREDIT_AMT, 'N0'),'') AS CREDAMT FROM APP_CUST_BANK_ACC acba WITH(NOLOCK) JOIN APP_CUST_COMPANY acp WITH(NOLOCK) ON acba.APP_CUST_ID = acp.APP_CUST_ID JOIN APP_CUST ac ON ac.APP_CUST_ID = acba.APP_CUST_ID JOIN APP a WITH(NOLOCK) ON a.APP_ID = ac.APP_ID JOIN APP_CUST_BANK_STMNT acbs WITH(NOLOCK) ON acba.APP_CUST_BANK_ACC_ID = acbs.APP_CUST_BANK_ACC_ID WHERE a.APP_NO = '"+ appno +"' AND ac.CUST_NAME = '"+ name +"' AND acba.BANK_ACC_NO = '"+ accno +"'"), {  row ->

			ResultSetMetaData rsmd = row.getMetaData()
			colmcount = rsmd.getColumnCount()


			for(i = 0 ; i < colmcount ; i++){
				bankstatdata = (row[i])
				listbankstatdata.add(bankstatdata)
			}


		})
		return listbankstatdata
	}

	//get NAP4 legal doc company Store DB
	@Keyword
	public NAP4LegalDocStoreData (Sql instance, String appno, String name){
		String legaldoc
		ArrayList <String> listlegaldocdata = new ArrayList<String>()
		instance.eachRow(("SELECT ISNULL(MR_LEGAL_DOC_TYPE_CODE,'') AS LEGALDOCTYPE , ISNULL(DOC_NO,'') AS DOCNO, ISNULL(FORMAT(accld.DOC_DT, 'MM/dd/yyyy'),'') AS DOCDATE, ISNULL(FORMAT(DOC_EXPIRED_DT, 'MM/dd/yyyy'),'') AS DOCEXPDATE, ISNULL(NOTARY_NAME,'') AS NOTARYNAME, ISNULL(NOTARY_LOCATION,'') AS NOTARYLOC, ISNULL(DOC_NOTES,'') AS DOCNOTE FROM APP_CUST_COMPANY_LEGAL_DOC accld WITH(NOLOCK) JOIN APP_CUST_COMPANY acc WITH(NOLOCK) ON acc.APP_CUST_COMPANY_ID = accld.APP_CUST_COMPANY_ID JOIN APP_CUST ac WITH(NOLOCK) ON ac.APP_CUST_ID = acc.APP_CUST_ID JOIN APP a WITH(NOLOCK) ON a.APP_ID = ac.APP_ID WHERE a.APP_NO = '"+ appno +"' AND ac.CUST_NAME = '"+ name +"' ORDER BY accld.APP_CUST_COMPANY_LEGAL_DOC_ID"), {  row ->

			ResultSetMetaData rsmd = row.getMetaData()
			colmcount = rsmd.getColumnCount()


			for(i = 0 ; i < colmcount ; i++){
				legaldoc = (row[i])
				listlegaldocdata.add(legaldoc)
			}
		})
		return listlegaldocdata
	}

	//keyword check total insurance
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

	//keyword check Return Handling Header Table setelah return dari comm / rsv
	@Keyword
	public checkReturnHandlingH(Sql instance, String appno){
		String returnhandling
		ArrayList <String> listStat = new ArrayList<String>()
		instance.eachRow(("SELECT TOP 1 ISNULL(rhh.RETURN_FROM_TRX_TYPE, '') AS [APP_STEP] , ISNULL(rsl.DESCR, '') AS [RETURN_HANDLING_H_STAT] , ISNULL(rrl.REF_REASON_NAME, '') AS [REASON], ISNULL(RETURN_NOTES, '') AS [NOTES] from RETURN_HANDLING_H rhh WITH(NOLOCK) JOIN APP a WITH(NOLOCK) ON rhh.APP_ID = a.APP_ID JOIN REF_REASON_LOS rrl WITH(NOLOCK) ON rhh.RETURN_H_REASON = rrl.REF_REASON_CODE JOIN REF_STATUS_LOS rsl WITH(NOLOCK) ON rsl.REF_STATUS_CODE = rhh.RETURN_H_STATUS where APP_NO = '"+ appno +"' ORDER BY rhh.RETURN_HANDLING_H_ID desc"), {  row ->

			ResultSetMetaData rsmd = row.getMetaData()
			colmcount = rsmd.getColumnCount()


			for(i = 0 ; i < colmcount ; i++){
				returnhandling = (row[i])
				listStat.add(returnhandling)
			}
		})
		return listStat
	}

	//keyword check Return Handling Detail Table setelah return dari comm / rsv
	@Keyword
	public checkReturnHandlingD(Sql instance, String appno){
		String returnhandling
		ArrayList <String> listStat = new ArrayList<String>()
		instance.eachRow(("SELECT TOP 1 ISNULL(rml.REF_MASTER_NAME, '') AS RTNTASK, ISNULL(RETURN_HANDLING_NOTES, '') AS NOTES, ISNULL(rsl.DESCR, '') AS STAT FROM RETURN_HANDLING_D rhd JOIN RETURN_HANDLING_H rhh WITH(NOLOCK) ON rhd.RETURN_HANDLING_H_ID = rhh.RETURN_HANDLING_H_ID JOIN APP a WITH(NOLOCK) ON rhh.APP_ID = a.APP_ID JOIN REF_MASTER_LOS rml WITH(NOLOCK) ON rml.REF_MASTER_CODE = rhd.MR_RETURN_TASK_CODE JOIN REF_STATUS_LOS rsl WITH(NOLOCK) ON rsl.REF_STATUS_CODE = rhd.RETURN_STAT WHERE APP_NO = '"+ appno +"' AND rml.REF_MASTER_TYPE_CODE = 'RETURN_TASK_CF4W' ORDER BY rhh.RETURN_HANDLING_H_ID desc"), {  row ->

			ResultSetMetaData rsmd = row.getMetaData()
			colmcount = rsmd.getColumnCount()


			for(i = 0 ; i < colmcount ; i++){
				returnhandling = (row[i])
				listStat.add(returnhandling)
			}
		})
		return listStat
	}

	//keyword check Return Handling Header Table setelah Edit Application
	@Keyword
	public checkStatusDoneH(Sql instance, String appno){
		String returnhandling

		instance.eachRow(("SELECT TOP 1 rsl.DESCR from RETURN_HANDLING_H rhh WITH(NOLOCK) JOIN APP a WITH(NOLOCK) ON rhh.APP_ID = a.APP_ID JOIN REF_STATUS_LOS rsl WITH(NOLOCK) ON rhh.RETURN_H_STATUS = rsl.REF_STATUS_CODE where APP_NO = '"+ appno +"' ORDER BY rhh.RETURN_HANDLING_H_ID desc"), {  row ->

			returnhandling = (row[0])

		})
		return returnhandling
	}

	//keyword check Return Handling Detail Table setelah Edit Application
	@Keyword
	public checkStatusDoneD(Sql instance, String appno){
		String returnhandling
		ArrayList <String> listStat = new ArrayList<String>()
		instance.eachRow(("SELECT TOP 1 ISNULL(rsl.DESCR, '') AS STAT, ISNULL(RETURN_HANDLING_EXEC_NOTES, '') AS EXCECNOTES FROM RETURN_HANDLING_D rhd JOIN RETURN_HANDLING_H rhh WITH(NOLOCK) ON rhd.RETURN_HANDLING_H_ID = rhh.RETURN_HANDLING_H_ID JOIN APP a WITH(NOLOCK) ON rhh.APP_ID = a.APP_ID JOIN REF_STATUS_LOS rsl WITH(NOLOCK) ON rsl.REF_STATUS_CODE = rhd.RETURN_STAT WHERE APP_NO = '"+ appno +"' ORDER BY rhh.RETURN_HANDLING_H_ID desc"), {  row ->

			ResultSetMetaData rsmd = row.getMetaData()
			colmcount = rsmd.getColumnCount()


			for(i = 0 ; i < colmcount ; i++){
				returnhandling = (row[i])
				listStat.add(returnhandling)
			}
		})
		return listStat
	}
}
