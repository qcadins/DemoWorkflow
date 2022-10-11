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

public class CustomerDataVerif {

	@Keyword
	public CustomerDataPersonal (Sql instance, String name, String idtype, String custmodel){
		String customerdata
		instance.eachRow(("SELECT Cust_name AS HEADER, BIRTH_PLACE AS HEADER, MR_ID_TYPE_CODE AS HEADER, ID_EXPIRED_DT AS HEADER, MR_MARITAL_STAT_CODE AS HEADER, MOBILE_PHN_NO_1 AS HEADER, MR_CUST_MODEL_CODE AS HEADER, MR_GENDER_CODE AS HEADER, FORMAT(BIRTH_DT, 'yyyy-MM-dd') AS HEADER, ID_NO AS HEADER, TAX_ID_NO AS HEADER, MOTHER_MAIDEN_NAME AS HEADER, EMAIL_1 AS HEADER, ADDR AS HEADER, AREA_CODE_4 AS HEADER, AREA_CODE_3 AS HEADER, ZIPCODE AS HEADER, AREA_CODE_2 AS HEADER, AREA_CODE_1 AS HEADER, CITY AS HEADER, MR_BUILDING_OWNERSHIP_CODE AS HEADER from CUST cust WITH(NOLOCK) join cust_addr addr on cust.CUST_ID = addr.CUST_ID JOIN cust_personal cp on cust.cust_ID = cp.CUST_ID where cust.cust_name = '"+ name +"' and MR_ID_TYPE_CODE = '"+ idtype +"' AND MR_CUST_MODEL_CODE = '"+ custmodel +"' AND MR_CUST_ADDR_TYPE_CODE = 'legal' GROUP BY Cust_name, BIRTH_PLACE, MR_ID_TYPE_CODE, ID_EXPIRED_DT, MR_MARITAL_STAT_CODE, MOBILE_PHN_NO_1, MR_CUST_MODEL_CODE, MR_GENDER_CODE, FORMAT(BIRTH_DT, 'yyyy-MM-dd'), ID_NO, TAX_ID_NO, MOTHER_MAIDEN_NAME, EMAIL_1, ADDR, AREA_CODE_4, AREA_CODE_3, ZIPCODE, AREA_CODE_2, AREA_CODE_1, CITY, MR_BUILDING_OWNERSHIP_CODE"), {  row ->

			customerdata = (row)
		})
		return customerdata
	}

	@Keyword
	public CustomerDataCompany (Sql instance, String name, String companytype){
		String customerdata
		instance.eachRow(("SELECT Cust_name AS HEADER, TAX_ID_NO AS HEADER, MR_COMPANY_TYPE_CODE AS HEADER ,MR_CUST_MODEL_CODE AS HEADER, ADDR AS HEADER, AREA_CODE_4 AS HEADER, AREA_CODE_3 AS HEADER, ZIPCODE AS HEADER, AREA_CODE_2 AS HEADER, AREA_CODE_1 AS HEADER, CITY AS HEADER, MR_BUILDING_OWNERSHIP_CODE AS HEADER from CUST cust WITH(NOLOCK) join cust_addr addr on cust.CUST_ID = addr.CUST_ID JOIN CUST_COMPANY cc on cust.cust_ID = cc.CUST_ID where cust.cust_name = '"+ name +"' AND MR_COMPANY_TYPE_CODE = '"+ companytype +"' AND MR_CUST_ADDR_TYPE_CODE = 'legal' GROUP BY Cust_name, TAX_ID_NO, MR_COMPANY_TYPE_CODE ,MR_CUST_MODEL_CODE, ADDR, AREA_CODE_4, AREA_CODE_3, ZIPCODE, AREA_CODE_2, AREA_CODE_1, CITY, MR_BUILDING_OWNERSHIP_CODE"), {  row ->

			customerdata = (row)
		})
		return customerdata
	}

	@Keyword
	public FamilyDataPersonal (Sql instance, String name, String idno){
		String customerdata
		instance.eachRow(("SELECT Cust_name AS HEADER, BIRTH_PLACE AS HEADER, MR_ID_TYPE_CODE AS HEADER, ID_EXPIRED_DT AS HEADER, MR_MARITAL_STAT_CODE AS HEADER, MOBILE_PHN_NO_1 AS HEADER, MR_CUST_MODEL_CODE AS HEADER, MR_GENDER_CODE AS HEADER, FORMAT(BIRTH_DT, 'yyyy-MM-dd') AS HEADER, ID_NO AS HEADER, TAX_ID_NO AS HEADER, MOTHER_MAIDEN_NAME AS HEADER, EMAIL_1 AS HEADER, MR_NATIONALITY_CODE AS HEADER, UPPER(COUNTRY_NAME) AS HEADER, ADDR AS HEADER, AREA_CODE_4 AS HEADER, AREA_CODE_3 AS HEADER, ZIPCODE AS HEADER, AREA_CODE_2 AS HEADER, AREA_CODE_1 AS HEADER, CITY AS HEADER, MR_BUILDING_OWNERSHIP_CODE AS HEADER from CUST cust WITH(NOLOCK) join cust_addr addr on cust.CUST_ID = addr.CUST_ID join cust_personal cp on cust.cust_ID = cp.CUST_ID join REF_COUNTRY country on cp.WNA_COUNTRY_CODE = country.COUNTRY_CODE where cust.cust_name = '"+ name + "' AND ID_NO = '"+ idno +"' AND MR_CUST_ADDR_TYPE_CODE = 'legal' GROUP BY Cust_name , BIRTH_PLACE , MR_ID_TYPE_CODE , ID_EXPIRED_DT , MR_MARITAL_STAT_CODE , MOBILE_PHN_NO_1 , MR_CUST_MODEL_CODE , MR_GENDER_CODE , FORMAT(BIRTH_DT, 'yyyy-MM-dd') , ID_NO , TAX_ID_NO , MOTHER_MAIDEN_NAME , EMAIL_1 , MR_NATIONALITY_CODE , UPPER(COUNTRY_NAME) , ADDR , AREA_CODE_4 , AREA_CODE_3 , ZIPCODE , AREA_CODE_2 , AREA_CODE_1 , CITY , MR_BUILDING_OWNERSHIP_CODE"), {  row ->

			customerdata = (row)
		})
		return customerdata
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
		println(appno)
		//instance.eachRow(("SELECT b.PROD_OFFERING_CODE AS HEADER, Cust_name AS HEADER, BIRTH_PLACE AS HEADER, h.REF_MASTER_NAME AS HEADER, FORMAT(ID_EXPIRED_DT, 'MM/dd/yyyy') AS HEADER, MR_MARITAL_STAT_CODE AS HEADER, MOBILE_PHN_NO_1 AS HEADER, f.REF_MASTER_NAME AS HEADER, MR_GENDER_CODE AS HEADER, FORMAT(BIRTH_DT, 'MM/dd/yyyy') AS HEADER, ID_NO AS HEADER, TAX_ID_NO AS HEADER, MOTHER_MAIDEN_NAME AS HEADER, EMAIL_1 AS HEADER, e.ATTR_VALUE AS HEADER, SUBQ.ATTR_VALUE AS HEADER, ADDR AS HEADER, AREA_CODE_4 AS HEADER, AREA_CODE_3 AS HEADER, ZIPCODE AS HEADER, AREA_CODE_2 AS HEADER, AREA_CODE_1 AS HEADER, CITY AS HEADER, g.REF_MASTER_NAME AS HEADER FROM APP_CUST a JOIN app b ON a.APP_ID = b.APP_ID JOIN APP_CUST_PERSONAL c ON a.APP_CUST_ID = c.APP_CUST_ID JOIN APP_CUST_ADDR d ON a.APP_CUST_ID = d.APP_CUST_ID JOIN APP_CUST_ATTR_CONTENT e ON e.APP_CUST_ID = a.APP_CUST_ID JOIN REF_MASTER_LOS f ON a.MR_CUST_MODEL_CODE = f.REF_MASTER_CODE JOIN REF_MASTER_LOS g ON d.MR_HOUSE_OWNERSHIP_CODE = g.REF_MASTER_CODE JOIN REF_MASTER_LOS h ON a.MR_ID_TYPE_CODE = h.REF_MASTER_CODE, (SELECT ATTR_VALUE, a.APP_ID FROM APP_CUST_ATTR_CONTENT g JOIN APP_CUST a ON g.APP_CUST_ID = a.APP_CUST_ID JOIN app b ON a.APP_ID = b.APP_ID WHERE APP_NO = '"+ appno +"'AND IS_CUSTOMER = 1 AND g.REF_ATTR_CODE = 'AUTH_AML') as SUBQ WHERE a.APP_ID = SUBQ.APP_ID AND IS_CUSTOMER = 1 AND MR_CUST_ADDR_TYPE_CODE = 'LEGAL' AND MR_CUST_TYPE_CODE = 'PERSONAL' AND e.REF_ATTR_CODE = 'AML_CUST_DEPARTMENT' AND f.REF_MASTER_TYPE_CODE = 'CUST_MODEL' AND g.REF_MASTER_TYPE_CODE = 'BUILDING_OWNERSHIP' AND h.REF_MASTER_TYPE_CODE = 'ID_TYPE'"), {  row ->
		instance.eachRow(("SELECT [ProductOffering] AS HEADER, [CustName] AS HEADER, [BirthPlace] AS HEADER, [ID_TYPE] AS HEADER,[IDExpiredDate] AS HEADER, [MaritalStatus] AS HEADER, [MobilePHN1] AS HEADER, [CUST_MODEL] AS HEADER, [Gender] AS HEADER, [BirthDate] AS HEADER, [IdNo] AS HEADER, [TaxID] AS HEADER, [MotherMaidenName] AS HEADER,[Email1] AS HEADER, [Addr] AS HEADER, [RT] AS HEADER, [RW] AS HEADER, [Zipcode] AS HEADER, [Kecamatan] AS HEADER, [Kelurahan] AS HEADER, [Kota] AS HEADER, [BUILDING_OWNERSHIP] AS HEADER FROM (SELECT [ProductOffering], [CustName], [BirthPlace], [IDExpiredDate], [MaritalStatus], [MobilePHN1], [Gender], [BirthDate], [IdNo], [TaxID], [MotherMaidenName], [Email1], [Addr], [RT], [RW], [Zipcode], [Kecamatan], [Kelurahan], [Kota], mastername.Code, REF_MASTER_NAME FROM (select [ProductOffering], [CustName], [BirthPlace], [IDExpiredDate], [MaritalStatus], [MobilePHN1], [Gender], [BirthDate], [IdNo], [TaxID], [MotherMaidenName], [Email1], [Addr], [RT], [RW], [Zipcode], [Kecamatan], [Kelurahan], [Kota], [Code], value FROM (SELECT app.PROD_OFFERING_CODE AS [ProductOffering], Cust_name AS [CustName], BIRTH_PLACE AS [BirthPlace], ac.MR_ID_TYPE_CODE AS 'ID_TYPE', FORMAT(ID_EXPIRED_DT, 'MM/dd/yyyy') AS [IDExpiredDate], MR_MARITAL_STAT_CODE AS [MaritalStatus], MOBILE_PHN_NO_1 AS [MobilePHN1], ac.MR_CUST_MODEL_CODE AS 'CUST_MODEL', MR_GENDER_CODE AS [Gender], FORMAT(BIRTH_DT, 'MM/dd/yyyy') AS [BirthDate], ID_NO AS [IdNo], TAX_ID_NO AS [TaxID], MOTHER_MAIDEN_NAME AS [MotherMaidenName], EMAIL_1 AS [Email1],  ADDR AS [Addr], AREA_CODE_4 AS [RT], AREA_CODE_3 AS [RW], ZIPCODE AS [Zipcode], AREA_CODE_2 AS [Kecamatan], AREA_CODE_1 AS [Kelurahan], CITY AS [Kota], addr.MR_HOUSE_OWNERSHIP_CODE AS 'BUILDING_OWNERSHIP' FROM APP_CUST ac JOIN APP app ON ac.APP_ID = app.APP_ID JOIN APP_CUST_PERSONAL acp ON ac.APP_CUST_ID = acp.APP_CUST_ID JOIN APP_CUST_ADDR addr ON ac.APP_CUST_ID = addr.APP_CUST_ID WHERE ac.IS_CUSTOMER = 1 AND APP_NO = '"+ appno +"' AND MR_CUST_ADDR_TYPE_CODE = 'LEGAL' AND MR_CUST_TYPE_CODE = 'PERSONAL') as Orig unpivot (value for [Code] in ([ID_TYPE],[CUST_MODEL],[BUILDING_OWNERSHIP]))as unpiv) as mastername JOIN REF_MASTER_LOS rf on rf.REF_MASTER_Code = mastername.value) AS ref PIVOT (MAX(ref.REF_MASTER_NAME) for [Code] in ([ID_TYPE],[CUST_MODEL],[BUILDING_OWNERSHIP])) as piv"), {  row ->
			customerdata = (row)
			println(row)
		})
		return customerdata
	}

