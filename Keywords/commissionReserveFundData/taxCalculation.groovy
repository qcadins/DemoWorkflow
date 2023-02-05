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

public class taxCalculation {

	//keyword check tax rate company
	@Keyword
	public checkTaxRateCompany(Sql instance){
		ArrayList<String> taxRate = new ArrayList<Double>()
		String value
		instance.eachRow(("SELECT RTT.TAX_TYPE_DESCR,TD.TAX_RATE_PRCNT,TD.PENALTY_RATE_PRCNT,PARTIAL_TAXABLE_PRCNT,amt_from,amt_to FROM dbo.TAX_SCHM_H TH WITH (NOLOCK) JOIN dbo.TAX_SCHM_D TD WITH (NOLOCK) ON TD.TAX_SCHM_H_ID = TH.TAX_SCHM_H_ID JOIN dbo.REF_TAX_TYPE RTT WITH (NOLOCK) ON RTT.REF_TAX_TYPE_ID = TD.REF_TAX_TYPE_ID WHERE TH.MR_TRX_TAX_TYPE_CODE = 'PRGSV' AND TH.MR_TAX_KIND_CODE = 'C'"), { def row ->
			value = row[0]+";"+row[1]+";"+row[2]+";"+row[3]+";"+row[4]+";"+row[5]
			taxRate.add(value)
		})
		return taxRate
	}

	//keyword check tax payer info
	@Keyword
	public checkTaxpayerInfo(Sql instance, String name){
		ArrayList<String> taxpayerInfo = new ArrayList<String>()
		instance.eachRow(("select mr_tax_kind_code, CAST(tax_id_no_exists AS INT), CAST(is_vat AS INT) from taxpayer where name = '"+name+"' and is_active = 1"), { def row ->
			taxpayerInfo.add(row[0])
			taxpayerInfo.add(row[1])
			taxpayerInfo.add(row[2])
		})
		//		if(taxpayerInfo[0]==null){
		//			taxpayerInfo.add("P")
		//			taxpayerInfo.add("0")
		//			taxpayerInfo.add("0")
		//		}

		return taxpayerInfo
	}

	@Keyword
	public checkSupplierEmployeeTax(Sql instance, String name){
		ArrayList<String> code = new ArrayList<String>()

		instance.eachRow(("SELECT CAST(is_npwp_exist AS INT) FROM VENDOR_EMP WHERE VENDOR_EMP_NAME = '"+name+"'"), { def row ->
			code.add("P")
			code.add(row[0])
			code.add("0")
		})

		return code
	}

	@Keyword
	public checkSupplierTax(Sql instance, String name){
		ArrayList<String> code = new ArrayList<String>()
		instance.eachRow(("SELECT MR_VENDOR_TYPE_CODE, CAST(IS_NPWP_EXIST AS INT), CAST(IS_VAT AS INT) FROM VENDOR WHERE VENDOR_NAME = '"+name+"' AND MR_VENDOR_CATEGORY_CODE = 'SUPPLIER' AND IS_ACTIVE = 1"), { def row ->
			code.add(row[0])
			code.add(row[1])
			code.add(row[2])
		})

		return code
	}

	//keyword calculate nett tax company
	@Keyword
	public calculateNettTaxCompany(Double taxablepercent, BigDecimal comAmt, Double amt_to, Double npwpRate, Double vatRate, Boolean isVat){
		ArrayList<Double> taxAmount = new ArrayList<Double>()
		Double trxAmt, dppAmt, whtAmount, vatAmount
		if(taxablepercent/100*comAmt>amt_to){
			trxAmt = amt_to
		}
		else{
			trxAmt = comAmt
		}
		dppAmt = Math.ceil(trxAmt)
		whtAmount = Math.ceil(dppAmt*npwpRate/100)
		if(isVat == true){
			vatAmount = dppAmt*vatRate/100
			vatAmount = Math.ceil(vatAmount)
		}
		else{
			vatAmount = 0
			vatAmount = Math.ceil(vatAmount)
		}
		taxAmount.add(whtAmount)
		taxAmount.add(vatAmount)
		return taxAmount
	}

	//keyword calculate gross tax company
	@Keyword
	public calculateGrossTaxCompany(Double taxablepercent, BigDecimal comAmt, Double amt_to, Double npwpRate, Double vatRate, Double penaltyRate, Boolean isVat, Boolean npwpExist){
		ArrayList<Double> taxAmount = new ArrayList<Double>()
		Double trxAmt, dppAmt, whtAmount, vatAmount

		if(taxablepercent/100*comAmt>amt_to){
			dppAmt = Math.ceil(amt_to)
			if(isVat==true){
				if(npwpExist==true){
					trxAmt = dppAmt*(1+vatRate/100-npwpRate/100)
				}
				else{
					trxAmt = dppAmt*(1+vatRate/100-penaltyRate/100)
				}
			}
			else{
				if(npwpExist==true){
					trxAmt = dppAmt*(1+0-npwpRate/100)
				}
				else{
					trxAmt = dppAmt*(1+0-penaltyRate/100)
				}
			}
		}
		else{
			trxAmt=taxablepercent/100*comAmt
			if(npwpExist==true){
				dppAmt = Math.ceil(trxAmt/(1-npwpRate/100))
			}
			else{
				dppAmt = Math.ceil(trxAmt/(1-(npwpRate/100*(1+penaltyRate/100))))
			}
		}

		whtAmount = Math.ceil(dppAmt*npwpRate/100)
		if(isVat == true){
			vatAmount = dppAmt*vatRate/100
			vatAmount = Math.ceil(vatAmount)
		}
		else{
			vatAmount = 0
			vatAmount = Math.ceil(vatAmount)
		}
		taxAmount.add(whtAmount)
		taxAmount.add(vatAmount)
		return taxAmount
	}
}