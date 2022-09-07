package insuranceData

import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject

import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import internal.GlobalVariable as GlobalVariable
import java.text.NumberFormat;
import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import org.openqa.selenium.WebElement as WebElement
import org.openqa.selenium.WebDriver as WebDriver
import org.openqa.selenium.By as By
import com.kms.katalon.core.annotation.Keyword

public class verifInsuranceData {

	@Keyword
	public verifyTabInsuranceData(){
		BigDecimal totalMainPremiumResult = 0, totalAdditionalPremiumResult = 0, totalFeeResult = 0, totalPremitoCustResult = 0, totalPremitoCustAftDiscountResult=0, capitalizeAmountResult=0

		'Inisialisasi Driver'
		WebDriver driver = DriverFactory.getWebDriver()

		'Inisialisasi Variabel'
		ArrayList<WebElement> variable = driver.findElements(By.cssSelector('#insuranceCoverage > div[formarrayname=AppInsMainCvgs] > table tbody'))

		//dimana css_selector_name adalah elemen dari parent atas object yang ingin dilacak, dan div tergantung daripada bentuk element html tersebut
		'Menghitung count (size dari variabel) yang akan digunakan sebagai total banyaknya tahun pada insurance '
		int count = variable.size()

		int counterPaidByMF=0, counterCap = 0

		'Looping data insurance untuk verify'
		for (int i = 1; i <= count; i++) {
			BigDecimal  totalResultAddtPremi = 0
			counterCap=0
			'Inisialisasi Format untuk mendapatkan nilai desimal dari nilai persen'
			NumberFormat decimalFormat = NumberFormat.getPercentInstance()

			Object capitalizeObject = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabInsuranceData/input_Capitalized'),'xpath','equals',"//*[@id='insuranceCoverage']/div[5]/table/tbody["+i+"]/tr[1]/td[1]/div/input",true)

			if(WebUI.verifyElementChecked(capitalizeObject,5,FailureHandling.OPTIONAL)){
				counterCap=1
			}
			Object paidByObject = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabInsuranceData/select_PaidBy'),'xpath','equals',"//*[@id='insuranceCoverage']/div[5]/table/tbody["+i+"]/tr[1]/td[2]/div/select",true)

			Object sumInsuredPercentObject = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabInsuranceData/input_SumInsuredPercentage'), 'xpath', 'equals',
					"//*[@id='insuranceCoverage']/div[5]/table/tbody["+i+"]/tr[1]/td[5]/div/input", true)

			Object mainCoverageObject = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabInsuranceData/select_MainCoverageTP'),'xpath','equals',"//*[@id='insuranceCoverage']/div[5]/table/tbody["+i+"]/tr[1]/td[6]/span[1]/select",true)

			if(WebUI.verifyOptionSelectedByLabel(paidByObject,'(?i)MULTIFINANCE',true,20,FailureHandling.OPTIONAL)&&counterPaidByMF==0){
				counterPaidByMF = 1
			}

			Object rateObject = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabInsuranceData/input_Rate'), 'xpath', 'equals',
					"//*[@id='insuranceCoverage']/div[5]/table/tbody["+i+"]/tr[1]/td[8]/div/input", true)

			'Ambil nilai dari rate'
			String textRate = WebUI.getAttribute(rateObject,'value').replaceAll("\\s","")

			'Mengubah nilai persen menjadi desimal dengan format yang telah diinsialisasi'
			Float rate = decimalFormat.parse(textRate).floatValue()

			'Ambil nilai dari persentase sum insured'
			String textSumInsuredPercent = WebUI.getAttribute(sumInsuredPercentObject,'value').replaceAll("\\s","")

			'Mengubah nilai persen menjadi desimal'
			Float sumInsuredPercent = decimalFormat.parse(textSumInsuredPercent).floatValue();

			'Ambil nilai coverage amount'
			String textAmount = WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabInsuranceData/input_Coverage Amount MF'),'value').replace(",","")
			BigDecimal Amount = Long.parseLong(textAmount)