	@Keyword
	public FamilyDataStoreDBPersonal (Sql instance, String appno, String name){
		String familydata
		//		instance.eachRow(("SELECT MR_CUST_RELATIONSHIP_CODE AS HEADER ,Cust_name AS HEADER, BIRTH_PLACE AS HEADER, g.REF_MASTER_NAME AS HEADER, FORMAT(ID_EXPIRED_DT, 'MM/dd/yyyy') AS HEADER, MR_MARITAL_STAT_CODE AS HEADER, MOBILE_PHN_NO_1 AS HEADER, h.REF_MASTER_NAME AS HEADER, e.MR_PROFESSION_CODE AS HEADER, FORMAT(e.ESTABLISHMENT_DT, 'MM/dd/yyyy') AS HEADER, MR_GENDER_CODE AS HEADER, FORMAT(BIRTH_DT, 'MM/dd/yyyy') AS HEADER, ID_NO AS HEADER, TAX_ID_NO AS HEADER, MOTHER_MAIDEN_NAME AS HEADER, EMAIL_1 AS HEADER, MR_NATIONALITY_CODE AS HEADER, NATIONALITY_COUNTRY_CODE AS HEADER, MR_JOB_POSITION_CODE AS HEADER, f.ATTR_VALUE AS HEADER, SUBQ.ATTR_VALUE AS HEADER, ADDR AS HEADER, AREA_CODE_4 AS HEADER, AREA_CODE_3 AS HEADER, ZIPCODE AS HEADER, AREA_CODE_2 AS HEADER, AREA_CODE_1 AS HEADER, CITY AS HEADER, i.REF_MASTER_NAME AS HEADER FROM APP_CUST a JOIN app b ON a.APP_ID = b.APP_ID JOIN APP_CUST_PERSONAL c ON a.APP_CUST_ID = c.APP_CUST_ID JOIN APP_CUST_ADDR d ON a.APP_CUST_ID = d.APP_CUST_ID JOIN APP_CUST_PERSONAL_JOB_DATA e ON c.APP_CUST_PERSONAL_ID = e.APP_CUST_PERSONAL_ID JOIN APP_CUST_ATTR_CONTENT f ON f.APP_CUST_ID = a.APP_CUST_ID JOIN REF_MASTER_LOS g ON a.MR_ID_TYPE_CODE = g.REF_MASTER_CODE JOIN REF_MASTER_LOS h ON a.MR_CUST_MODEL_CODE = h.REF_MASTER_CODE JOIN REF_MASTER_LOS i ON d.MR_HOUSE_OWNERSHIP_CODE = i.REF_MASTER_CODE, (SELECT ATTR_VALUE, a.APP_ID FROM APP_CUST_ATTR_CONTENT g JOIN APP_CUST a ON g.APP_CUST_ID = a.APP_CUST_ID JOIN app b ON a.APP_ID = b.APP_ID WHERE APP_NO = '"+ appno +"' AND IS_FAMILY = 1 AND g.REF_ATTR_CODE = 'AUTH_AML') as SUBQ where a.APP_ID = SUBQ.APP_ID AND IS_FAMILY = 1 AND MR_CUST_ADDR_TYPE_CODE = 'LEGAL' AND MR_CUST_TYPE_CODE = 'PERSONAL' AND f.REF_ATTR_CODE = 'AML_CUST_DEPARTMENT' AND g.REF_MASTER_TYPE_CODE = 'ID_TYPE' AND h.REF_MASTER_TYPE_CODE = 'CUST_MODEL' AND i.REF_MASTER_TYPE_CODE = 'BUILDING_OWNERSHIP'"), {  row ->
		instance.eachRow(("SELECT [Relationship] AS HEADER, [CustName] AS HEADER, [BirthPlace] AS HEADER, [ID_TYPE] AS HEADER,[IDExpiredDate] AS HEADER, [MaritalStatus] AS HEADER, [MobilePHN1] AS HEADER, [CUST_MODEL] AS HEADER, [ProfessionCode] AS HEADER, [EstablishmentDate] AS HEADER, [Gender] AS HEADER, [BirthDate] AS HEADER, [Idno] AS HEADER, [TaxID] AS HEADER, [MotherMaidenName] AS HEADER, [Email1] AS HEADER, [NationalityCode] AS HEADER, [CountryCode] AS HEADER, [JobPositionCode] AS HEADER, [Addr] AS HEADER, [RT] AS HEADER, [RW] AS HEADER, [Zipcode] AS HEADER, [Kecamatan] AS HEADER, [Kelurahan] AS HEADER, [Kota] AS HEADER, [BUILDING_OWNERSHIP] AS HEADER FROM (SELECT [Relationship], [CustName], [BirthPlace], [IDExpiredDate], [MaritalStatus], [MobilePHN1], [ProfessionCode], [EstablishmentDate], [Gender], [BirthDate], [Idno], [TaxID], [MotherMaidenName], [Email1], [NationalityCode], [CountryCode], [JobPositionCode], [Addr], [RT], [RW], [Zipcode], [Kecamatan], [Kelurahan], [Kota], mastername.Code, REF_MASTER_NAME FROM (select [Relationship], [CustName], [BirthPlace], [IDExpiredDate], [MaritalStatus], [MobilePHN1], [ProfessionCode], [EstablishmentDate], [Gender], [BirthDate], [Idno], [TaxID], [MotherMaidenName], [Email1], [NationalityCode], [CountryCode], [JobPositionCode], [Addr], [RT], [RW], [Zipcode], [Kecamatan], [Kelurahan], [Kota], [Code], value FROM (SELECT MR_CUST_RELATIONSHIP_CODE AS [Relationship] ,Cust_name AS [CustName], BIRTH_PLACE AS [BirthPlace], ac.MR_ID_TYPE_CODE AS 'ID_TYPE', FORMAT(ID_EXPIRED_DT, 'MM/dd/yyyy') AS [IDExpiredDate], MR_MARITAL_STAT_CODE AS [MaritalStatus], MOBILE_PHN_NO_1 AS [MobilePHN1], MR_CUST_MODEL_CODE AS 'CUST_MODEL', job.MR_PROFESSION_CODE AS [ProfessionCode], FORMAT(job.ESTABLISHMENT_DT, 'MM/dd/yyyy') AS [EstablishmentDate], MR_GENDER_CODE AS [Gender], FORMAT(BIRTH_DT, 'MM/dd/yyyy') AS [BirthDate], ID_NO AS [Idno], TAX_ID_NO AS [TaxID], MOTHER_MAIDEN_NAME AS [MotherMaidenName], EMAIL_1 AS [Email1], MR_NATIONALITY_CODE AS [NationalityCode], NATIONALITY_COUNTRY_CODE AS [CountryCode], MR_JOB_POSITION_CODE AS [JobPositionCode], ADDR AS [Addr], AREA_CODE_4 AS [RT], AREA_CODE_3 AS [RW], ZIPCODE AS [Zipcode], AREA_CODE_2 AS [Kecamatan], AREA_CODE_1 AS [Kelurahan], CITY AS [Kota], addr.MR_HOUSE_OWNERSHIP_CODE AS 'BUILDING_OWNERSHIP' FROM APP_CUST ac JOIN app app ON ac.APP_ID = app.APP_ID JOIN APP_CUST_PERSONAL acp ON ac.APP_CUST_ID = acp.APP_CUST_ID JOIN APP_CUST_ADDR addr ON ac.APP_CUST_ID = addr.APP_CUST_ID JOIN APP_CUST_PERSONAL_JOB_DATA job ON acp.APP_CUST_PERSONAL_ID = job.APP_CUST_PERSONAL_ID where APP_NO = '"+ appno +"' AND IS_FAMILY = 1 AND Cust_name = '"+ name +"') as Orig unpivot (value for [Code] in ([ID_TYPE],[CUST_MODEL],[BUILDING_OWNERSHIP]))as unpiv) as mastername JOIN REF_MASTER_LOS rf on rf.REF_MASTER_Code = mastername.value) AS ref PIVOT (MAX(ref.REF_MASTER_NAME) for [Code] in ([ID_TYPE],[CUST_MODEL],[BUILDING_OWNERSHIP])) as piv"), {  row ->
			familydata = (row)
		})
		return familydata
	}

	@Keyword
	public GuarantorDataStoreDBPersonal (Sql instance, String appno, String name){
		String guarantordata
		//		instance.eachRow(("SELECT MR_CUST_RELATIONSHIP_CODE AS HEADER ,Cust_name AS HEADER, BIRTH_PLACE AS HEADER, f.REF_MASTER_NAME AS HEADER, FORMAT(ID_EXPIRED_DT, 'MM/dd/yyyy') AS HEADER, MR_MARITAL_STAT_CODE AS HEADER, MOBILE_PHN_NO_1 AS HEADER, g.REF_MASTER_NAME AS HEADER, MR_GENDER_CODE AS HEADER, FORMAT(BIRTH_DT, 'MM/dd/yyyy') AS HEADER, ID_NO AS HEADER, TAX_ID_NO AS HEADER, MOTHER_MAIDEN_NAME AS HEADER, EMAIL_1 AS HEADER, e.ATTR_VALUE AS HEADER, SUBQ.ATTR_VALUE AS HEADER, ADDR AS HEADER, AREA_CODE_4 AS HEADER, AREA_CODE_3 AS HEADER, ZIPCODE AS HEADER, AREA_CODE_2 AS HEADER, AREA_CODE_1 AS HEADER, CITY AS HEADER, h.REF_MASTER_NAME AS HEADER FROM APP_CUST a JOIN app b ON a.APP_ID = b.APP_ID JOIN APP_CUST_PERSONAL c ON a.APP_CUST_ID = c.APP_CUST_ID JOIN APP_CUST_ADDR d ON a.APP_CUST_ID = d.APP_CUST_ID JOIN APP_CUST_ATTR_CONTENT e ON e.APP_CUST_ID = a.APP_CUST_ID JOIN REF_MASTER_LOS f ON a.MR_ID_TYPE_CODE = f.REF_MASTER_CODE JOIN REF_MASTER_LOS g ON a.MR_CUST_MODEL_CODE = g.REF_MASTER_CODE JOIN REF_MASTER_LOS h ON d.MR_HOUSE_OWNERSHIP_CODE = h.REF_MASTER_CODE, (SELECT ATTR_VALUE, a.APP_ID FROM APP_CUST_ATTR_CONTENT g JOIN APP_CUST a ON g.APP_CUST_ID = a.APP_CUST_ID JOIN app b ON a.APP_ID = b.APP_ID WHERE APP_NO = '"+ appno +"' AND IS_GUARANTOR = 1 AND g.REF_ATTR_CODE = 'AUTH_AML') as SUBQ WHERE APP_NO = '" + appno +"' AND IS_GUARANTOR = 1 AND MR_CUST_ADDR_TYPE_CODE = 'LEGAL' AND MR_CUST_TYPE_CODE = 'PERSONAL' AND e.REF_ATTR_CODE = 'AML_CUST_DEPARTMENT' AND f.REF_MASTER_TYPE_CODE = 'ID_TYPE' AND g.REF_MASTER_TYPE_CODE = 'CUST_MODEL' AND h.REF_MASTER_TYPE_CODE = 'BUILDING_OWNERSHIP'"), {  row ->
		instance.eachRow(("SELECT [Relationship] AS HEADER, [CustName] AS HEADER, [BirthPlace] AS HEADER, [ID_TYPE] AS HEADER,[IDExpiredDate] AS HEADER, [MaritalStatus] AS HEADER, [MobilePHN1] AS HEADER, [CUST_MODEL] AS HEADER, [Gender] AS HEADER, [BirthDate] AS HEADER, [Idno] AS HEADER, [TaxID] AS HEADER, [MotherMaidenName] AS HEADER, [Email1] AS HEADER, [Addr] AS HEADER, [RT] AS HEADER, [RW] AS HEADER, [Zipcode] AS HEADER, [Kecamatan] AS HEADER, [Kelurahan] AS HEADER, [Kota] AS HEADER, [BUILDING_OWNERSHIP] AS HEADER FROM (SELECT [Relationship], [CustName], [BirthPlace], [IDExpiredDate], [MaritalStatus], [MobilePHN1], [Gender], [BirthDate], [Idno], [TaxID], [MotherMaidenName], [Email1], [Addr], [RT], [RW], [Zipcode], [Kecamatan], [Kelurahan], [Kota], mastername.Code, REF_MASTER_NAME FROM (select [Relationship], [CustName], [BirthPlace], [IDExpiredDate], [MaritalStatus], [MobilePHN1], [Gender], [BirthDate], [Idno], [TaxID], [MotherMaidenName], [Email1], [Addr], [RT], [RW], [Zipcode], [Kecamatan], [Kelurahan], [Kota], [Code], value FROM (SELECT MR_CUST_RELATIONSHIP_CODE AS [Relationship] ,Cust_name AS [CustName], BIRTH_PLACE AS [BirthPlace], ac.MR_ID_TYPE_CODE AS 'ID_TYPE', FORMAT(ID_EXPIRED_DT, 'MM/dd/yyyy') AS [IDExpiredDate], MR_MARITAL_STAT_CODE AS [MaritalStatus], MOBILE_PHN_NO_1 AS [MobilePHN1], MR_CUST_MODEL_CODE AS 'CUST_MODEL', MR_GENDER_CODE AS [Gender], FORMAT(BIRTH_DT, 'MM/dd/yyyy') AS [BirthDate], ID_NO AS [Idno], TAX_ID_NO AS [TaxID], MOTHER_MAIDEN_NAME AS [MotherMaidenName], EMAIL_1 AS [Email1], ADDR AS [Addr], AREA_CODE_4 AS [RT], AREA_CODE_3 AS [RW], ZIPCODE AS [Zipcode], AREA_CODE_2 AS [Kecamatan], AREA_CODE_1 AS [Kelurahan], CITY AS [Kota], addr.MR_HOUSE_OWNERSHIP_CODE AS 'BUILDING_OWNERSHIP' FROM APP_CUST ac WITH(NOLOCK) JOIN app app ON ac.APP_ID = app.APP_ID JOIN APP_CUST_PERSONAL acp ON ac.APP_CUST_ID = acp.APP_CUST_ID JOIN APP_CUST_ADDR addr ON ac.APP_CUST_ID = addr.APP_CUST_ID JOIN APP_CUST_PERSONAL_JOB_DATA job ON acp.APP_CUST_PERSONAL_ID = job.APP_CUST_PERSONAL_ID where APP_NO = '"+ appno +"' AND IS_GUARANTOR = 1 AND Cust_name = '"+ name +"') as Orig unpivot (value for [Code] in ([ID_TYPE],[CUST_MODEL],[BUILDING_OWNERSHIP]))as unpiv) as mastername JOIN REF_MASTER_LOS rf on rf.REF_MASTER_Code = mastername.value) AS ref PIVOT (MAX(ref.REF_MASTER_NAME) for [Code] in ([ID_TYPE],[CUST_MODEL],[BUILDING_OWNERSHIP])) as piv"), {  row ->
			guarantordata = (row)
		})
		return guarantordata
	}

	@Keyword
	public GuarantorDataStoreDBCompany (Sql instance, String appno){
		String guarantordata
		instance.eachRow(("SELECT e.REF_MASTER_NAME AS HEADER ,CUST_NAME AS HEADER ,TAX_ID_NO AS HEADER ,MR_COMPANY_TYPE_CODE AS HEADER ,MR_CUST_MODEL_CODE AS HEADER, ADDR AS HEADER, AREA_CODE_4 AS HEADER, AREA_CODE_3 AS HEADER, ZIPCODE AS HEADER, AREA_CODE_2 AS HEADER, AREA_CODE_1 AS HEADER, CITY AS HEADER, f.REF_MASTER_NAME AS HEADER FROM APP_CUST ac WITH(NOLOCK) JOIN app app ON ac.APP_ID = app.APP_ID JOIN APP_CUST_COMPANY acp ON ac.APP_CUST_ID = acp.APP_CUST_ID JOIN APP_CUST_ADDR addr ON ac.APP_CUST_ID = addr.APP_CUST_ID JOIN REF_MASTER_LOS e ON e.REF_MASTER_CODE = ac.MR_CUST_RELATIONSHIP_CODE JOIN REF_MASTER_LOS f ON f.REF_MASTER_CODE = addr.MR_HOUSE_OWNERSHIP_CODE where APP_NO = '"+ appno +"' AND IS_GUARANTOR = 1 AND MR_CUST_ADDR_TYPE_CODE = 'LEGAL' AND MR_CUST_TYPE_CODE = 'COMPANY' AND e.REF_MASTER_TYPE_CODE = 'GUAR_COMPANY_RELATIONSHIP' AND f.REF_MASTER_TYPE_CODE = 'BUILDING_OWNERSHIP'"), {  row ->
			guarantordata = (row)
		})
		return guarantordata
	}

	@Keyword
	public CustomerDataStoreDBCompany (Sql instance, String appno){
		String customerdata
		instance.eachRow(("SELECT CUST_NAME AS HEADER ,TAX_ID_NO AS HEADER ,MR_COMPANY_TYPE_CODE AS HEADER ,MR_CUST_MODEL_CODE AS HEADER, ADDR AS HEADER, AREA_CODE_4 AS HEADER, AREA_CODE_3 AS HEADER, ZIPCODE AS HEADER, AREA_CODE_2 AS HEADER, AREA_CODE_1 AS HEADER, CITY AS HEADER, rml.REF_MASTER_NAME AS HEADER FROM APP_CUST ac WITH(NOLOCK) JOIN app app ON ac.APP_ID = app.APP_ID JOIN APP_CUST_COMPANY acc ON ac.APP_CUST_ID = acc.APP_CUST_ID JOIN APP_CUST_ADDR addr ON ac.APP_CUST_ID = addr.APP_CUST_ID JOIN REF_MASTER_LOS rml ON rml.REF_MASTER_CODE = addr.MR_HOUSE_OWNERSHIP_CODE where APP_NO = '"+ appno +"' AND IS_CUSTOMER = 1 AND MR_CUST_ADDR_TYPE_CODE = 'LEGAL' AND MR_CUST_TYPE_CODE = 'COMPANY' AND rml.REF_MASTER_TYPE_CODE = 'BUILDING_OWNERSHIP'"), {  row ->

			customerdata = (row)
		})
		return customerdata
	}

	@Keyword
	public MSDataStoreDBCompany (Sql instance, String appno){
		String MSdata
		instance.eachRow(("SELECT CUST_NAME AS HEADER, TAX_ID_NO AS HEADER, MR_POSITION_SLIK_CODE AS HEADER, MR_COMPANY_TYPE_CODE AS HEADER, MR_CUST_MODEL_CODE AS HEADER,  FORMAT(SHARE_PRCNT, 'N0') AS HEADER, IS_ACTIVE AS HEADER, IS_OWNER AS HEADER, ADDR AS HEADER, AREA_CODE_3 AS HEADER, AREA_CODE_4 AS HEADER, ZIPCODE AS HEADER, AREA_CODE_1 AS HEADER, AREA_CODE_2 AS HEADER, CITY AS HEADER, MR_HOUSE_OWNERSHIP_CODE AS HEADER FROM APP_CUST ac WITH(NOLOCK) JOIN app app ON ac.APP_ID = app.APP_ID JOIN APP_CUST_COMPANY acc ON ac.APP_CUST_ID = acc.APP_CUST_ID JOIN APP_CUST_ADDR addr ON ac.APP_CUST_ID = addr.APP_CUST_ID JOIN APP_CUST_COMPANY_MGMNT_SHRHOLDER accMS ON ac.APP_CUST_ID = accMS.APP_CUST_ID where APP_NO = '"+ appno +"' AND IS_SHAREHOLDER = 1 AND MR_CUST_ADDR_TYPE_CODE = 'LEGAL' AND MR_CUST_TYPE_CODE = 'COMPANY'"), {  row ->

			MSdata = (row)
		})
		return MSdata
	}

	@Keyword
	public MSDataStoreDBPersonal (Sql instance, String appno, String name){
		String MSdata
		instance.eachRow(("SELECT [CustName] AS HEADER, [BirthPlace] AS HEADER, [ID_TYPE] AS HEADER,[IDExpiredDate] AS HEADER, [MaritalStatus] AS HEADER, [MobilePHN1] AS HEADER, [CUST_MODEL] AS HEADER, [ProffesionCode] AS HEADER, [PositionSLIK] AS HEADER, [EstablishmentDate] AS HEADER, [Gender] AS HEADER, [BirthDate] AS HEADER, [Idno] AS HEADER, [TaxID] AS HEADER, [MotherMaidenName] AS HEADER, [Email1] AS HEADER, [JobPosition] AS HEADER, [Sharepercent] AS HEADER, [isOwner] AS HEADER, [isActive] AS HEADER, [isSigner] AS HEADER, [DeptAML] AS HEADER, [AuthAML] AS HEADER, [Addr] AS HEADER, [RT] AS HEADER, [RW] AS HEADER, [Zipcode] AS HEADER, [Kecamatan] AS HEADER, [Kelurahan] AS HEADER, [Kota] AS HEADER, [BUILDING_OWNERSHIP] AS HEADER FROM (SELECT [CustName], [BirthPlace], [IDExpiredDate], [MaritalStatus], [MobilePHN1], [ProffesionCode], [PositionSLIK], [Gender], [EstablishmentDate], [BirthDate], [Idno], [TaxID], [MotherMaidenName], [Email1], [JobPosition], [Sharepercent], [isOwner], [isActive], [isSigner], [DeptAML], [AuthAML], [Addr], [RT], [RW], [Zipcode], [Kecamatan], [Kelurahan], [Kota], mastername.Code, REF_MASTER_NAME FROM (select [CustName], [BirthPlace], [IDExpiredDate], [MaritalStatus], [MobilePHN1], [ProffesionCode], [PositionSLIK], [Gender], [EstablishmentDate], [BirthDate], [Idno], [TaxID], [MotherMaidenName], [Email1], [JobPosition], [Sharepercent], [isOwner], [isActive], [isSigner], [DeptAML], [AuthAML], [Addr], [RT], [RW], [Zipcode], [Kecamatan], [Kelurahan], [Kota], [Code], value FROM (SELECT Cust_name AS [CustName], BIRTH_PLACE AS [BirthPlace], ac.MR_ID_TYPE_CODE AS 'ID_TYPE', FORMAT(ID_EXPIRED_DT, 'MM/dd/yyyy') AS [IDExpiredDate], MR_MARITAL_STAT_CODE AS [MaritalStatus], MOBILE_PHN_NO_1 AS [MobilePHN1], MR_CUST_MODEL_CODE AS 'CUST_MODEL', job.MR_PROFESSION_CODE AS [ProffesionCode], MR_POSITION_SLIK_CODE AS [PositionSLIK], FORMAT(accMS.ESTABLISHMENT_DT, 'MM/dd/yyyy') AS [EstablishmentDate], MR_GENDER_CODE AS [Gender], FORMAT(BIRTH_DT, 'MM/dd/yyyy') AS [BirthDate], ID_NO AS [Idno], TAX_ID_NO AS [TaxID], MOTHER_MAIDEN_NAME AS [MotherMaidenName], EMAIL_1 AS [Email1],  accMS.MR_JOB_POSITION_CODE AS [JobPosition], FORMAT(SHARE_PRCNT, 'N0') AS [Sharepercent], IS_OWNER AS [isOwner], accMS.IS_ACTIVE AS [isActive], IS_SIGNER AS [isSigner], attr.ATTR_VALUE AS [DeptAML], SUBQ.ATTR_VALUE AS [AuthAML], ADDR AS [Addr], AREA_CODE_4 AS [RT], AREA_CODE_3 AS [RW], ZIPCODE AS [Zipcode], AREA_CODE_2 AS [Kecamatan], AREA_CODE_1 AS [Kelurahan], CITY AS [Kota], addr.MR_HOUSE_OWNERSHIP_CODE AS 'BUILDING_OWNERSHIP' FROM APP_CUST ac WITH(NOLOCK) JOIN app app ON ac.APP_ID = app.APP_ID JOIN APP_CUST_PERSONAL acp ON ac.APP_CUST_ID = acp.APP_CUST_ID JOIN APP_CUST_ADDR addr ON ac.APP_CUST_ID = addr.APP_CUST_ID JOIN APP_CUST_COMPANY_MGMNT_SHRHOLDER accMS ON acp.APP_CUST_ID = accMS.APP_CUST_ID JOIN APP_CUST_PERSONAL_JOB_DATA job ON job.APP_CUST_PERSONAL_ID = acp.APP_CUST_PERSONAL_ID JOIN APP_CUST_ATTR_CONTENT attr ON attr.APP_CUST_ID = ac.APP_CUST_ID, (SELECT ATTR_VALUE, ac.APP_ID FROM APP_CUST_ATTR_CONTENT attr JOIN APP_CUST ac ON attr.APP_CUST_ID = ac.APP_CUST_ID JOIN app app ON ac.APP_ID = app.APP_ID WHERE APP_NO = '"+ appno +"' AND IS_SHAREHOLDER = 1 AND attr.REF_ATTR_CODE = 'AUTH_AML') as SUBQ where ac.APP_ID = SUBQ.APP_ID AND MR_CUST_ADDR_TYPE_CODE = 'LEGAL' AND accMS.MR_SHRHOLDER_TYPE_CODE = 'PERSONAL' AND CUST_NAME = '"+ name +"' AND attr.REF_ATTR_CODE = 'AML_CUST_DEPARTMENT') as Orig unpivot (value for [Code] in ([ID_TYPE],[CUST_MODEL],[BUILDING_OWNERSHIP]))as unpiv) as mastername JOIN REF_MASTER_LOS rf on rf.REF_MASTER_Code = mastername.value) AS ref PIVOT (MAX(ref.REF_MASTER_NAME) for [Code] in ([ID_TYPE],[CUST_MODEL],[BUILDING_OWNERSHIP])) as piv"), {  row ->

			MSdata = (row)
		})
		return MSdata
	}

	@Keyword
	public NAP2TabReferantorStoreDB (Sql instance, String appno){
		String referantordata
		ArrayList <String> listreferantor = new ArrayList<String>()
		instance.eachRow(("SELECT DISTINCT CASE WHEN MR_REFERANTOR_TYPE = 'AGENCY_PERSONAL' OR MR_REFERANTOR_TYPE = 'AGENCY_COMPANY' THEN 'AGENCY' WHEN MR_REFERANTOR_TYPE = 'CUSTOMER_PERSONAL' OR MR_REFERANTOR_TYPE = 'CUSTOMER_COMPANY' THEN 'CUSTOMER' ELSE 'MULTIFINANCE EMPLOYEE' END, c.REFERANTOR_NAME, d.BANK_NAME, c.BANK_ACC_NAME, c.BANK_ACC_NO, c.MR_TAX_CALC_METHOD FROM APP_CUST a JOIN APP b ON a.APP_ID = b.APP_ID JOIN APP_REFERANTOR c ON a.APP_ID = c.APP_ID JOIN REF_BANK_LOS d ON c.REF_BANK_CODE = d.BANK_CODE WHERE APP_NO = '"+ appno +"'"), {  row ->

			referantordata = (row[0])
			listreferantor.add(referantordata)
			referantordata = (row[1])
			listreferantor.add(referantordata)
			referantordata = (row[2])
			listreferantor.add(referantordata)
			referantordata = (row[3])
			listreferantor.add(referantordata)
			referantordata = (row[4])
			listreferantor.add(referantordata)
			referantordata = (row[5])
			listreferantor.add(referantordata)

		})
		return listreferantor
	}


	@Keyword
	public NAP2TabApplicationStoreDB (Sql instance, String appno){
		String applicationdata
		instance.eachRow(("SELECT DISTINCT d.APP_SRC_NAME AS HEADER, e.REF_MASTER_NAME AS HEADER, PAY_FREQ_CODE AS HEADER, TENOR AS HEADER, p.REF_MASTER_NAME AS HEADER, t.REF_MASTER_NAME AS HEADER, FLOATING_PERIOD_CODE AS HEADER, r.REF_MASTER_NAME AS HEADER, f.REF_MASTER_NAME AS HEADER, g.REF_MASTER_NAME AS HEADER, s.REF_MASTER_NAME AS HEADER, c.ADDR AS HEADER, c.AREA_CODE_3 AS HEADER, c.AREA_CODE_4 AS HEADER, c.ZIPCODE AS HEADER, c.AREA_CODE_1 AS HEADER, c.AREA_CODE_2 AS HEADER, c.CITY AS HEADER, c.PHN_AREA_1 AS HEADER, c.PHN_1 AS HEADER, c.PHN_EXT_1 AS HEADER, c.PHN_AREA_2 AS HEADER, c.PHN_2 AS HEADER, c.PHN_EXT_2 AS HEADER , c.PHN_AREA_3 AS HEADER, c.PHN_3 AS HEADER, c.PHN_EXT_3 AS HEADER, c.FAX_AREA AS HEADER, c.FAX AS HEADER, m.REF_MASTER_NAME AS HEADER, l.PREV_AGRMNT_NO AS HEADER, n.REF_MASTER_NAME AS HEADER, l.MR_SLIK_SEC_ECO_CODE AS HEADER, h.ATTR_VALUE AS HEADER, i.ATTR_VALUE AS HEADER, j.ATTR_VALUE AS HEADER, k.ATTR_VALUE AS HEADER, jk.ATTR_VALUE AS HEADER FROM APP a JOIN APP_CUST b ON a.APP_ID = b.APP_ID JOIN APP_CUST_ADDR c ON b.APP_CUST_ID = c.APP_CUST_ID JOIN REF_APP_SRC d ON d.APP_SRC_CODE = a.MR_APP_SOURCE_CODE JOIN REF_MASTER_LOS e ON e.REF_MASTER_CODE = a.MR_FIRST_INST_TYPE_CODE JOIN REF_MASTER_LOS f ON f.REF_MASTER_CODE = a.MR_WOP_CODE JOIN REF_MASTER_LOS g ON g.REF_MASTER_CODE = a.MR_CUST_NOTIFY_OPT_CODE JOIN APP_ATTR_CONTENT h ON h.APP_ID = a.APP_ID JOIN APP_ATTR_CONTENT i ON i.APP_ID = a.APP_ID JOIN APP_ATTR_CONTENT j ON j.APP_ID = a.APP_ID JOIN APP_ATTR_CONTENT k ON k.APP_ID = a.APP_ID JOIN APP_ATTR_CONTENT jk ON jk.APP_ID = a.APP_ID JOIN APP_GOVERMENT_REGULATION l ON l.APP_ID = a.APP_ID JOIN REF_MASTER_LOS m ON m.REF_MASTER_CODE = l.MR_CHARACTERISTIC_OF_CREDIT_CODE JOIN REF_MASTER_LOS n ON n.REF_MASTER_CODE = l.MR_WAY_OF_RESTRUCTURE_CODE JOIN REF_MASTER_LOS o ON o.REF_MASTER_CODE = l.MR_SLIK_SEC_ECO_CODE JOIN REF_MASTER_LOS p ON p.REF_MASTER_CODE = l.MR_DP_SRC_PAYMENT_CODE JOIN APP_FIN_DATA q ON q.APP_ID = b.APP_ID JOIN REF_MASTER_LOS r ON r.REF_MASTER_CODE = q.MR_INST_SCHEME_CODE JOIN REF_MASTER_LOS s ON s.REF_MASTER_CODE = l.MR_INST_SRC_PAYMENT_CODE JOIN REF_MASTER_LOS t ON t.REF_MASTER_CODE = q.INTEREST_TYPE WHERE APP_NO = '"+ appno +"' AND c.MR_CUST_ADDR_TYPE_CODE = 'MAILING' AND e.REF_MASTER_TYPE_CODE = 'FIRST_INST_TYPE' AND f.REF_MASTER_TYPE_CODE = 'WOP' AND h.REF_ATTR_CODE = 'BLCKLST_APPI' AND i.REF_ATTR_CODE = 'APPI_SCR' AND j.REF_ATTR_CODE = 'DATE_APP_DATA' AND k.REF_ATTR_CODE = 'REF_MASTER_APP_DATA' AND jk.REF_ATTR_CODE = 'ATTR_NAP_TEST' AND n.REF_MASTER_TYPE_CODE = 'WAY_OF_RESTRUCTURE'"), {  row ->

			applicationdata = (row)
		})
		return applicationdata
	}