			Object numberOfMonthObject =  WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabInsuranceData/td_numberOfMonth'), 'xpath', 'equals',
					"//*[@id='insuranceCoverage']/div[5]/table/tbody["+i+"]/tr[1]/td[4]", true)
			'Ambil nilai number of month'
			String textNumberOfMonth = WebUI.getText(numberOfMonthObject)

			BigDecimal numberOfMonth = Integer.parseInt(textNumberOfMonth)

			Object mainPremiObject = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabInsuranceData/td_MainPremiumAmt'), 'xpath', 'equals',
					"//*[@id='insuranceCoverage']/div[5]/table/tbody["+i+"]/tr[1]/td[9]", true)

			'Ambil nilai Main Premium Amount'
			String textMainPremi = WebUI.getText(mainPremiObject).replace(",","").replace(".00","")

			'Perhitungan Main Premium Amount'
			BigDecimal Result = Math.round(Amount*sumInsuredPercent*rate*(numberOfMonth/12)/10)*10

			'Verifikasi/memastikan nilai main premium amount pada confins sama dengan perhitungan'
			WebUI.verifyMatch(textMainPremi,Result.toString(),false, FailureHandling.OPTIONAL)

			'Tambahkan main premium ke total main premium'
			totalMainPremiumResult+=Result

			ArrayList<WebElement> variableAddCovAll = driver.findElements(By.cssSelector('#insuranceCoverage > div:nth-child(2) > div > label'))

			'Mengambil dan menyimpan jumlah additional coverage'
			int countAddCov = variableAddCovAll.size()
			println(countAddCov+"countaddcov")

			int flagLoading = 0
			'Looping additional coverage'
			for (int j = 1; j <= countAddCov; j++) {
				println(j+"j")
				BigDecimal resultAddtPremi=0
				int countSumInsuredAmount = 0

				Object addCovYearCheckbox = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabInsuranceData/input_Flood_checkboxLabel TP'),
						'xpath', 'equals', ((('//*[@id=\'insuranceCoverage\']/div[5]/table/tbody[' + i) + ']/tr[') + (j + 2)) + ']/td[6]/div/div/label/input',
						true)

				Object modifySumInsuredAmount = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabInsuranceData/select_SumInsuredAmountFlood'),
						'xpath', 'equals', ((('//div[@id=\'insuranceCoverage\']/div[5]/table/tbody[' + i) + ']/tr[') + (j + 2)) + ']/td[7]/div/div/select',
						true)

				'Pengecekan untuk flagging sum insured amount dari additional coverage ada atau tidak'
				if (WebUI.verifyElementPresent(modifySumInsuredAmount, 2, FailureHandling.OPTIONAL)) {
					countSumInsuredAmount = 1
				}


				Object modifyAddtRateObject = WebUI.modifyObjectProperty(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabInsuranceData/input_AddtRate'),'xpath','equals',"//div[@id='insuranceCoverage']/div[5]/table/tbody["+i+"]/tr["+(j+2)+"]/td[8]/div/span/div/input",true)

				'Ambil nilai dari rate additional coverage '
				String textAddCovRate = WebUI.getAttribute(modifyAddtRateObject,'value').replaceAll("\\s","").replace(",","")

				Float AddtRate = 0.00
				'Jika additional coverage tercentang'
				if(WebUI.verifyElementChecked(addCovYearCheckbox,2,FailureHandling.OPTIONAL)){
					'Pengecekan jika sum insured amount ada atau tidak'
					if(countSumInsuredAmount==1){
						AddtRate = Long.parseLong(textAddCovRate)
						'Perhitungan nilai additional premi'
						resultAddtPremi = Math.round(AddtRate*(numberOfMonth/12))
					}
					else{

						'Mengubah nilai persen menjadi desimal dengan format yang telah diinsialisasi dan simpan nilainya'
						AddtRate = decimalFormat.parse(textAddCovRate).floatValue()

						'Perhitungan nilai additional premi'
						resultAddtPremi = Math.round(Result*AddtRate*(numberOfMonth/12))
					}

				}
				Object AddtAmtObject = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabInsuranceData/td_FloodAmt'),'xpath','equals',"//*[@id='insuranceCoverage']/div[5]/table/tbody["+i+"]/tr["+(j+2)+"]/td[9]",true)

				'ambil nilai additional premi dari confins'
				String textAddtPremiAmt = WebUI.getText(AddtAmtObject).replace(",","")

				println(textAddtPremiAmt+", "+resultAddtPremi)
				'Verifikasi nilai additional premi confins sama dengan hasil penghitungan pada katalon'
				WebUI.verifyMatch(textAddtPremiAmt, String.format("%.2f", resultAddtPremi),false)


				totalResultAddtPremi+=resultAddtPremi
			}
			println("totalresultaddtpremi "+totalResultAddtPremi)
			'tambahkan additional premi ke total additional premi'
			totalAdditionalPremiumResult+=totalResultAddtPremi


			BigDecimal resultTotalPremiPerYear = 0

			'tambahkan main premi dan additional premi untuk menghitung total premi per tahunnya'
			resultTotalPremiPerYear = Result+totalResultAddtPremi

			Object totalPremiPerYearObject = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabInsuranceData/td_TotalPremiPerYear'),'xpath','equals',"//*[@id='insuranceCoverage']/div[5]/table/tbody["+i+"]/tr["+(countAddCov+3)+"]/td[9]",true)

			'ambil nilai total premi per tahun dari confins'
			String textTotalPremiPerYear = WebUI.getText(totalPremiPerYearObject).replace(",","")
			println(textTotalPremiPerYear+","+resultTotalPremiPerYear)
			'verifikasi nilai total premi per tahun dari confins sesuai dengan perhitungan'
			WebUI.verifyMatch(textTotalPremiPerYear, String.format("%.2f", resultTotalPremiPerYear),false)

			if(counterCap==1){
				capitalizeAmountResult+=resultTotalPremiPerYear
			}


		}
		ArrayList<BigDecimal> totalResult = new ArrayList<BigDecimal>()
		totalResult.add(totalMainPremiumResult)
		totalResult.add(totalAdditionalPremiumResult)
		totalResult.add(counterPaidByMF)
		totalResult.add(capitalizeAmountResult)
		return totalResult
	}

	@Keyword
	public verifyTabInsuranceDataCapPartial(){
		BigDecimal totalMainPremiumResult = 0, totalAdditionalPremiumResult = 0, totalFeeResult = 0, totalPremitoCustResult = 0, totalPremitoCustAftDiscountResult=0, capitalizeAmountResult=0

		'Inisialisasi Driver'
		WebDriver driver = DriverFactory.getWebDriver()

		'Inisialisasi Variabel'
		ArrayList<WebElement> variable = driver.findElements(By.cssSelector('#insuranceCoverage > div[formarrayname=AppInsMainCvgs] > table tbody'))

		//dimana css_selector_name adalah elemen dari parent atas object yang ingin dilacak, dan div tergantung daripada bentuk element html tersebut
		'Menghitung count (size dari variabel) yang akan digunakan sebagai total banyaknya tahun pada insurance '
		int count = variable.size()

		int counterPaidByMF=0, counterCap = 0

		if(WebUI.verifyElementChecked(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabInsuranceData/input_FullCapitalizedAmount'),2,FailureHandling.OPTIONAL)){
			counterCap = 1
		}
		'Looping data insurance untuk verify'
		for (int i = 1; i <= count; i++) {
			BigDecimal  totalResultAddtPremi = 0

			'Inisialisasi Format untuk mendapatkan nilai desimal dari nilai persen'
			NumberFormat decimalFormat = NumberFormat.getPercentInstance()

			Object paidByObject = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabInsuranceData/select_PaidBy'),'xpath','equals',"//*[@id='insuranceCoverage']/div[5]/table/tbody["+i+"]/tr[1]/td[1]/div/select",true)

			Object sumInsuredPercentObject = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabInsuranceData/input_SumInsuredPercentage'), 'xpath', 'equals',
					"//*[@id='insuranceCoverage']/div[5]/table/tbody["+i+"]/tr[1]/td[4]/div/input", true)

			Object mainCoverageObject = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabInsuranceData/select_MainCoverageTP'),'xpath','equals',"//*[@id='insuranceCoverage']/div[5]/table/tbody["+i+"]/tr[1]/td[5]/span[1]/select",true)

			if(WebUI.verifyOptionSelectedByLabel(paidByObject,'(?i)MULTIFINANCE',true,20,FailureHandling.OPTIONAL)&&counterPaidByMF==0){
				counterPaidByMF = 1
			}

			Object rateObject = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabInsuranceData/input_Rate'), 'xpath', 'equals',
					"//*[@id='insuranceCoverage']/div[5]/table/tbody["+i+"]/tr[1]/td[7]/div/input", true)

			'Ambil nilai dari rate'
			String textRate = WebUI.getAttribute(rateObject,'value').replaceAll("\\s","")

			'Mengubah nilai persen menjadi desimal dengan format yang telah diinsialisasi'
			Float rate = decimalFormat.parse(textRate).floatValue()

			'Ambil nilai dari persentase sum insured'
			String textSumInsuredPercent = WebUI.getAttribute(sumInsuredPercentObject,'value').replaceAll("\\s","")

			'Mengubah nilai persen menjadi desimal'
			Float sumInsuredPercent = decimalFormat.parse(textSumInsuredPercent).floatValue();

			'Ambil nilai coverage amount'
			String textAmount = WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabInsuranceData/input_Coverage Amount MF'),'value').replace(",","")
			BigDecimal Amount = Long.parseLong(textAmount)

			Object numberOfMonthObject =  WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabInsuranceData/td_numberOfMonth'), 'xpath', 'equals',
					"//*[@id='insuranceCoverage']/div[5]/table/tbody["+i+"]/tr[1]/td[3]", true)
			'Ambil nilai number of month'
			String textNumberOfMonth = WebUI.getText(numberOfMonthObject)

			BigDecimal numberOfMonth = Integer.parseInt(textNumberOfMonth)

			Object mainPremiObject = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabInsuranceData/td_MainPremiumAmt'), 'xpath', 'equals',
					"//*[@id='insuranceCoverage']/div[5]/table/tbody["+i+"]/tr[1]/td[8]", true)

			'Ambil nilai Main Premium Amount'
			String textMainPremi = WebUI.getText(mainPremiObject).replace(",","").replace(".00","")

			'Perhitungan Main Premium Amount'
			BigDecimal Result = Math.round(Amount*sumInsuredPercent*rate*(numberOfMonth/12)/10)*10

			'Verifikasi/memastikan nilai main premium amount pada confins sama dengan perhitungan'
			WebUI.verifyMatch(textMainPremi,Result.toString(),false, FailureHandling.OPTIONAL)

			'Tambahkan main premium ke total main premium'
			totalMainPremiumResult+=Result

			ArrayList<WebElement> variableAddCovAll = driver.findElements(By.cssSelector('#insuranceCoverage > div:nth-child(2) > div > label'))

			'Mengambil dan menyimpan jumlah additional coverage'
			int countAddCov = variableAddCovAll.size()
			println(countAddCov+"countaddcov")

			int flagLoading = 0
			'Looping additional coverage'
			for (int j = 1; j <= countAddCov; j++) {
				println(j+"j")
				BigDecimal resultAddtPremi=0
				int countSumInsuredAmount = 0

				Object addCovYearCheckbox = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabInsuranceData/input_Flood_checkboxLabel TP'),
						'xpath', 'equals', ((('//*[@id=\'insuranceCoverage\']/div[5]/table/tbody[' + i) + ']/tr[') + (j + 2)) + ']/td[5]/div/div/label/input',
						true)

				Object modifySumInsuredAmount = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabInsuranceData/select_SumInsuredAmountFlood'),
						'xpath', 'equals', ((('//div[@id=\'insuranceCoverage\']/div[5]/table/tbody[' + i) + ']/tr[') + (j + 2)) + ']/td[6]/div/div/select',
						true)

				'Pengecekan untuk flagging sum insured amount dari additional coverage ada atau tidak'
				if (WebUI.verifyElementPresent(modifySumInsuredAmount, 2, FailureHandling.OPTIONAL)) {
					countSumInsuredAmount = 1
				}


				Object modifyAddtRateObject = WebUI.modifyObjectProperty(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabInsuranceData/input_AddtRate'),'xpath','equals',"//div[@id='insuranceCoverage']/div[5]/table/tbody["+i+"]/tr["+(j+2)+"]/td[7]/div/span/div/input",true)

				'Ambil nilai dari rate additional coverage '
				String textAddCovRate = WebUI.getAttribute(modifyAddtRateObject,'value').replaceAll("\\s","").replace(",","")

				Float AddtRate = 0.00
				'Jika additional coverage tercentang'
				if(WebUI.verifyElementChecked(addCovYearCheckbox,2,FailureHandling.OPTIONAL)){
					'Pengecekan jika sum insured amount ada atau tidak'
					if(countSumInsuredAmount==1){
						AddtRate = Long.parseLong(textAddCovRate)
						'Perhitungan nilai additional premi'
						resultAddtPremi = Math.round(AddtRate*(numberOfMonth/12))
					}
					else{

						'Mengubah nilai persen menjadi desimal dengan format yang telah diinsialisasi dan simpan nilainya'
						AddtRate = decimalFormat.parse(textAddCovRate).floatValue()

						'Perhitungan nilai additional premi'
						resultAddtPremi = Math.round(Result*AddtRate*(numberOfMonth/12))
					}

				}
				Object AddtAmtObject = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabInsuranceData/td_FloodAmt'),'xpath','equals',"//*[@id='insuranceCoverage']/div[5]/table/tbody["+i+"]/tr["+(j+2)+"]/td[8]",true)

				'ambil nilai additional premi dari confins'
				String textAddtPremiAmt = WebUI.getText(AddtAmtObject).replace(",","")

				println(textAddtPremiAmt+", "+resultAddtPremi)
				'Verifikasi nilai additional premi confins sama dengan hasil penghitungan pada katalon'
				WebUI.verifyMatch(textAddtPremiAmt, String.format("%.2f", resultAddtPremi),false)


				totalResultAddtPremi+=resultAddtPremi
			}
			println("totalresultaddtpremi "+totalResultAddtPremi)
			'tambahkan additional premi ke total additional premi'
			totalAdditionalPremiumResult+=totalResultAddtPremi


			BigDecimal resultTotalPremiPerYear = 0

			'tambahkan main premi dan additional premi untuk menghitung total premi per tahunnya'
			resultTotalPremiPerYear = Result+totalResultAddtPremi

			Object totalPremiPerYearObject = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabInsuranceData/td_TotalPremiPerYear'),'xpath','equals',"//*[@id='insuranceCoverage']/div[5]/table/tbody["+i+"]/tr["+(countAddCov+3)+"]/td[8]",true)

			'ambil nilai total premi per tahun dari confins'
			String textTotalPremiPerYear = WebUI.getText(totalPremiPerYearObject).replace(",","")
			println(textTotalPremiPerYear+","+resultTotalPremiPerYear)
			'verifikasi nilai total premi per tahun dari confins sesuai dengan perhitungan'
			WebUI.verifyMatch(textTotalPremiPerYear, String.format("%.2f", resultTotalPremiPerYear),false)

			if(counterCap==1&&WebUI.verifyOptionSelectedByLabel(paidByObject,'(?i)CUSTOMER',true,20,FailureHandling.OPTIONAL)){
				capitalizeAmountResult+=resultTotalPremiPerYear
			}


		}
		ArrayList<BigDecimal> totalResult = new ArrayList<BigDecimal>()
		totalResult.add(totalMainPremiumResult)
		totalResult.add(totalAdditionalPremiumResult)
		totalResult.add(counterPaidByMF)
		totalResult.add(capitalizeAmountResult)
		return totalResult
	}
}