	@Keyword
	public BankAccountTabApplicationDataStoreDB (Sql instance, String appno){
		String applicationdata
		instance.eachRow(("SELECT c.BANK_NAME AS HEADER, b.BANK_ACC_NO AS HEADER, b.BANK_ACC_NAME AS HEADER FROM app a JOiN APP_OTHER_INFO b ON a.APP_ID = b.APP_ID JOIN REF_BANK_LOS c ON b.BANK_CODE = c.BANK_CODE WHERE APP_NO = '"+ appno +"'"), {  row ->

			applicationdata = (row)
		})
		return applicationdata
	}


	@Keyword
	public NAP2TabAssetStoreDB (Sql instance, String appno){
		String applicationdata
		instance.eachRow(("SELECT SUPPL_CODE AS HEADER, SUPPL_NAME AS HEADER, d.SUPPL_EMP_NAME AS HEADER, e.SUPPL_EMP_NAME AS HEADER, a.FULL_ASSET_CODE AS HEADER, MR_ASSET_CONDITION_CODE AS HEADER, ASSET_PRICE_AMT AS HEADER, h.REF_MASTER_NAME AS HEADER, a.COLOR AS HEADER, a.TAX_CITY_ISSUER AS HEADER, FORMAT(a.ASSET_TAX_DT, 'MM/dd/yyy') AS HEADER, a.ASSET_NOTES AS HEADER, a.MANUFACTURING_YEAR AS HEADER, CONVERT(INT, DOWN_PAYMENT_PRCNT) AS HEADER, CONVERT(INT, DOWN_PAYMENT_AMT) AS HEADER, a.SERIAL_NO_1 AS HEADER, a.SERIAL_NO_2 AS HEADER, a.SERIAL_NO_3 AS HEADER, a.SERIAL_NO_4 AS HEADER, a.SERIAL_NO_5 AS HEADER, c.ATTR_VALUE AS HEADER, g.USER_NAME AS HEADER, i.REF_MASTER_NAME AS HEADER, g.MR_OWNER_TYPE_CODE AS HEADER, g.OWNER_NAME AS HEADER, i.REF_MASTER_NAME AS HEADER, g.OWNER_PROFESSION_CODE AS HEADER, j.REF_MASTER_NAME AS HEADER, g.OWNER_ID_NO AS HEADER, g.OWNER_MOBILE_PHN_NO AS HEADER, g.OWNER_ADDR AS HEADER, g.LOCATION_AREA_CODE_3 AS HEADER, g.LOCATION_AREA_CODE_4 AS HEADER, g.OWNER_ZIPCODE AS HEADER, g.OWNER_AREA_CODE_1 AS HEADER, g.OWNER_AREA_CODE_2 AS HEADER, g.OWNER_CITY AS HEADER, g.LOCATION_ADDR AS HEADER, g.LOCATION_AREA_CODE_3 AS HEADER, g.LOCATION_AREA_CODE_4 AS HEADER, g.LOCATION_ZIPCODE AS HEADER, g.LOCATION_AREA_CODE_1 AS HEADER, g.LOCATION_AREA_CODE_2 AS HEADER, g.LOCATION_CITY AS HEADER FROM APP_ASSET a JOIN APP b ON a.APP_ID = b.APP_ID JOIN APP_ASSET_ATTR c ON a.APP_ASSET_ID = c.APP_ASSET_ID JOIN APP_ASSET_SUPPL_EMP d ON a.APP_ASSET_ID = d.APP_ASSET_ID JOIN APP_ASSET_SUPPL_EMP e ON a.APP_ASSET_ID = e.APP_ASSET_ID JOIN APP_COLLATERAL f ON a.APP_ID = f.APP_ID JOIN APP_COLLATERAL_REGISTRATION g ON f.APP_COLLATERAL_ID = g.APP_COLLATERAL_ID JOIN REF_MASTER_LOS h ON h.REF_MASTER_CODE = a.MR_ASSET_USAGE_CODE JOIN REF_MASTER_LOS i ON i.REF_MASTER_CODE = g.MR_OWNER_RELATIONSHIP_CODE JOIN REF_MASTER_LOS j ON j.REF_MASTER_CODE = g.MR_ID_TYPE_CODE WHERE APP_NO = '"+ appno +"' AND c.ASSET_ATTR_NAME = 'ASSET REGION' AND d.MR_SUPPL_EMP_POSITION_CODE = 'SALES_PERSON' AND e.MR_SUPPL_EMP_POSITION_CODE = 'ADMIN_HEAD' AND h.REF_MASTER_TYPE_CODE = 'ASSET_USAGE' AND i.REF_MASTER_TYPE_CODE = 'CUST_PERSONAL_RELATIONSHIP'AND j.REF_MASTER_TYPE_CODE = 'ID_TYPE'"), {  row ->

			applicationdata = (row)
		})
		return applicationdata
	}

	@Keyword
	public NAP2AccessoriesStoreDB (Sql instance, String appno){
		String accessoriesdata
		ArrayList <String> listaccessories = new ArrayList<String>()
		instance.eachRow(("SELECT b.SUPPL_CODE AS HEADER, b.SUPPL_NAME AS HEADER, a.ASSET_ACCESSORY_CODE AS HEADER, a.ASSET_ACCESSORY_NAME AS HEADER, CONVERT(INT, a.ACCESSORY_PRICE_AMT) AS HEADER, CONVERT(INT, a.DOWN_PAYMENT_PRCNT) AS HEADER, CONVERT(INT, a.DOWN_PAYMENT_AMT) AS HEADER, a.ACCESSORY_NOTES AS HEADER FROM APP_ASSET_ACCESSORY a JOIN APP_ASSET b ON a.APP_ASSET_ID = b.APP_ASSET_ID JOIN APP c ON b.APP_ID = c.APP_ID WHERE APP_NO = '"+ appno +"'"), {  row ->

			accessoriesdata = (row[0])
			listaccessories.add(accessoriesdata)
			accessoriesdata = (row[1])
			listaccessories.add(accessoriesdata)
			accessoriesdata = (row[2])
			listaccessories.add(accessoriesdata)
			accessoriesdata = (row[3])
			listaccessories.add(accessoriesdata)
			accessoriesdata = (row[4])
			listaccessories.add(accessoriesdata)
			accessoriesdata = (row[5])
			listaccessories.add(accessoriesdata)
			accessoriesdata = (row[6])
			listaccessories.add(accessoriesdata)
			accessoriesdata = (row[7])
			listaccessories.add(accessoriesdata)
		})
		return listaccessories
	}

	@Keyword
	public NAP2InsuranceCMStoreDB (Sql instance, String appno){
		String insurancedata
		instance.eachRow(("SELECT c.CUST_INSCO_BRANCH_NAME AS HEADER, CONVERT(INT , c.CUST_CVG_AMT) AS HEADER, c.INS_POLICY_NO AS HEADER, c.INS_POLICY_NAME AS HEADER,FORMAT(c.CUST_COVER_START_DT, 'MM/dd/yyyy') AS HEADER, FORMAT(c.END_DT, 'MM/dd/yyyy') AS HEADER, c.CUST_NOTES AS HEADER, c.INS_ASSET_REGION AS HEADER, CONVERT(INT , c.CVG_AMT) AS HEADER, f.REF_MASTER_NAME AS HEADER, c.MR_INS_PAY_METHOD_CODE AS HEADER, c.INSCO_BRANCH_NAME AS HEADER, c.NOTES AS HEADER, c.INS_LENGTH AS HEADER, CONVERT(INT, c.CUST_ADMIN_FEE_AMT) AS HEADER, CONVERT(INT, c.CUST_STAMP_DUTY_FEE) AS HEADER, d.MR_MAIN_CVG_TYPE_CODE AS HEADER, IS_CAPITALIZED AS HEADER, CONVERT(INT, TOTAL_INS_CPTLZ_AMT) AS HEADER, CONVERT(INT, e.SUM_INSURED_AMT) AS HEADER, CONVERT(INT, e.CUST_ADD_PREMI_AMT) AS HEADER, CONVERT(INT, TOTAL_CUST_FEE_AMT) AS HEADER, CONVERT(INT , a.TOTAL_CUST_DISC_AMT) AS HEADER, CONVERT(INT, TOTAL_PREMI_PAID_BY_CUST_AMT) AS HEADER FROM APP_INS a JOIN APP b ON a.APP_ID = b.APP_ID JOIN APP_INS_OBJ c ON b.APP_ID = c.APP_ID JOIN APP_INS_MAIN_CVG d ON d.APP_INS_OBJ_ID = c.APP_INS_OBJ_ID JOIN APP_INS_ADD_CVG e ON e.APP_INS_MAIN_CVG_ID = d.APP_INS_MAIN_CVG_ID JOIN REF_MASTER_LOS f ON f.REF_MASTER_CODE = c.INS_ASSET_COVER_PERIOD WHERE APP_NO = '"+ appno +"'"), {  row ->

			insurancedata = (row)
		})
		return insurancedata
	}


	@Keyword
	public NAP2InsuranceCStoreDB (Sql instance, String appno){
		String insurancedata
		instance.eachRow(("SELECT c.CUST_INSCO_BRANCH_NAME AS HEADER, CONVERT(INT , c.CUST_CVG_AMT) AS HEADER, c.INS_POLICY_NO AS HEADER, c.INS_POLICY_NAME AS HEADER, FORMAT(c.CUST_COVER_START_DT, 'MM/dd/yyyy') AS HEADER, FORMAT(c.END_DT, 'MM/dd/yyyy') AS HEADER, c.CUST_NOTES AS HEADER  FROM APP_INS a JOIN APP b ON a.APP_ID = b.APP_ID JOIN APP_INS_OBJ c ON b.APP_ID = c.APP_ID WHERE APP_NO = '"+ appno +"'"), {  row ->

			insurancedata = (row)
		})
		return insurancedata
	}

	@Keyword
	public NAP2InsuranceMStoreDB (Sql instance, String appno){
		String insurancedata
		instance.eachRow(("SELECT DISTINCT c.INS_ASSET_REGION AS HEADER, CONVERT(INT , c.CVG_AMT) AS HEADER, f.REF_MASTER_NAME AS HEADER, g.REF_MASTER_NAME AS HEADER, c.INSCO_BRANCH_NAME AS HEADER, c.NOTES AS HEADER, c.INS_LENGTH AS HEADER, CONVERT(INT, c.CUST_ADMIN_FEE_AMT) AS HEADER, CONVERT(INT, c.CUST_STAMP_DUTY_FEE) AS HEADER, h.REF_MASTER_NAME AS HEADER, IS_CAPITALIZED AS HEADER, CONVERT(INT, TOTAL_INS_CPTLZ_AMT) AS HEADER FROM APP_INS a JOIN APP b ON a.APP_ID = b.APP_ID JOIN APP_INS_OBJ c ON b.APP_ID = c.APP_ID JOIN APP_INS_MAIN_CVG d ON d.APP_INS_OBJ_ID = c.APP_INS_OBJ_ID JOIN APP_INS_ADD_CVG e ON e.APP_INS_MAIN_CVG_ID = d.APP_INS_MAIN_CVG_ID JOIN REF_MASTER_LOS f ON f.REF_MASTER_CODE = c.INS_ASSET_COVER_PERIOD JOIN REF_MASTER_LOS g ON g.REF_MASTER_CODE = c.PAY_PERIOD_TO_INSCO JOIN REF_MASTER_LOS h ON h.REF_MASTER_CODE = d.MR_MAIN_CVG_TYPE_CODE WHERE APP_NO = '"+ appno +"' AND g.REF_MASTER_TYPE_CODE = 'PAY_PERIOD_TO_INSCO'"), {  row ->

			insurancedata = (row)
		})
		return insurancedata
	}


	@Keyword
	public NAP2InsuranceCVGStoreDB (Sql instance, String appno){
		String insurancedata
		ArrayList <String> listCVG = new ArrayList<String>()
		instance.eachRow(("SELECT CONVERT(INT, e.SUM_INSURED_AMT) AS HEADER, CONVERT(INT, e.CUST_ADD_PREMI_AMT) AS HEADER, CONVERT(INT, TOTAL_CUST_FEE_AMT) AS HEADER, CONVERT(INT , a.TOTAL_CUST_DISC_AMT) AS HEADER, CONVERT(INT, TOTAL_PREMI_PAID_BY_CUST_AMT) AS HEADER FROM APP_INS a JOIN APP b ON a.APP_ID = b.APP_ID JOIN APP_INS_OBJ c ON b.APP_ID = c.APP_ID JOIN APP_INS_MAIN_CVG d ON d.APP_INS_OBJ_ID = c.APP_INS_OBJ_ID JOIN APP_INS_ADD_CVG e ON e.APP_INS_MAIN_CVG_ID = d.APP_INS_MAIN_CVG_ID JOIN REF_MASTER_LOS f ON f.REF_MASTER_CODE = c.INS_ASSET_COVER_PERIOD JOIN REF_MASTER_LOS g ON g.REF_MASTER_CODE = c.PAY_PERIOD_TO_INSCO JOIN REF_MASTER_LOS h ON h.REF_MASTER_CODE = d.MR_MAIN_CVG_TYPE_CODE WHERE APP_NO = '"+ appno +"'"), {  row ->

			insurancedata = (row[0])
			listCVG.add(insurancedata)
			insurancedata = (row[1])
			listCVG.add(insurancedata)
			insurancedata = (row[2])
			listCVG.add(insurancedata)
			insurancedata = (row[3])
			listCVG.add(insurancedata)
			insurancedata = (row[4])
			listCVG.add(insurancedata)
		})
		return insurancedata
	}


	@Keyword
	public NAP2LifeInsuranceStoreDB (Sql instance, String appno){
		String lifeinsurancedata
		instance.eachRow(("SELECT b.LIFE_INSCO_BRANCH_NAME AS HEADER, d.REF_MASTER_NAME AS HEADER, 100 - CONVERT(int, b.PAID_IN_ADV_PRCNT) AS HEADER, NEW_COVER_NOTES AS HEADER, IS_CUST_COVER AS HEADER, IS_GUARANTOR_COVER AS HEADER, IS_SPOUSE_COVER AS HEADER, CONVERT(INT, CUST_ADMIN_FEE_AMT) AS HEADER FROM APP_LIFE_INS_D a JOIN APP_LIFE_INS_H b ON a.APP_LIFE_INS_H_ID = b.APP_LIFE_INS_H_ID JOIN APP c ON b.APP_ID = c.APP_ID JOIN REF_MASTER_LOS d ON d.REF_MASTER_CODE = b.MR_LIFE_INS_PAID_METHOD_CODE WHERE APP_NO = '"+ appno +"'"), {  row ->

			lifeinsurancedata = (row)
		})
		return lifeinsurancedata
	}

	@Keyword
	public NAP2FinancialStoreDB (Sql instance, String appno){
		String financialdata
		instance.eachRow(("SELECT CONVERT(INT, TOTAL_FEE_AMT) AS HEADER, CONVERT(INT, TOTAL_FEE_CPTLZ_AMT) AS HEADER, c.REF_MASTER_NAME AS HEADER, EFFECTIVE_RATE_PRCNT AS HEADER, FLAT_RATE_PRCNT AS HEADER, GRACE_PERIOD AS HEADER, a.MR_GRACE_PERIOD_TYPE_CODE AS HEADER, CONVERT(INT,TDP_PAID_COY_AMT) AS HEADER FROM APP_FIN_DATA a JOIN APP b ON a.APP_ID = b.APP_ID JOIN REF_MASTER_LOS c ON c.REF_MASTER_CODE = a.MR_PROVISION_FEE_CALC_METHOD_CODE WHERE APP_NO = '"+ appno +"'"), {  row ->

			financialdata = (row)
		})
		return financialdata
	}


	@Keyword
	public NAP2SubsidyStoreDB (Sql instance, String appno){
		String subsidy
		ArrayList <String> listsubsidy = new ArrayList<String>()
		instance.eachRow(("select MR_SUBSIDY_FROM_TYPE_NAME, SUBSIDY_FROM_VALUE_NAME, MR_SUBSIDY_ALLOC_NAME, MR_SUBSIDY_SOURCE_NAME, CONVERT(INT, SUBSIDY_AMT), CONVERT(INT, SUBSIDY_PRCNT) from APP_SUBSIDY a JOIN APP b ON a.APP_ID = b.APP_ID WHERE APP_NO = '"+ appno +"'"), {  row ->

			subsidy = (row[0])
			listsubsidy.add(subsidy)
			subsidy = (row[1])
			listsubsidy.add(subsidy)
			subsidy = (row[2])
			listsubsidy.add(subsidy)
			subsidy = (row[3])
			listsubsidy.add(subsidy)
			subsidy = (row[4])
			listsubsidy.add(subsidy)
			subsidy = (row[5])
			listsubsidy.add(subsidy)
		})
		return listsubsidy
	}

	@Keyword
	public NAP2TermConditionStoreDB (Sql instance, String appno){
		String termandcondition
		ArrayList <String> listTC = new ArrayList<String>()
		instance.eachRow(("SELECT IS_CHECKED, FORMAT(PROMISED_DT, 'MM/dd/yyy'), FORMAT(EXPIRED_DT, 'MM/dd/yyyy'), IS_WAIVED FROM APP_TC a JOIN APP b ON a.APP_ID = b.APP_ID WHERE APP_NO = '"+ appno +"'"), {  row ->

			termandcondition = (row[0])
			listTC.add(termandcondition)
			termandcondition = (row[1])
			listTC.add(termandcondition)
			termandcondition = (row[2])
			listTC.add(termandcondition)
			termandcondition = (row[3])
			listTC.add(termandcondition)
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
			instance.eachRow(("SELECT COMMISSION_RECIPIENT_REF_NO, MR_COMMISSION_SOURCE_CODE, COMMISSION_AMT, REFUND_AMT FROM APP_COMMISSION_H commHead WITH(NOLOCK) JOIN APP_COMMISSION_D commDetail ON commDetail.APP_COMMISSION_H_ID = commHead.APP_COMMISSION_H_ID WHERE APP_id = (Select app_id from app where app_no = '"+appno+"') and MR_COMMISSION_RECIPIENT_TYPE_CODE = 'SUPPLIER'"), {  row ->
				if(row[3]==0.00){
					resultSupp.add("-1")
				}
				else{
					resultSupp.add(row[2])
				}
			})
			instance.eachRow(("SELECT COMMISSION_RECIPIENT_REF_NO, MR_COMMISSION_SOURCE_CODE, COMMISSION_AMT, REFUND_AMT FROM APP_COMMISSION_H commHead WITH(NOLOCK) JOIN APP_COMMISSION_D commDetail ON commDetail.APP_COMMISSION_H_ID = commHead.APP_COMMISSION_H_ID WHERE APP_id = (Select app_id from app where app_no = '"+appno+"') and MR_COMMISSION_RECIPIENT_TYPE_CODE = 'SUPPLIER_EMP'"), {  row ->
				if(row[3]==0.00){
					resultSuppEmp.add("-1")
				}
				else{
					resultSuppEmp.add(row[2])
				}
			})
			instance.eachRow(("SELECT COMMISSION_RECIPIENT_REF_NO, MR_COMMISSION_SOURCE_CODE, COMMISSION_AMT, REFUND_AMT FROM APP_COMMISSION_H commHead WITH (NOLOCK) JOIN APP_COMMISSION_D commDetail ON commHead.APP_COMMISSION_H_ID = commDetail.APP_COMMISSION_H_ID WHERE APP_id = (Select app_id from app where app_no = '"+appno+"') and MR_COMMISSION_RECIPIENT_TYPE_CODE = 'REFERANTOR'"), {  row ->
				if(row[3]==0.00){
					resultRef.add("-1")
				}
				else{
					resultRef.add(row[2])
				}
			})
		}
		else if(allocationType.equalsIgnoreCase("Percentage")){
			instance.eachRow(("SELECT COMMISSION_RECIPIENT_REF_NO, MR_COMMISSION_SOURCE_CODE, CASE WHEN (COMMISSION_AMT/NULLIF(REFUND_AMT, 0))*100 IS NULL THEN 0 ELSE (COMMISSION_AMT/NULLIF(REFUND_AMT, 0))*100 END, REFUND_AMT FROM APP_COMMISSION_H commHead WITH(NOLOCK) JOIN APP_COMMISSION_D commDetail ON commDetail.APP_COMMISSION_H_ID = commHead.APP_COMMISSION_H_ID WHERE APP_id = (Select app_id from app where app_no = '"+appno+"') and MR_COMMISSION_RECIPIENT_TYPE_CODE = 'SUPPLIER'"), {  row ->

				if(row[3]==0.00){
					resultSupp.add("-1")
				}
				else{
					resultSupp.add(row[2])
				}
			})
			instance.eachRow(("SELECT COMMISSION_RECIPIENT_REF_NO, MR_COMMISSION_SOURCE_CODE, CASE WHEN (COMMISSION_AMT/NULLIF(REFUND_AMT, 0))*100 IS NULL THEN 0 ELSE (COMMISSION_AMT/NULLIF(REFUND_AMT, 0))*100 END, REFUND_AMT FROM APP_COMMISSION_H commHead WITH(NOLOCK) JOIN APP_COMMISSION_D commDetail ON commDetail.APP_COMMISSION_H_ID = commHead.APP_COMMISSION_H_ID WHERE APP_id = (Select app_id from app where app_no = '"+appno+"') and MR_COMMISSION_RECIPIENT_TYPE_CODE = 'SUPPLIER_EMP'"), {  row ->

				if(row[3]==0.00){
					resultSuppEmp.add("-1")
				}
				else{
					resultSuppEmp.add(row[2])
				}
			})
			instance.eachRow(("SELECT COMMISSION_RECIPIENT_REF_NO, MR_COMMISSION_SOURCE_CODE, CASE WHEN (COMMISSION_AMT/NULLIF(REFUND_AMT, 0))*100 IS NULL THEN 0 ELSE (COMMISSION_AMT/NULLIF(REFUND_AMT, 0))*100 END, REFUND_AMT FROM APP_COMMISSION_H commHead WITH(NOLOCK) JOIN APP_COMMISSION_D commDetail ON commDetail.APP_COMMISSION_H_ID = commHead.APP_COMMISSION_H_ID WHERE APP_id = (Select app_id from app where app_no = '"+appno+"') and MR_COMMISSION_RECIPIENT_TYPE_CODE = 'REFERANTOR'"), {  row ->

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
		instance.eachRow(("SELECT 'SUPPLIER' AS HEADER, COUNT(*) AS HEADER FROM APP_COMMISSION_H WITH(NOLOCK) WHERE APP_id = (Select app_id from app where app_no = '"+appno+"') AND MR_COMMISSION_RECIPIENT_TYPE_CODE = 'SUPPLIER' UNION SELECT 'SUPPLIER_EMP' AS HEADER, COUNT(*) AS HEADER FROM APP_COMMISSION_H WHERE APP_id = (Select app_id from app where app_no = '"+appno+"') AND MR_COMMISSION_RECIPIENT_TYPE_CODE = 'SUPPLIER_EMP' UNION SELECT 'REFERANTOR' AS HEADER, COUNT(*) AS HEADER FROM APP_COMMISSION_H WHERE APP_id = (Select app_id from app where app_no = '"+appno+"') AND MR_COMMISSION_RECIPIENT_TYPE_CODE = 'REFERANTOR'"), {  row ->
			listcount.add(row[1])
		})
		return listcount
	}

	@Keyword
	public countCommissionSourceSupplierDB(Sql instance, String appno){
		Integer countCS
		instance.eachRow(("SELECT commHead.APP_COMMISSION_H_ID, COMMISSION_RECIPIENT_REF_NO, COUNT(*) AS HEADER FROM APP_COMMISSION_H commHead WITH (NOLOCK) JOIN APP_COMMISSION_D commDetail ON commDetail.APP_COMMISSION_H_ID = commHead.APP_COMMISSION_H_ID WHERE APP_id = (Select app_id from app where app_no = '"+appno+"') and MR_COMMISSION_RECIPIENT_TYPE_CODE = 'SUPPLIER' GROUP BY COMMISSION_RECIPIENT_REF_NO, commHead.APP_COMMISSION_H_ID ORDER BY commHead.APP_COMMISSION_H_ID"), {  row ->
			countCS = row[2]
		})
		return countCS
	}

	@Keyword
	public countCommissionSourceSupplierEmpDB(Sql instance, String appno){
		ArrayList<Integer> countCS = new ArrayList<>()
		instance.eachRow(("SELECT commHead.APP_COMMISSION_H_ID, COMMISSION_RECIPIENT_REF_NO, COUNT(MR_COMMISSION_SOURCE_CODE) AS HEADER FROM APP_COMMISSION_H commHead WITH(NOLOCK) JOIN APP_COMMISSION_D commDetail ON commDetail.APP_COMMISSION_H_ID = commHead.APP_COMMISSION_H_ID WHERE APP_id = (Select app_id from app where app_no = '"+appno+"') and MR_COMMISSION_RECIPIENT_TYPE_CODE = 'SUPPLIER_EMP' GROUP BY COMMISSION_RECIPIENT_REF_NO, commHead.APP_COMMISSION_H_ID ORDER BY commHead.APP_COMMISSION_H_ID"), {  row ->
			println(row[2])
			countCS.add(row[2])
		})
		return countCS
	}

	@Keyword
	public countCommissionSourceReferantorDB(Sql instance, String appno){
		ArrayList<Integer> countCS = new ArrayList<>()
		instance.eachRow(("SELECT commHead.APP_COMMISSION_H_ID, COMMISSION_RECIPIENT_REF_NO, COUNT(MR_COMMISSION_SOURCE_CODE) AS HEADER FROM APP_COMMISSION_H commHead WITH(NOLOCK) JOIN APP_COMMISSION_D commDetail ON commDetail.APP_COMMISSION_H_ID = commHead.APP_COMMISSION_H_ID WHERE APP_id = (Select app_id from app where app_no = '"+appno+"') and MR_COMMISSION_RECIPIENT_TYPE_CODE = 'REFERANTOR' GROUP BY COMMISSION_RECIPIENT_REF_NO, commHead.APP_COMMISSION_H_ID ORDER BY commHead.APP_COMMISSION_H_ID"), {  row ->
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
		instance.eachRow(("SELECT [Custprefix] as HEADER, [no_of_dependent] as HEADER, [no_of_residence] as HEADER, [family_card_no] as HEADER, [IS_RIP] as HEADER, [IS_VIP] as HEADER, [IS_AFF_MF] as HEADER, [SALUTATION] AS HEADER, [NICKNAME] AS HEADER, [Custsuffix] AS HEADER, [NATIONALITY] AS HEADER, [Countryname] AS HEADER, [EDUCATION] AS HEADER, [RELIGION] AS HEADER, [VIPNOTES] AS HEADER FROM (SELECT [Custprefix],  [no_of_dependent], [no_of_residence], [family_card_no], [IS_RIP], [IS_VIP], [IS_AFF_MF], [NICKNAME], [Custsuffix], [Countryname], [VIPNOTES], mastername.Code, REF_MASTER_NAME FROM (select [Custprefix], [no_of_dependent], [no_of_residence], [family_card_no], [IS_RIP], [IS_VIP], [IS_AFF_MF], [NICKNAME], [Custsuffix], [Countryname], [VIPNOTES], [Code], value FROM (SELECT MR_EDUCATION_CODE AS 'EDUCATION', MR_NATIONALITY_CODE as 'NATIONALITY', MR_RELIGION_CODE as 'RELIGION', MR_SALUTATION_CODE as 'SALUTATION', acp.CUST_PREFIX_NAME as [Custprefix], acp.NO_OF_DEPENDENTS as [no_of_dependent] , acp.NO_OF_RESIDENCE as [no_of_residence], acp.FAMILY_CARD_NO as [family_card_no], acp.IS_REST_IN_PEACE as [IS_RIP], ac.IS_VIP as [IS_VIP], ac.IS_AFFILIATE_WITH_MF as [IS_AFF_MF], acp.NICK_NAME as [NICKNAME], acp.CUST_SUFFIX_NAME as [Custsuffix], rc.COUNTRY_NAME as [Countryname], ac.VIP_NOTES as [VIPNOTES] FROM APP_CUST_PERSONAL acp join app_cust ac on ac.app_cust_id = acp.APP_CUST_ID JOIN APP app ON ac.APP_ID = app.APP_ID JOIN APP_CUST_GRP acg ON acg.APP_CUST_ID = ac.APP_CUST_ID JOIN V_REF_COUNTRY rc ON rc.COUNTRY_CODE = acp.NATIONALITY_COUNTRY_CODE WHERE ac.cust_name='"+ name +"' and app_no ='"+ appno +"') as Orig unpivot (value for [Code] in ([EDUCATION],[NATIONALITY],[RELIGION],[SALUTATION]) )as unpiv) as mastername JOIN REF_MASTER_LOS rf on rf.REF_MASTER_Code = mastername.value) AS ref PIVOT (MAX(ref.REF_MASTER_NAME) for [Code] in ([EDUCATION],[NATIONALITY],[RELIGION],[SALUTATION])) as piv"), {  row ->

			customerdetail = (row)
		})
		return customerdetail
	}


	@Keyword
	public NAP4AddressStoreData (Sql instance, String appno, String name, String addresstype){
		String address
		instance.eachRow(("SELECT [CUST_ADDR_TYPE] AS HEADER, [ADDR] AS HEADER, [RT] AS HEADER, [RW] AS HEADER, [ZIPCODE] AS HEADER, [KECAMATAN] AS HEADER, [KELURAHAN] AS HEADER, [KOTA] AS HEADER, [PHN1AREA] AS HEADER, [PHN1] AS HEADER, [PHN1EXT] AS HEADER, [PHN2AREA] AS HEADER, [PHN2] AS HEADER, [PHN2EXT] AS HEADER, [PHN3AREA] AS HEADER, [PHN3] AS HEADER, [PHN3EXT] AS HEADER, [FAXAREA] AS HEADER, [FAX] AS HEADER, [BUILDING_OWNERSHIP] AS HEADER FROM (SELECT [ADDR], [RT], [RW], [ZIPCODE], [KECAMATAN], [KELURAHAN], [KOTA], [PHN1AREA], [PHN1], [PHN1EXT], [PHN2AREA], [PHN2], [PHN2EXT], [PHN3AREA], [PHN3], [PHN3EXT], [FAXAREA], [FAX], mastername.Code, REF_MASTER_NAME FROM (select [ADDR], [RT], [RW], [ZIPCODE], [KECAMATAN], [KELURAHAN], [KOTA], [PHN1AREA], [PHN1], [PHN1EXT], [PHN2AREA], [PHN2], [PHN2EXT], [PHN3AREA], [PHN3], [PHN3EXT], [FAXAREA], [FAX], [Code], value FROM (SELECT MR_CUST_ADDR_TYPE_CODE AS 'CUST_ADDR_TYPE', aca.ADDR AS [ADDR], aca.AREA_CODE_4 AS [RT], aca.AREA_CODE_3 AS [RW], aca.ZIPCODE AS [ZIPCODE], aca.AREA_CODE_1 AS [KECAMATAN], aca.AREA_CODE_2 AS [KELURAHAN], aca.CITY AS [KOTA], aca.PHN_AREA_1 AS [PHN1AREA], aca.PHN_1 AS [PHN1], aca.PHN_EXT_1 AS [PHN1EXT], aca.PHN_AREA_2 AS [PHN2AREA], aca.PHN_2 AS [PHN2], aca.PHN_EXT_2 AS [PHN2EXT], aca.PHN_AREA_3 AS [PHN3AREA], aca.PHN_3 AS [PHN3], aca.PHN_EXT_3 AS [PHN3EXT], aca.FAX_AREA AS [FAXAREA], aca.FAX AS [FAX], MR_HOUSE_OWNERSHIP_CODE AS 'BUILDING_OWNERSHIP' FROM APP_CUST_ADDR aca WITH(NOLOCK) JOIN APP_CUST ac ON aca.APP_CUST_ID = ac.APP_CUST_ID JOIN APP app ON app.APP_ID = ac.APP_ID JOIN REF_MASTER_LOS rml ON rml.REF_MASTER_CODE = aca.MR_CUST_ADDR_TYPE_CODE WHERE app.APP_NO = '"+ appno +"'  AND ac.CUST_NAME = '"+ name +"' AND rml.REF_MASTER_NAME = '"+ addresstype +"') as orig unpivot (value for [Code] in ([CUST_ADDR_TYPE],[BUILDING_OWNERSHIP]) )as unpiv) as mastername JOIN REF_MASTER_LOS rf on rf.REF_MASTER_Code = mastername.value) AS ref PIVOT (MAX(ref.REF_MASTER_NAME) for [Code] in ([CUST_ADDR_TYPE],[BUILDING_OWNERSHIP])) as piv"), {  row ->

			address = (row)
		})
		return address
	}

	@Keyword
	public NAP4JobdataEmployeePersonalStoreData (Sql instance, String appno, String name){
		String Jobdata
		instance.eachRow(("SELECT [PROFCODE] AS HEADER, [JOB_POSITION] AS HEADER, [JOB_STAT] AS HEADER, [ISMFEMP] AS HEADER, [ISWELLKNOWNCOY] AS HEADER, [COYNAME] AS HEADER, [INDUSTRYTYPE] AS HEADER, [COY_SCALE] AS HEADER, [ESTABLISHMENTDATE] AS HEADER, [JOBTITLE] AS HEADER, [NUMOFEMP] AS HEADER FROM (SELECT [PROFCODE], [ISMFEMP], [ISWELLKNOWNCOY], [COYNAME], [INDUSTRYTYPE], [ESTABLISHMENTDATE], [JOBTITLE], [NUMOFEMP], mastername.Code, REF_MASTER_NAME FROM (select [PROFCODE], [ISMFEMP], [ISWELLKNOWNCOY], [COYNAME], [INDUSTRYTYPE], [ESTABLISHMENTDATE], [JOBTITLE], [NUMOFEMP], [Code], value FROM (SELECT MR_PROFESSION_CODE AS [PROFCODE], MR_JOB_POSITION_CODE AS 'JOB_POSITION', MR_JOB_STAT_CODE AS  'JOB_STAT', custjob.IS_MF_EMP [ISMFEMP], custjob.IS_WELLKNOWN_COY AS [ISWELLKNOWNCOY], custjob.COY_NAME AS [COYNAME], INDUSTRY_TYPE_CODE AS [INDUSTRYTYPE], MR_COY_SCALE_CODE AS 'COY_SCALE', FORMAT(custjob.EMPLOYMENT_ESTABLISHMENT_DT, 'MM/dd/yyyy') AS [ESTABLISHMENTDATE], JOB_TITLE_NAME AS [JOBTITLE], NUM_OF_EMPLOYEE AS [NUMOFEMP] FROM APP_CUST_PERSONAL_JOB_DATA custjob  JOIN APP_CUST_PERSONAL acp ON acp.APP_CUST_PERSONAL_ID = custjob.APP_CUST_PERSONAL_ID JOIN APP_CUST ac ON ac.APP_CUST_ID = acp.APP_CUST_ID JOIN APP app ON app.APP_ID = ac.APP_ID WHERE app.APP_NO = '"+ appno +"' AND ac.CUST_NAME = '"+ name +"' ) as orig unpivot(value for [Code] in ([JOB_STAT],[COY_SCALE],[JOB_POSITION]))as unpiv) as mastername JOIN REF_MASTER_LOS rf on rf.REF_MASTER_Code = mastername.value) AS ref PIVOT (MAX(ref.REF_MASTER_NAME) for [Code] in ([JOB_STAT],[COY_SCALE], [JOB_POSITION])) as piv"), {  row ->

			Jobdata = (row)
		})
		return Jobdata
	}

	@Keyword
	public NAP4JobdataProfPersonalStoreData (Sql instance, String appno, String name){
		String Jobdata
		instance.eachRow(("SELECT MR_PROFESSION_CODE AS HEADER, MR_JOB_POSITION_CODE AS HEADER, custjob.IS_WELLKNOWN_COY AS HEADER, custjob.COY_NAME AS HEADER, INDUSTRY_TYPE_CODE AS HEADER, MR_COY_SCALE_CODE, FORMAT(custjob.EMPLOYMENT_ESTABLISHMENT_DT, 'MM/dd/yyyy') AS HEADER, JOB_TITLE_NAME AS HEADER, NUM_OF_EMPLOYEE AS HEADER, MR_INVESTMENT_TYPE_CODE FROM APP_CUST_PERSONAL_JOB_DATA custjob JOIN APP_CUST_PERSONAL acp ON acp.APP_CUST_PERSONAL_ID = custjob.APP_CUST_PERSONAL_ID JOIN APP_CUST ac ON ac.APP_CUST_ID = acp.APP_CUST_ID JOIN APP app ON app.APP_ID = ac.APP_ID WHERE app.APP_NO = '"+ appno +"' AND ac.CUST_NAME = '"+ name +"'"), {  row ->

			Jobdata = (row)
		})
		return Jobdata
	}

	@Keyword
	public NAP4JobdataNonProfPersonalStoreData (Sql instance, String appno, String name){
		String Jobdata
		instance.eachRow(("SELECT custjob.MR_PROFESSION_CODE AS HEADER, custjob.JOB_TITLE_NAME AS HEADER FROM APP_CUST_PERSONAL_JOB_DATA custjob JOIN APP_CUST_PERSONAL acp ON acp.APP_CUST_PERSONAL_ID = custjob.APP_CUST_PERSONAL_ID JOIN APP_CUST ac ON ac.APP_CUST_ID = acp.APP_CUST_ID JOIN APP app ON app.APP_ID = ac.APP_ID WHERE app.APP_NO = '"+ appno +"' AND ac.CUST_NAME = '"+ name +"'"), {  row ->

			Jobdata = (row)
		})
		return Jobdata
	}

	@Keyword
	public NAP4JobdataSMEPersonalStoreData (Sql instance, String appno, String name){
		String Jobdata
		instance.eachRow(("SELECT [PROFCODE] AS HEADER, [JOB_POSITION] AS HEADER, [ISWELLKNOWNCOY] AS HEADER, [COYNAME] AS HEADER, [INDUSTRYTYPE] AS HEADER, [COY_SCALE] AS HEADER, [ESTABLISHMENTDATE] AS HEADER, [JOBTITLE] AS HEADER, [NUMOFEMP] AS HEADER, [INVESTMENT_TYPE] AS HEADER FROM (SELECT [PROFCODE], [ISWELLKNOWNCOY], [COYNAME], [INDUSTRYTYPE], [ESTABLISHMENTDATE], [JOBTITLE], [NUMOFEMP], mastername.Code, REF_MASTER_NAME FROM (select [PROFCODE], [ISWELLKNOWNCOY], [COYNAME], [INDUSTRYTYPE], [ESTABLISHMENTDATE], [JOBTITLE], [NUMOFEMP], [Code], value FROM (SELECT MR_PROFESSION_CODE AS [PROFCODE], MR_JOB_POSITION_CODE AS 'JOB_POSITION', custjob.IS_WELLKNOWN_COY AS [ISWELLKNOWNCOY], custjob.COY_NAME AS [COYNAME], INDUSTRY_TYPE_CODE AS [INDUSTRYTYPE], MR_COY_SCALE_CODE AS 'COY_SCALE', FORMAT(custjob.EMPLOYMENT_ESTABLISHMENT_DT, 'MM/dd/yyyy') AS [ESTABLISHMENTDATE], JOB_TITLE_NAME AS [JOBTITLE], NUM_OF_EMPLOYEE AS [NUMOFEMP], MR_INVESTMENT_TYPE_CODE AS 'INVESTMENT_TYPE' FROM APP_CUST_PERSONAL_JOB_DATA custjob JOIN APP_CUST_PERSONAL acp ON acp.APP_CUST_PERSONAL_ID = custjob.APP_CUST_PERSONAL_ID JOIN APP_CUST ac ON ac.APP_CUST_ID = acp.APP_CUST_ID JOIN APP app ON app.APP_ID = ac.APP_ID WHERE app.APP_NO = '"+ appno +"' AND ac.CUST_NAME = '"+ name +"' ) as orig unpivot (value for [Code] in ([COY_SCALE],[JOB_POSITION],[INVESTMENT_TYPE]))as unpiv) as mastername JOIN REF_MASTER_LOS rf on rf.REF_MASTER_Code = mastername.value) AS ref PIVOT (MAX(ref.REF_MASTER_NAME) for [Code] in ([COY_SCALE], [JOB_POSITION], [INVESTMENT_TYPE])) as piv"), {  row ->

			Jobdata = (row)
		})
		return Jobdata
	}

	@Keyword
	public NAP4JobdataAddressStoreData (Sql instance, String appno, String name){
		String Jobdata
		instance.eachRow(("SELECT ADDR AS HEADER, AREA_CODE_4 AS HEADER, AREA_CODE_3 AS HEADER, ZIPCODE AS HEADER, AREA_CODE_2 AS HEADER, AREA_CODE_1 AS HEADER, CITY AS HEADER, NOTES AS HEADER, PHN_AREA_1 AS HEADER, PHN_1 AS HEADER, PHN_EXT_1 AS HEADER, PHN_AREA_2 AS HEADER, PHN_2 AS HEADER, PHN_EXT_2 AS HEADER, PHN_AREA_3 AS HEADER, PHN_3 AS HEADER, PHN_EXT_3 AS HEADER, FAX_AREA AS HEADER, FAX AS HEADER FROM APP_CUST_PERSONAL_JOB_DATA custjob JOIN APP_CUST_PERSONAL acp ON acp.APP_CUST_PERSONAL_ID = custjob.APP_CUST_PERSONAL_ID JOIN APP_CUST ac ON ac.APP_CUST_ID = acp.APP_CUST_ID JOIN APP app ON app.APP_ID = ac.APP_ID JOIN APP_CUST_ADDR addr ON addr.APP_CUST_ID = acp.APP_CUST_ID WHERE app.APP_NO = '"+ appno +"' AND ac.CUST_NAME = '"+ name +"' AND addr.MR_CUST_ADDR_TYPE_CODE = 'JOB'"), {  row ->

			Jobdata = (row)
		})
		return Jobdata
	}

	@Keyword
	public NAP4PrevJobAddressStoreData (Sql instance, String appno, String name){
		String Jobdata
		instance.eachRow(("SELECT PREV_COY_NAME AS HEADER, FORMAT(PREV_EMPLOYMENT_DT, 'MM/dd/yyy') AS HEADER, ADDR AS HEADER, AREA_CODE_4 AS HEADER, AREA_CODE_3 AS HEADER, ZIPCODE AS HEADER, AREA_CODE_2 AS HEADER, AREA_CODE_1 AS HEADER, CITY AS HEADER, NOTES AS HEADER, PHN_AREA_1 AS HEADER, PHN_1 AS HEADER, PHN_EXT_1 AS HEADER, PHN_AREA_2 AS HEADER, PHN_2 AS HEADER, PHN_EXT_2 AS HEADER, PHN_AREA_3 AS HEADER, PHN_3 AS HEADER, PHN_EXT_3 AS HEADER, FAX_AREA AS HEADER, FAX AS HEADER FROM APP_CUST_PERSONAL_JOB_DATA custjob JOIN APP_CUST_PERSONAL acp ON acp.APP_CUST_PERSONAL_ID = custjob.APP_CUST_PERSONAL_ID JOIN APP_CUST ac ON ac.APP_CUST_ID = acp.APP_CUST_ID JOIN APP app ON app.APP_ID = ac.APP_ID JOIN APP_CUST_ADDR addr ON addr.APP_CUST_ID = acp.APP_CUST_ID WHERE app.APP_NO = '"+ appno +"' AND ac.CUST_NAME = '"+ name +"' AND addr.MR_CUST_ADDR_TYPE_CODE = 'PREV_JOB'"), {  row ->

			Jobdata = (row)
		})
		return Jobdata
	}

	@Keyword
	public NAP4OtherBizAddressStoredata (Sql instance, String appno, String name){
		String Jobdata
		instance.eachRow(("SELECT OTH_BIZ_NAME AS HEADER, OTH_BIZ_TYPE AS HEADER, OTH_BIZ_INDUSTRY_TYPE_CODE AS HEADER, OTH_BIZ_JOB_POSITION AS HEADER, FORMAT(OTH_BIZ_ESTABLISHMENT_DT, 'MM/dd/yyy') AS HEADER, ADDR AS HEADER, AREA_CODE_4 AS HEADER, AREA_CODE_3 AS HEADER, ZIPCODE AS HEADER, AREA_CODE_2 AS HEADER, AREA_CODE_1 AS HEADER, CITY AS HEADER, NOTES AS HEADER, PHN_AREA_1 AS HEADER, PHN_1 AS HEADER, PHN_EXT_1 AS HEADER, PHN_AREA_2 AS HEADER, PHN_2 AS HEADER, PHN_EXT_2 AS HEADER, PHN_AREA_3 AS HEADER, PHN_3 AS HEADER, PHN_EXT_3 AS HEADER, FAX_AREA AS HEADER, FAX AS HEADER FROM APP_CUST_PERSONAL_JOB_DATA custjob JOIN APP_CUST_PERSONAL acp ON acp.APP_CUST_PERSONAL_ID = custjob.APP_CUST_PERSONAL_ID JOIN APP_CUST ac ON ac.APP_CUST_ID = acp.APP_CUST_ID JOIN APP app ON app.APP_ID = ac.APP_ID JOIN APP_CUST_ADDR addr ON addr.APP_CUST_ID = acp.APP_CUST_ID WHERE app.APP_NO = '"+ appno +"' AND ac.CUST_NAME = '"+ name +"' AND addr.MR_CUST_ADDR_TYPE_CODE = 'OTH_BIZ'"), {  row ->

			Jobdata = (row)
		})
		return Jobdata
	}

	@Keyword
	public NAP4EmergencyContactStoreData (Sql instance, String appno, String name){
		String emergencycontact
		instance.eachRow(("SELECT CONTACT_PERSON_NAME AS HEADER, rml.REF_MASTER_NAME AS HEADER,  emrgnc.ID_NO AS HEADER, FORMAT(emrgnc.ID_EXPIRED_DT, 'MM/dd/yyy') AS HEADER, emrgnc.MR_CUST_RELATIONSHIP_CODE AS HEADER,  emrgnc.EMAIL AS HEADER, emrgnc.MR_GENDER_CODE AS HEADER, emrgnc.BIRTH_PLACE AS HEADER, FORMAT(emrgnc.BIRTH_DT, 'MM/dd/yyyy') AS HEADER, emrgnc.MOBILE_PHN_NO_1 AS HEADER, emrgnc.MOBILE_PHN_NO_2 AS HEADER, emrgnc.ADDR AS HEADER, emrgnc.AREA_CODE_4 AS HEADER, emrgnc.AREA_CODE_3 AS HEADER, emrgnc.ZIPCODE AS HEADER, emrgnc.AREA_CODE_1 AS HEADER, emrgnc.AREA_CODE_2 AS HEADER, emrgnc.CITY AS HEADER, emrgnc.PHN_AREA_1 AS HEADER, emrgnc.PHN_1 AS HEADER, emrgnc.PHN_EXT_1 AS HEADER, emrgnc.PHN_AREA_2 AS HEADER, emrgnc.PHN_2 AS HEADER, emrgnc.PHN_EXT_2 AS HEADER, emrgnc.PHN_AREA_3 AS HEADER, emrgnc.PHN_3 AS HEADER, emrgnc.PHN_EXT_3 AS HEADER FROM APP_CUST_EMRGNC_CNTCT emrgnc JOIN APP_CUST ac ON ac.APP_CUST_ID = emrgnc.APP_CUST_ID JOIN APP app ON app.APP_ID = ac.APP_ID JOIN REF_MASTER_LOS rml ON rml.REF_MASTER_CODE = emrgnc.MR_ID_TYPE_CODE WHERE app.APP_NO = '"+ appno +"' AND ac.CUST_NAME = '"+ name +"' AND rml.REF_MASTER_TYPE_CODE = 'ID_TYPE'"), {  row ->

			emergencycontact = (row)
		})
		return emergencycontact
	}

	@Keyword
	public NAP4FinancialDataPersonalStoreData (Sql instance, String appno, String name){
		String financialdata
		ArrayList <String> listfinancialdata = new ArrayList<String>()
		instance.eachRow(("SELECT FORMAT(MONTHLY_INCOME_AMT, 'N0') AS HEADER, FORMAT(OTHER_INCOME_AMT, 'N0') AS HEADER, rml.REF_MASTER_NAME AS HEADER, FORMAT(MONTHLY_INSTALLMENT_AMT, 'N0') AS HEADER, FORMAT(MONTHLY_EXPENSE_AMT, 'N0') AS HEADER, FORMAT(fin.DATE_AS_OF , 'MM/dd/yyy') AS HEADER FROM APP_CUST_PERSONAL_FIN_DATA fin WITH(NOLOCK) JOIN APP_CUST_PERSONAL acp ON acp.APP_CUST_PERSONAL_ID = fin.APP_CUST_PERSONAL_ID JOIN APP_CUST ac ON ac.APP_CUST_ID = acp.APP_CUST_ID JOIN APP app ON app.APP_ID = ac.APP_ID JOIN REF_MASTER_LOS rml ON rml.REF_MASTER_CODE = fin.MR_SOURCE_OF_INCOME_TYPE_CODE WHERE app.APP_NO = '"+ appno +"' AND ac.CUST_NAME = '"+ name +"'"), {  row ->

			financialdata = (row[0])
			listfinancialdata.add(financialdata)
			financialdata = (row[1])
			listfinancialdata.add(financialdata)
			financialdata = (row[2])
			listfinancialdata.add(financialdata)
			financialdata = (row[3])
			listfinancialdata.add(financialdata)
			financialdata = (row[4])
			listfinancialdata.add(financialdata)
			financialdata = (row[5])
			listfinancialdata.add(financialdata)
		})
		return listfinancialdata
	}

	@Keyword
	public NAP4FinancialAttrStoreData (Sql instance, String appno, String name){
		String financialattr
		ArrayList <String> listfinancialattr = new ArrayList<String>()
		instance.eachRow(("SELECT ATTR_VALUE AS HEADER FROM APP_CUST_FIN_DATA_ATTR_CONTENT fin WITH(NOLOCK) JOIN APP_CUST ac ON ac.APP_CUST_ID = fin.APP_CUST_ID JOIN APP app ON app.APP_ID = ac.APP_ID WHERE app.APP_NO = '"+ appno +"' AND ac.CUST_NAME = '"+ name +"'"), {  row ->

			financialattr = (row[0])
			listfinancialattr.add(financialattr)
		})
		return listfinancialattr
	}

	@Keyword
	public NAP4FinDataBankAccStoreData (Sql instance, String appno, String name){
		String bankacc
		ArrayList <String> listbankacc = new ArrayList<String>()
		instance.eachRow(("SELECT bank.BANK_CODE AS HEADER, bank.BANK_BRANCH AS HEADER, bank.BANK_ACC_NAME AS HEADER, bank.BANK_ACC_NO AS HEADER, bank.IS_DEFAULT AS HEADER, bank.IS_ACTIVE AS HEADER, FORMAT(bank.BEG_BALANCE_AMT, 'N0') AS HEADER FROM APP_CUST_BANK_ACC bank WITH(NOLOCK) JOIN APP_CUST_PERSONAL acp ON bank.APP_CUST_ID = acp.APP_CUST_ID JOIN APP_CUST ac ON ac.APP_CUST_ID = bank.APP_CUST_ID JOIN APP app ON app.APP_ID = ac.APP_ID WHERE app.APP_NO = '"+ appno +"' AND ac.CUST_NAME = '"+ name +"' GROUP BY bank.BANK_CODE, bank.BANK_BRANCH, bank.BANK_ACC_NAME, bank.BANK_ACC_NO, bank.IS_DEFAULT, bank.IS_ACTIVE, bank.BEG_BALANCE_AMT"), {  row ->

			bankacc = (row[0])
			listbankacc.add(bankacc)
			bankacc = (row[1])
			listbankacc.add(bankacc)
			bankacc = (row[2])
			listbankacc.add(bankacc)
			bankacc = (row[3])
			listbankacc.add(bankacc)
			bankacc = (row[4])
			listbankacc.add(bankacc)
			bankacc = (row[5])
			listbankacc.add(bankacc)
			bankacc = (row[6])
			listbankacc.add(bankacc)


		})
		return listbankacc
	}

	@Keyword
	public NAP4FinancialCheckBankStatStoreData (Sql instance, String appno, String name, String accno){
		String bankstat
		instance.eachRow(("SELECT bank.IS_BANK_STMNT AS HEADER FROM APP_CUST_BANK_ACC bank WITH(NOLOCK) JOIN APP_CUST_PERSONAL acp ON bank.APP_CUST_ID = acp.APP_CUST_ID JOIN APP_CUST ac ON ac.APP_CUST_ID = bank.APP_CUST_ID JOIN APP app ON app.APP_ID = ac.APP_ID JOIN APP_CUST_BANK_STMNT bs ON bank.APP_CUST_BANK_ACC_ID = bs.APP_CUST_BANK_ACC_ID WHERE app.APP_NO = '"+ appno +"' AND ac.CUST_NAME = '"+ name +"' AND bank.BANK_ACC_NO = '"+ accno +"' GROUP BY bank.IS_BANK_STMNT"), {  row ->

			bankstat = (row[0])

		})
		return bankstat
	}

	@Keyword
	public NAP4BankStatDataStoreData (Sql instance, String appno, String name, String accno){
		String bankstatdata
		ArrayList <String> listbankstatdata = new ArrayList<String>()
		instance.eachRow(("SELECT bs.MONTH AS HEADER ,bs.YEAR AS HEADER, bs.DEBIT_TRX_COUNT AS HEADER, FORMAT(bs.DEBIT_AMT, 'N0') AS HEADER, bs.CREDIT_TRX_COUNT AS HEADER, FORMAT(bs.CREDIT_AMT, 'N0') AS HEADER FROM APP_CUST_BANK_ACC bank WITH(NOLOCK) JOIN APP_CUST_PERSONAL acp ON bank.APP_CUST_ID = acp.APP_CUST_ID JOIN APP_CUST ac ON ac.APP_CUST_ID = bank.APP_CUST_ID JOIN APP app ON app.APP_ID = ac.APP_ID JOIN APP_CUST_BANK_STMNT bs ON bank.APP_CUST_BANK_ACC_ID = bs.APP_CUST_BANK_ACC_ID WHERE app.APP_NO = '"+ appno +"' AND ac.CUST_NAME = '"+ name +"' AND bank.BANK_ACC_NO = '"+ accno +"'"), {  row ->

			bankstatdata = (row[0])
			listbankstatdata.add(bankstatdata)
			bankstatdata = (row[1])
			listbankstatdata.add(bankstatdata)
			bankstatdata = (row[2])
			listbankstatdata.add(bankstatdata)
			bankstatdata = (row[3])
			listbankstatdata.add(bankstatdata)
			bankstatdata = (row[4])
			listbankstatdata.add(bankstatdata)
			bankstatdata = (row[5])
			listbankstatdata.add(bankstatdata)


		})
		return listbankstatdata
	}

	@Keyword
	public NAP4CustomerAssetDataStoreData (Sql instance, String appno, String name){
		String customerasset
		ArrayList <String> listcustomerasset = new ArrayList<String>()
		instance.eachRow(("SELECT rml.REF_MASTER_NAME AS HEADER, asset.ASSET_DESCR AS HEADER, FORMAT(asset.ASSET_VALUE, 'N0') AS HEADER, asset.ASSET_QTY AS HEADER FROM APP_CUST_ASSET asset WITH(NOLOCK) JOIN APP_CUST ac ON ac.APP_CUST_ID = asset.APP_CUST_ID JOIN  APP app ON app.APP_ID = ac.APP_ID JOIN REF_MASTER_LOS rml ON rml.REF_MASTER_CODE = asset.MR_CUST_ASSET_TYPE_CODE WHERE app.APP_NO = '"+ appno +"' AND ac.CUST_NAME = '"+ name +"'"), {  row ->

			customerasset = (row[0])
			listcustomerasset.add(customerasset)
			customerasset = (row[1])
			listcustomerasset.add(customerasset)
			customerasset = (row[2])
			listcustomerasset.add(customerasset)
			customerasset = (row[3])
			listcustomerasset.add(customerasset)
		})
		return listcustomerasset
	}

	@Keyword
	public NAP4OtherAttrInfoStoreData (Sql instance, String appno, String name){
		String Otherattrinfo
		instance.eachRow(("SELECT oth.LBPPMS_DEBT_GRP_LBPP_DESCR AS HEADER, oth.LBPPMS_BIZ_SCL_LBPP_DESCR AS HEADER, oth.LBPPMS_CNTRPRT_LBPP_DESCR AS HEADER, oth.LBPPMS_BIZ_SUSTAIN_LBPP_DESCR AS HEADER FROM APP_CUST_OTHER_INFO oth WITH(NOLOCK) JOIN APP_CUST ac ON ac.APP_CUST_ID = oth.APP_CUST_ID JOIN APP app ON app.APP_ID = ac.APP_ID WHERE app.APP_NO = '"+ appno +"' AND ac.CUST_NAME = '"+ name +"'"), {  row ->

			Otherattrinfo = (row)
		})
		return Otherattrinfo
	}

	@Keyword
	public NAP4OtherAttrStoreData (Sql instance, String appno, String name){
		String OtherAttr
		ArrayList <String> listOtherAttr = new ArrayList<String>()
		instance.eachRow(("SELECT ATTR_VALUE FROM APP_CUST_ATTR_CONTENT attr WITH (NOLOCK) JOIN APP_CUST ac ON ac.APP_CUST_ID = attr.APP_CUST_ID JOIN APP app ON app.APP_ID = ac.APP_ID WHERE app.APP_NO = '"+ appno +"' AND ac.CUST_NAME = '"+ name +"'"), {  row ->

			OtherAttr = (row[0])
			listOtherAttr.add(OtherAttr)

		})
		return listOtherAttr
	}

	@Keyword
	public NAP4CustomerDetailCompanyStoreData (Sql instance, String appno, String name){
		String customerdetail
		instance.eachRow(("SELECT FORMAT(acc.ESTABLISHMENT_DT, 'MM/dd/yyyy') AS HEADER, acc.NUM_OF_EMP AS HEADER, 'custgroup' AS HEADER, ac.IS_VIP AS HEADER, ac.IS_AFFILIATE_WITH_MF AS HEADER, acc.INDUSTRY_TYPE_CODE AS HEADER, acc.MR_COMPANY_TYPE_CODE AS HEADER, ac.VIP_NOTES AS HEADER FROM APP_CUST ac WITH(NOLOCK) JOIN APP app ON app.APP_ID = ac.APP_ID JOIN APP_CUST_COMPANY acc ON acc.APP_CUST_ID = ac.APP_CUST_ID WHERE app.APP_NO = '"+ appno +"' AND ac.CUST_NAME = '"+ name +"'"), {  row ->

			customerdetail = (row)
		})
		return customerdetail
	}

	@Keyword
	public NAP4ContactInformationStoreData (Sql instance, String appno, String name){
		String contactinfo
		instance.eachRow(("SELECT [ContactPersonName] AS HEADER, [JobTitle] AS HEADER, [ID_TYPE] AS HEADER, [ContactIDNo] AS HEADER, [ExpiredDate] AS HEADER, [MobilePhn1] AS HEADER, [Email1] AS HEADER, [GENDER] AS HEADER, [JOB_POSITION] AS HEADER, [BirthPlace] AS HEADER, [BirthDate] AS HEADER, [CUST_COMPANY_RELATIONSHIP] AS HEADER, [Mobilephn2] AS HEADER, [Email2] AS HEADER, [Addr] AS HEADER, [RT] AS HEADER, [RW] AS HEADER, [Zipcode] AS HEADER, [Kecamatan] AS HEADER, [Kelurahan] AS HEADER, [Kota] AS HEADER, [Phone1Area] AS HEADER, [Phone1] AS HEADER, [Phone1Ext] AS HEADER, [Phone2Area] AS HEADER, [Phone2] AS HEADER, [Phone2Ext] AS HEADER, [FaxArea] AS HEADER, [Fax] AS HEADER FROM (SELECT [ContactPersonName], [JobTitle], [ContactIDNo], [ExpiredDate], [MobilePhn1], [Email1], [BirthPlace], [BirthDate], [Mobilephn2], [Email2], [Addr], [RT], [RW], [Zipcode], [Kecamatan], [Kelurahan], [Kota], [Phone1Area], [Phone1], [Phone1Ext], [Phone2Area], [Phone2], [Phone2Ext], [FaxArea], [Fax], mastername.Code, REF_MASTER_NAME FROM (select [ContactPersonName], [JobTitle], [ContactIDNo], [ExpiredDate], [MobilePhn1], [Email1], [BirthPlace], [BirthDate], [Mobilephn2], [Email2], [Addr], [RT], [RW], [Zipcode], [Kecamatan], [Kelurahan], [Kota], [Phone1Area], [Phone1], [Phone1Ext], [Phone2Area], [Phone2], [Phone2Ext], [FaxArea], [Fax], [Code], value FROM (SELECT contact.CONTACT_PERSON_NAME as [ContactPersonName], contact.JOB_TITLE_NAME as [JobTitle], contact.MR_ID_TYPE_CODE as 'ID_TYPE', contact.ID_NO as [ContactIDNo], FORMAT(contact.ID_EXPIRED_DT, 'MM/dd/yyyy') as [ExpiredDate], contact.MOBILE_PHN_NO_1 as [MobilePhn1], contact.EMAIL_1 as [Email1], contact.MR_JOB_POSITION_CODE as 'JOB_POSITION', contact.BIRTH_PLACE as [BirthPlace], FORMAT(contact.BIRTH_DT, 'MM/dd/yyyy') as [BirthDate], contact.MR_CUST_RELATIONSHIP_CODE as 'CUST_COMPANY_RELATIONSHIP', contact.MOBILE_PHN_NO_2 as [Mobilephn2], contact.EMAIL_2 as [Email2], addr.ADDR as [Addr], addr.AREA_CODE_4 as [RT], addr.AREA_CODE_3 as [RW], addr.ZIPCODE as [Zipcode], addr.AREA_CODE_1 as [Kecamatan], addr.AREA_CODE_2 as [Kelurahan], addr.CITY as [Kota], addr.PHN_AREA_1 as [Phone1Area], addr.PHN_1 as [Phone1], addr.PHN_EXT_1 as [Phone1Ext], addr.PHN_AREA_2 as [Phone2Area], addr.PHN_2 as [Phone2], addr.PHN_EXT_2 as [Phone2Ext], addr.FAX_AREA as [FaxArea], addr.FAX as [Fax], MR_GENDER_CODE as 'GENDER' FROM APP_CUST_COMPANY_CONTACT_PERSON contact WITH(NOLOCK) JOIN APP_CUST_COMPANY acc ON acc.APP_CUST_COMPANY_ID = contact.APP_CUST_COMPANY_ID JOIN APP_CUST ac ON ac.APP_CUST_ID = acc.APP_CUST_ID JOIN APP app ON app.APP_ID = ac.APP_ID JOIN APP_CUST_ADDR addr ON addr.APP_CUST_ID = ac.APP_CUST_ID WHERE app.APP_NO = '"+ appno +"' AND ac.CUST_NAME = '"+ name +"' AND addr.MR_CUST_ADDR_TYPE_CODE = 'COMPANY') as orig unpivot (value for [Code] in ([ID_TYPE],[CUST_COMPANY_RELATIONSHIP],[JOB_POSITION],[GENDER]) )as unpiv) as mastername JOIN REF_MASTER_LOS rf on rf.REF_MASTER_Code = mastername.value) AS ref PIVOT (MAX(ref.REF_MASTER_NAME) for [Code] in ([ID_TYPE],[CUST_COMPANY_RELATIONSHIP],[JOB_POSITION],[GENDER])) as piv"), {  row ->

			contactinfo = (row)
		})
		return contactinfo
	}

	@Keyword
	public NAP4FinancialDataCompanyStoreData (Sql instance, String appno, String name){
		String financialdata
		instance.eachRow(("SELECT FORMAT(GROSS_MONTHLY_INCOME_AMT, 'N0') AS HEADER, FORMAT(GROSS_MONTHLY_EXPENSE_AMT, 'N0'), FORMAT(RETURN_OF_INVESTMENT_PRCNT, 'N2') AS HEADER, FORMAT(RETURN_OF_EQUITY_PRCNT, 'N2') AS HEADER, FORMAT(RETURN_OF_ASSET_PRCNT, 'N2') AS HEADER, FORMAT(PROFIT_MARGIN_PRCNT, 'N2') AS HEADER, FORMAT(CURRENT_RATIO_PRCNT, 'N2') AS HEADER, FORMAT(DEBT_EQUITY_RATIO_PRCNT, 'N2') AS HEADER, FORMAT(INV_TURN_OVER_PRCNT, 'N2') AS HEADER, FORMAT(AR_TURN_OVER_PRCNT, 'N2') AS HEADER, FORMAT(GROWTH_PRCNT, 'N2') AS HEADER, FORMAT(WORKING_CAPITAL_AMT, 'N0') AS HEADER, FORMAT(OTH_MONTHLY_INST_AMT, 'N0') AS HEADER, FORMAT(REVENUE, 'N0') AS HEADER, FORMAT(OPR_COST, 'N0') AS HEADER, FORMAT(PROFIT_BEFORE_TAX, 'N0') AS HEADER, FORMAT(CURR_ASSET, 'N0') AS HEADER, FORMAT(NET_FIXED_ASSET, 'N0') AS HEADER, FORMAT(TOTAL_ASSET, 'N0') AS HEADER, FORMAT(CURR_LIABLTS, 'N0') AS HEADER, FORMAT(LONG_TERM_LIABLTS, 'N0') AS HEADER, FORMAT(SHAREHOLDER_EQUITY, 'N0') AS HEADER, FORMAT(CURR_RATIO, 'N0') AS HEADER, FORMAT(DATE_AS_OF, 'MM/dd/yyyy') FROM APP_CUST_COMPANY_FIN_DATA fin WITH(NOLOCK) JOIN APP_CUST ac ON ac.APP_CUST_ID = fin.APP_CUST_ID JOIN APP app ON app.APP_ID = ac.APP_ID WHERE app.APP_NO = '"+ appno +"' AND ac.CUST_NAME = '"+ name +"'"), {  row ->

			financialdata = (row)
		})
		return financialdata
	}

	@Keyword
	public NAP4FinancialAttrCompanyStoreData (Sql instance, String appno, String name){
		String financialattr
		ArrayList <String> listfinancialattr = new ArrayList<String>()
		instance.eachRow(("SELECT ATTR_VALUE AS HEADER FROM APP_CUST_FIN_DATA_ATTR_CONTENT fin WITH(NOLOCK) JOIN APP_CUST ac ON ac.APP_CUST_ID = fin.APP_CUST_ID JOIN APP app ON app.APP_ID = ac.APP_ID WHERE app.APP_NO = '"+ appno +"' AND ac.CUST_NAME = '"+ name +"'"), {  row ->

			financialattr = (row[0])
			listfinancialattr.add(financialattr)
		})
		return listfinancialattr
	}

	@Keyword
	public NAP4FinDataBankAccCompanyStoreData (Sql instance, String appno, String name){
		String bankacc
		ArrayList <String> listbankacc = new ArrayList<String>()
		instance.eachRow(("SELECT bank.BANK_CODE AS HEADER, bank.BANK_BRANCH AS HEADER, bank.BANK_ACC_NAME AS HEADER, bank.BANK_ACC_NO AS HEADER, bank.IS_DEFAULT AS HEADER, bank.IS_ACTIVE AS HEADER, FORMAT(bank.BEG_BALANCE_AMT, 'N0') AS HEADER FROM APP_CUST_BANK_ACC bank WITH(NOLOCK) JOIN APP_CUST_COMPANY acp ON bank.APP_CUST_ID = acp.APP_CUST_ID JOIN APP_CUST ac ON ac.APP_CUST_ID = bank.APP_CUST_ID JOIN APP app ON app.APP_ID = ac.APP_ID WHERE app.APP_NO = '"+ appno +"' AND ac.CUST_NAME = '"+ name +"' GROUP BY bank.BANK_CODE, bank.BANK_BRANCH, bank.BANK_ACC_NAME, bank.BANK_ACC_NO, bank.IS_DEFAULT, bank.IS_ACTIVE, bank.BEG_BALANCE_AMT"), {  row ->

			bankacc = (row[0])
			listbankacc.add(bankacc)
			bankacc = (row[1])
			listbankacc.add(bankacc)
			bankacc = (row[2])
			listbankacc.add(bankacc)
			bankacc = (row[3])
			listbankacc.add(bankacc)
			bankacc = (row[4])
			listbankacc.add(bankacc)
			bankacc = (row[5])
			listbankacc.add(bankacc)
			bankacc = (row[6])
			listbankacc.add(bankacc)


		})
		return listbankacc
	}

	@Keyword
	public NAP4FinancialCheckBankStatCompanyStoreData (Sql instance, String appno, String name, String accno){
		String bankstat
		instance.eachRow(("SELECT bank.IS_BANK_STMNT AS HEADER FROM APP_CUST_BANK_ACC bank WITH(NOLOCK) JOIN APP_CUST_COMPANY acp ON bank.APP_CUST_ID = acp.APP_CUST_ID JOIN APP_CUST ac ON ac.APP_CUST_ID = bank.APP_CUST_ID JOIN APP app ON app.APP_ID = ac.APP_ID JOIN APP_CUST_BANK_STMNT bs ON bank.APP_CUST_BANK_ACC_ID = bs.APP_CUST_BANK_ACC_ID WHERE app.APP_NO = '"+ appno +"' AND ac.CUST_NAME = '"+ name +"' AND bank.BANK_ACC_NO = '"+ accno +"' GROUP BY bank.IS_BANK_STMNT"), {  row ->

			bankstat = (row[0])

		})
		return bankstat
	}

	@Keyword
	public NAP4BankStatDataCompanyStoreData (Sql instance, String appno, String name, String accno){
		String bankstatdata
		ArrayList <String> listbankstatdata = new ArrayList<String>()
		instance.eachRow(("SELECT bs.MONTH AS HEADER ,bs.YEAR AS HEADER, bs.DEBIT_TRX_COUNT AS HEADER, FORMAT(bs.DEBIT_AMT, 'N0') AS HEADER, bs.CREDIT_TRX_COUNT AS HEADER, FORMAT(bs.CREDIT_AMT, 'N0') AS HEADER FROM APP_CUST_BANK_ACC bank WITH(NOLOCK) JOIN APP_CUST_COMPANY acp ON bank.APP_CUST_ID = acp.APP_CUST_ID JOIN APP_CUST ac ON ac.APP_CUST_ID = bank.APP_CUST_ID JOIN APP app ON app.APP_ID = ac.APP_ID JOIN APP_CUST_BANK_STMNT bs ON bank.APP_CUST_BANK_ACC_ID = bs.APP_CUST_BANK_ACC_ID WHERE app.APP_NO = '"+ appno +"' AND ac.CUST_NAME = '"+ name +"' AND bank.BANK_ACC_NO = '"+ accno +"'"), {  row ->

			bankstatdata = (row[0])
			listbankstatdata.add(bankstatdata)
			bankstatdata = (row[1])
			listbankstatdata.add(bankstatdata)
			bankstatdata = (row[2])
			listbankstatdata.add(bankstatdata)
			bankstatdata = (row[3])
			listbankstatdata.add(bankstatdata)
			bankstatdata = (row[4])
			listbankstatdata.add(bankstatdata)
			bankstatdata = (row[5])
			listbankstatdata.add(bankstatdata)


		})
		return listbankstatdata
	}

	@Keyword
	public NAP4LegalDocStoreData (Sql instance, String appno, String name){
		String legaldoc
		ArrayList <String> listlegaldocdata = new ArrayList<String>()
		instance.eachRow(("SELECT MR_LEGAL_DOC_TYPE_CODE AS HEADER, DOC_NO AS HEADER, FORMAT(legal.DOC_DT, 'MM/dd/yyyy') AS HEADER, FORMAT(DOC_EXPIRED_DT, 'MM/dd/yyyy') AS HEADER, NOTARY_NAME AS HEADER, NOTARY_LOCATION AS HEADER, DOC_NOTES AS HEADER FROM APP_CUST_COMPANY_LEGAL_DOC legal WITH(NOLOCK) JOIN APP_CUST_COMPANY acc ON acc.APP_CUST_COMPANY_ID = legal.APP_CUST_COMPANY_ID JOIN APP_CUST ac ON ac.APP_CUST_ID = acc.APP_CUST_ID JOIN APP app ON app.APP_ID = ac.APP_ID WHERE app.APP_NO = '"+ appno +"' AND ac.CUST_NAME = '"+ name +"'"), {  row ->

			legaldoc = (row[0])
			listlegaldocdata.add(legaldoc)
			legaldoc = (row[1])
			listlegaldocdata.add(legaldoc)
			legaldoc = (row[2])
			listlegaldocdata.add(legaldoc)
			legaldoc = (row[3])
			listlegaldocdata.add(legaldoc)
			legaldoc = (row[4])
			listlegaldocdata.add(legaldoc)
			legaldoc = (row[5])
			listlegaldocdata.add(legaldoc)
			legaldoc = (row[6])
			listlegaldocdata.add(legaldoc)
		})
		return listlegaldocdata
	}
}
