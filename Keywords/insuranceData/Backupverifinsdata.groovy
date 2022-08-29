//package insuranceData
//
//import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
//import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
//import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
//import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
//import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
//
//import com.kms.katalon.core.annotation.Keyword
//import com.kms.katalon.core.checkpoint.Checkpoint
//import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
//import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
//import com.kms.katalon.core.model.FailureHandling
//import com.kms.katalon.core.testcase.TestCase
//import com.kms.katalon.core.testdata.TestData
//import com.kms.katalon.core.testobject.TestObject
//import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
//import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
//import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
//
//import internal.GlobalVariable
//
//public class test {
//	@Keyword
//	public test(){
//		BigDecimal totalMainPremiumResult = 0, totalAdditionalPremiumResult = 0, totalFeeResult = 0, totalPremitoCustResult = 0, totalPremitoCustAftDiscountResult=0, capitalizeAmountResult=0
//
//				'Inisialisasi Driver'
//				WebDriver driver = DriverFactory.getWebDriver()
//
//				'Inisialisasi Variabel'
//				ArrayList<WebElement> variable = driver.findElements(By.cssSelector('#insuranceCoverage > div[formarrayname=AppInsMainCvgs] > table tbody'))
//
//				//dimana css_selector_name adalah elemen dari parent atas object yang ingin dilacak, dan div tergantung daripada bentuk element html tersebut
//				'Menghitung count (size dari variabel) yang akan digunakan sebagai total banyaknya tahun pada insurance '
//				int count = variable.size()
//
//				int counterPaidByMF=0, counterCap = 0
//
//		//		int countFlood = 0, countTPL = 0, countAOG = 0, countSRCC = 0, countTJHTP = 0, countKDUP = 0, countTerrorist = 0
//		//		if(WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabInsuranceData/select_SumInsuredAmountFlood'),2,FailureHandling.OPTIONAL)){
//		//			countFlood = 1
//		//		}
//		//		if(WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabInsuranceData/select_SumInsuredAmountTPL'),2,FailureHandling.OPTIONAL)){
//		//			countTPL = 1
//		//		}
//		//		if(WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabInsuranceData/select_SumInsuredAmountAOG'),2,FailureHandling.OPTIONAL)){
//		//			countAOG = 1
//		//		}
//		//		if(WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabInsuranceData/select_SumInsuredAmountSRCC'),2,FailureHandling.OPTIONAL)){
//		//			countSRCC = 1
//		//		}
//		//		if(WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabInsuranceData/select_SumInsuredAmountTJHTP'),2,FailureHandling.OPTIONAL)){
//		//			countTJHTP = 1
//		//		}
//		//		if(WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabInsuranceData/select_SumInsuredAmountKDUP'),2,FailureHandling.OPTIONAL)){
//		//			countKDUP = 1
//		//		}
//		//		if(WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabInsuranceData/select_SumInsuredAmountTerrorist'),2,FailureHandling.OPTIONAL)){
//		//			countTerrorist = 1
//		//		}
//				'Looping data insurance untuk verify'
//				for (int i = 1; i <= count; i++) {
//					BigDecimal resultFloodPremi=0, resultTPLPremi=0, resultAOGPremi=0, resultSRCCPremi=0, resultTJHTPPremi=0, resultKDUPPremi=0, resultTerroristPremi=0
//					counterCap=0
//					'Inisialisasi Format untuk mendapatkan nilai desimal dari nilai persen'
//					NumberFormat decimalFormat = NumberFormat.getPercentInstance()
//
//					Object capitalizeObject = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabInsuranceData/input_Capitalized'),'xpath','equals',"//*[@id='insuranceCoverage']/div[5]/table/tbody["+i+"]/tr[1]/td[1]/div/input",true)
//
//					if(WebUI.verifyElementChecked(capitalizeObject,5,FailureHandling.OPTIONAL)){
//						counterCap=1
//					}
//					Object paidByObject = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabInsuranceData/select_PaidBy'),'xpath','equals',"//*[@id='insuranceCoverage']/div[5]/table/tbody["+i+"]/tr[1]/td[2]/div/select",true)
//
//					Object sumInsuredPercentObject = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabInsuranceData/input_SumInsuredPercentage'), 'xpath', 'equals',
//							"//*[@id='insuranceCoverage']/div[5]/table/tbody["+i+"]/tr[1]/td[5]/div/input", true)
//
//					Object mainCoverageObject = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabInsuranceData/select_MainCoverageTP'),'xpath','equals',"//*[@id='insuranceCoverage']/div[5]/table/tbody["+i+"]/tr[1]/td[6]/span[1]/select",true)
//
//		//			Object floodYearCheckbox = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabInsuranceData/input_Flood_checkboxLabel TP'),'xpath','equals',"//*[@id='insuranceCoverage']/div[5]/table/tbody["+i+"]/tr[3]/td[6]/div/div/label/input",true)
//		//			Object tplYearCheckbox = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabInsuranceData/input_TPL_checkboxLabel TP'),'xpath','equals',"//*[@id='insuranceCoverage']/div[5]/table/tbody["+i+"]/tr[4]/td[6]/div/div/label/input",true)
//		//			Object aogYearCheckbox = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabInsuranceData/input_Act of God_checkboxLabel TP'),'xpath','equals',"//*[@id='insuranceCoverage']/div[5]/table/tbody["+i+"]/tr[5]/td[6]/div/div/label/input",true)
//		//			Object srccYearCheckbox = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabInsuranceData/input_SRCC_checkboxLabel TP'),'xpath','equals',"//*[@id='insuranceCoverage']/div[5]/table/tbody["+i+"]/tr[6]/td[6]/div/div/label/input",true)
//		//			Object tjhtpYearCheckbox = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabInsuranceData/input_Tanggung Jawab Hukum Terhadap Penumpang_checkboxLabel TP'),'xpath','equals',"//*[@id='insuranceCoverage']/div[5]/table/tbody["+i+"]/tr[7]/td[6]/div/div/label/input",true)
//		//			Object kdupYearCheckbox = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabInsuranceData/input_Kecelakaan Diri Untuk Penumpang_checkboxLabel TP'),'xpath','equals',"//*[@id='insuranceCoverage']/div[5]/table/tbody["+i+"]/tr[8]/td[6]/div/div/label/input",true)
//		//			Object terroristYearCheckbox = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabInsuranceData/input_Terrorist_checkboxLabel TP'),'xpath','equals',"//*[@id='insuranceCoverage']/div[5]/table/tbody["+i+"]/tr[9]/td[6]/div/div/label/input",true)
//		//			Object loadingYearCheckbox = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabInsuranceData/input_LoadingCheckboxTP'),'xpath','equals',"//*[@id='insuranceCoverage']/div[5]/table/tbody["+i+"]/tr[10]/td[6]/div/div/label/input",true)
//
//					if(WebUI.verifyOptionSelectedByLabel(paidByObject,'(?i)MULTIFINANCE',true,20,FailureHandling.OPTIONAL)&&counterPaidByMF==0){
//						counterPaidByMF = 1
//					}
//
//					Object rateObject = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabInsuranceData/input_Rate'), 'xpath', 'equals',
//							"//*[@id='insuranceCoverage']/div[5]/table/tbody["+i+"]/tr[1]/td[8]/div/input", true)
//
//					'Ambil nilai dari rate'
//					String textRate = WebUI.getAttribute(rateObject,'value').replaceAll("\\s","")
//
//					'Mengubah nilai persen menjadi desimal dengan format yang telah diinsialisasi'
//					Float rate = decimalFormat.parse(textRate).floatValue()
//
//					'Ambil nilai dari persentase sum insured'
//					String textSumInsuredPercent = WebUI.getAttribute(sumInsuredPercentObject,'value').replaceAll("\\s","")
//
//					'Mengubah nilai persen menjadi desimal'
//					Float sumInsuredPercent = decimalFormat.parse(textSumInsuredPercent).floatValue();
//
//					'Ambil nilai coverage amount'
//					String textAmount = WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabInsuranceData/input_Coverage Amount MF'),'value').replace(",","")
//					BigDecimal Amount = Long.parseLong(textAmount)
//
//					Object numberOfMonthObject =  WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabInsuranceData/td_numberOfMonth'), 'xpath', 'equals',
//							"//*[@id='insuranceCoverage']/div[5]/table/tbody["+i+"]/tr[1]/td[4]", true)
//					'Ambil nilai number of month'
//					String textNumberOfMonth = WebUI.getText(numberOfMonthObject)
//
//					BigDecimal numberOfMonth = Integer.parseInt(textNumberOfMonth)
//
//					Object mainPremiObject = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabInsuranceData/td_MainPremiumAmt'), 'xpath', 'equals',
//							"//*[@id='insuranceCoverage']/div[5]/table/tbody["+i+"]/tr[1]/td[9]", true)
//
//					'Ambil nilai Main Premium Amount'
//					String textMainPremi = WebUI.getText(mainPremiObject).replace(",","").replace(".00","")
//
//					'Perhitungan Main Premium Amount'
//					BigDecimal Result = Math.round(Amount*sumInsuredPercent*rate*(numberOfMonth/12)/10)*10
//
//					'Verifikasi/memastikan nilai main premium amount pada confins sama dengan perhitungan'
//					WebUI.verifyMatch(textMainPremi,Result.toString(),false, FailureHandling.OPTIONAL)
//
//					'Tambahkan main premium ke total main premium'
//					totalMainPremiumResult+=Result
//
//					Object floodRateObject= WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabInsuranceData/input_Flood_CustAddPremiRate'),'xpath','equals',"//*[@id='insuranceCoverage']/div[5]/table/tbody["+i+"]/tr[3]/td[8]/div/span/div/input",true)
//					Object tplRateObject= WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabInsuranceData/input_TPL_CustAddPremiRate'),'xpath','equals',"//*[@id='insuranceCoverage']/div[5]/table/tbody["+i+"]/tr[4]/td[8]/div/span/div/input",true)
//					Object aogRateObject= WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabInsuranceData/input_Act of God_CustAddPremiRate'),'xpath','equals',"//*[@id='insuranceCoverage']/div[5]/table/tbody["+i+"]/tr[5]/td[8]/div/span/div/input",true)
//					Object srccRateObject= WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabInsuranceData/input_SRCC_CustAddPremiRate'),'xpath','equals',"//*[@id='insuranceCoverage']/div[5]/table/tbody["+i+"]/tr[6]/td[8]/div/span/div/input",true)
//					Object tjhtpRateObject= WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabInsuranceData/input_Tanggung Jawab Hukum Terhadap Penumpang_CustAddPremiRate'),'xpath','equals',"//*[@id='insuranceCoverage']/div[5]/table/tbody["+i+"]/tr[7]/td[8]/div/span/div/input",true)
//					Object kdupRateObject= WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabInsuranceData/input_Kecelakaan Diri Untuk Penumpang_CustAddPremiRate'),'xpath','equals',"//*[@id='insuranceCoverage']/div[5]/table/tbody["+i+"]/tr[8]/td[8]/div/span/div/input",true)
//					Object terroristRateObject= WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabInsuranceData/input_Terrorist_CustAddPremiRate'),'xpath','equals',"//*[@id='insuranceCoverage']/div[5]/table/tbody["+i+"]/tr[9]/td[8]/div/span/div/input",true)
//					Object loadingRateObject= WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabInsuranceData/input_Loading_CustAddPremiRate'),'xpath','equals',"//*[@id='insuranceCoverage']/div[5]/table/tbody["+i+"]/tr[10]/td[8]/div/span/div/input",true)
//
//					'Ambil nilai dari rate additional coverage flood'
//					String textFloodRate = WebUI.getAttribute(floodRateObject,'value').replaceAll("\\s","").replace(",","")
//
//
//					'Ambil nilai dari rate additional coverage tpl'
//					String textTPLRate = WebUI.getAttribute(tplRateObject,'value').replace(",","").replaceAll("\\s","")
//
//
//					'Ambil nilai dari rate additional coverage act of god'
//					String textAOGRate = WebUI.getAttribute(aogRateObject,'value').replaceAll("\\s","").replace(",","")
//
//
//					'Ambil nilai dari rate additional coverage srcc'
//					String textSRCCRate = WebUI.getAttribute(srccRateObject,'value').replaceAll("\\s","").replace(",","")
//
//
//					'Ambil nilai dari rate additional coverage tanggung jawab hukum terhadap penumpang'
//					String textTJHTPRate = WebUI.getAttribute(tjhtpRateObject,'value').replace(",","").replaceAll("\\s","")
//
//
//					'Ambil nilai dari rate additional coverage kecelakaan diri untuk penumpang'
//					String textKDUPRate = WebUI.getAttribute(kdupRateObject,'value').replace(",","").replaceAll("\\s","")
//
//
//					'Ambil nilai dari rate additional coverage terrorist'
//					String textTerroristRate = WebUI.getAttribute(terroristRateObject,'value').replaceAll("\\s","").replace(",","")
//
//
//					'Ambil nilai dari rate additional coverage loading'
//					String textLoadingRate = WebUI.getAttribute(loadingRateObject,'value').replaceAll("\\s","").replace(",","")
//
//
//					Float floodRate = 0.00
//					'Jika additional coverage flood tercentang'
//					if(WebUI.verifyElementChecked(floodYearCheckbox,2,FailureHandling.OPTIONAL)){
//						if(countFlood==1){
//							floodRate = Long.parseLong(textFloodRate)
//							resultFloodPremi = Math.round(floodRate*(numberOfMonth/12))
//						}
//						else{
//							'Mengubah nilai persen menjadi desimal dengan format yang telah diinsialisasi dan simpan nilainya'
//							floodRate = decimalFormat.parse(textFloodRate).floatValue()
//							'Perhitungan nilai additional premi flood'
//							resultFloodPremi = Math.round(Result*floodRate*(numberOfMonth/12))
//						}
//
//					}
//					Float tplRate = 0.00
//					'Jika tpl tercentang'
//					if(WebUI.verifyElementChecked(tplYearCheckbox,2,FailureHandling.OPTIONAL)){
//						if(countTPL==1){
//							'simpan nilai ratenya'
//							tplRate = Long.parseLong(textTPLRate)
//							'Perhitungan nilai additional premi tpl'
//							resultTPLPremi = Math.round(tplRate*(numberOfMonth/12))
//						}
//						else{
//							tplRate = decimalFormat.parse(textTPLRate).floatValue()
//							resultTPLPremi = Math.round(Result*tplRate*(numberOfMonth/12))
//						}
//
//
//					}
//					Float aogRate = 0.00
//					'jika act of god tercentang'
//					if(WebUI.verifyElementChecked(aogYearCheckbox,2,FailureHandling.OPTIONAL)){
//						if(countAOG==1){
//							aogRate = Long.parseLong(textAOGRate)
//							resultAOGPremi = Math.round(aogRate*(numberOfMonth/12))
//						}
//						else{
//							'Mengubah nilai persen menjadi desimal dengan format yang telah diinsialisasi dan simpan nilainya'
//							aogRate = decimalFormat.parse(textAOGRate).floatValue()
//							'Perhitungan nilai additional premi act of god'
//							resultAOGPremi = Math.round(Result*aogRate*(numberOfMonth/12))
//						}
//
//					}
//					Float srccRate = 0.00
//					'jika srcc tercentang'
//					if(WebUI.verifyElementChecked(srccYearCheckbox,2,FailureHandling.OPTIONAL)){
//						if(countSRCC ==1){
//							srccRate = Long.parseLong(textSRCCRate)
//							resultSRCCPremi = Math.round(srccRate*(numberOfMonth/12))
//
//						}
//						else{
//							'Mengubah nilai persen menjadi desimal dengan format yang telah diinsialisasi dan simpan nilainya'
//							srccRate = decimalFormat.parse(textSRCCRate).floatValue()
//							'Perhitungan nilai additional premi srcc'
//							resultSRCCPremi = Math.round(Result*srccRate*(numberOfMonth/12))
//						}
//
//					}
//					Float tjhtpRate = 0.00
//					'jika tanggung jawab hukum terhadap penumpang tercentang'
//					if(WebUI.verifyElementChecked(tjhtpYearCheckbox,2,FailureHandling.OPTIONAL)){
//						if(countTJHTP==1){
//							'simpan nilai ratenya'
//							tjhtpRate = Long.parseLong(textTJHTPRate)
//							'Perhitungan nilai additional premi tanggung jawab hukum terhadap penumpang'
//							resultTJHTPPremi = Math.round(tjhtpRate*(numberOfMonth/12))
//						}
//						else{
//							'Mengubah nilai persen menjadi desimal dengan format yang telah diinsialisasi dan simpan nilainya'
//							tjhtpRate = decimalFormat.parse(textTJHTPRate).floatValue()
//							resultTJHTPPremi = Math.round(Result*tjhtpRate*(numberOfMonth/12))
//						}
//
//					}
//					Float kdupRate = 0.00
//					'jika kecelekaan diri untuk penumpang tercentang'
//					if(WebUI.verifyElementChecked(kdupYearCheckbox,2,FailureHandling.OPTIONAL)){
//						if(countKDUP==1){
//							'simpan nilai ratenya'
//							kdupRate = Long.parseLong(textKDUPRate)
//							'Perhitungan nilai additional premi kecelakaan diri untuk penumpang'
//							resultKDUPPremi = Math.round(kdupRate*(numberOfMonth/12))
//						}
//						else{
//							kdupRate = decimalFormat.parse(textKDUPRate).floatValue()
//							resultKDUPPremi = Math.round(Result*kdupRate*(numberOfMonth/12))
//						}
//
//					}
//					Float terroristRate = 0.00
//					'jika terrorist tercentang'
//					if(WebUI.verifyElementChecked(terroristYearCheckbox,2,FailureHandling.OPTIONAL)){
//						if(countTerrorist==1){
//							terroristRate = Long.parseLong(textTerroristRate)
//							resultTerroristPremi = Math.round(terroristRate*(numberOfMonth/12))
//						}
//						else{
//							'Mengubah nilai persen menjadi desimal dengan format yang telah diinsialisasi dan simpan nilainya'
//							terroristRate = decimalFormat.parse(textTerroristRate).floatValue()
//							'Perhitungan nilai additional premi terrorist'
//							resultTerroristPremi = Math.round(Result*terroristRate*(numberOfMonth/12))
//						}
//
//					}
//					Float loadingRate = 0.00
//					'jika rate loading tidak kosong'
//					if(textLoadingRate.length()>1&&WebUI.verifyElementChecked(loadingYearCheckbox,2,FailureHandling.OPTIONAL)){
//						'Mengubah nilai persen menjadi desimal dengan format yang telah diinsialisasi dan simpan nilainya'
//						loadingRate = decimalFormat.parse(textLoadingRate).floatValue()
//					}
//
//					'Perhitungan nilai additional premi loading'
//					BigDecimal resultLoadingPremi = Math.round(Result*loadingRate*(numberOfMonth/12))
//
//					Object floodPremiAmtObject = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabInsuranceData/td_FloodAmt'),'xpath','equals',"//*[@id='insuranceCoverage']/div[5]/table/tbody["+i+"]/tr[3]/td[9]",true)
//					Object tplPremiAmtObject = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabInsuranceData/td_TPLAmt'),'xpath','equals',"//*[@id='insuranceCoverage']/div[5]/table/tbody["+i+"]/tr[4]/td[9]",true)
//					Object aogPremiAmtObject = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabInsuranceData/td_AOGAmt'),'xpath','equals',"//*[@id='insuranceCoverage']/div[5]/table/tbody["+i+"]/tr[5]/td[9]",true)
//					Object srccPremiAmtObject = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabInsuranceData/td_SRCCAmt'),'xpath','equals',"//*[@id='insuranceCoverage']/div[5]/table/tbody["+i+"]/tr[6]/td[9]",true)
//					Object tjhtpPremiAmtObject = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabInsuranceData/td_TJHTPAmt'),'xpath','equals',"//*[@id='insuranceCoverage']/div[5]/table/tbody["+i+"]/tr[7]/td[9]",true)
//					Object kdupPremiAmtObject = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabInsuranceData/td_KDUPAmt'),'xpath','equals',"//*[@id='insuranceCoverage']/div[5]/table/tbody["+i+"]/tr[8]/td[9]",true)
//					Object terroristPremiAmtObject = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabInsuranceData/td_TerroristAmt'),'xpath','equals',"//*[@id='insuranceCoverage']/div[5]/table/tbody["+i+"]/tr[9]/td[9]",true)
//					Object loadingPremiAmtObject = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabInsuranceData/td_LoadingAmt'),'xpath','equals',"//*[@id='insuranceCoverage']/div[5]/table/tbody["+i+"]/tr[10]/td[9]",true)
//
//					'ambil nilai additional premi flood dari confins'
//					String textFloodPremiAmt = WebUI.getText(floodPremiAmtObject).replace(",","")
//					'ambil nilai additional premi tpl dari confins'
//					String textTPLPremiAmt = WebUI.getText(tplPremiAmtObject).replace(",","")
//					'ambil nilai additional premi act of god dari confins'
//					String textAOGPremiAmt = WebUI.getText(aogPremiAmtObject).replace(",","")
//					'ambil nilai additional premi srcc dari confins'
//					String textSRCCPremiAmt = WebUI.getText(srccPremiAmtObject).replace(",","")
//					'ambil nilai additional premi tanggung jawab hukum terhdap penumpang dari confins'
//					String textTJHTPPremiAmt = WebUI.getText(tjhtpPremiAmtObject).replace(",","")
//					'ambil nilai additional premi kecelakaan diri untuk penumpang dari confins'
//					String textKDUPPremiAmt = WebUI.getText(kdupPremiAmtObject).replace(",","")
//					'ambil nilai additional premi terrorist dari confins'
//					String textTerroristPremiAmt = WebUI.getText(terroristPremiAmtObject).replace(",","")
//					'ambil nilai additional premi loading dari confins'
//					String textLoadingPremiAmt = WebUI.getText(loadingPremiAmtObject).replace(",","")
//
//					'Verifikasi nilai additional premi confins sama dengan hasil penghitungan pada katalon'
//					WebUI.verifyMatch(textFloodPremiAmt, String.format("%.2f", resultFloodPremi),false)
//					'Verifikasi nilai additional premi confins sama dengan hasil penghitungan pada katalon'
//					WebUI.verifyMatch(textTPLPremiAmt, String.format("%.2f", resultTPLPremi),false)
//					'Verifikasi nilai additional premi confins sama dengan hasil penghitungan pada katalon'
//					WebUI.verifyMatch(textAOGPremiAmt, String.format("%.2f", resultAOGPremi),false)
//					'Verifikasi nilai additional premi confins sama dengan hasil penghitungan pada katalon'
//					WebUI.verifyMatch(textSRCCPremiAmt, String.format("%.2f", resultSRCCPremi),false)
//					'Verifikasi nilai additional premi confins sama dengan hasil penghitungan pada katalon'
//					WebUI.verifyMatch(textTJHTPPremiAmt, String.format("%.2f", resultTJHTPPremi),false)
//					'Verifikasi nilai additional premi confins sama dengan hasil penghitungan pada katalon'
//					WebUI.verifyMatch(textKDUPPremiAmt, String.format("%.2f", resultKDUPPremi),false)
//					'Verifikasi nilai additional premi confins sama dengan hasil penghitungan pada katalon'
//					WebUI.verifyMatch(textTerroristPremiAmt, String.format("%.2f", resultTerroristPremi),false)
//					'Verifikasi nilai additional premi confins sama dengan hasil penghitungan pada katalon'
//					WebUI.verifyMatch(textLoadingPremiAmt, String.format("%.2f", resultLoadingPremi),false)
//
//					'tambahkan additional premi ke total additional premi'
//					totalAdditionalPremiumResult+=(resultFloodPremi+resultAOGPremi+resultTPLPremi+resultSRCCPremi+resultTJHTPPremi+resultKDUPPremi+resultTerroristPremi+resultLoadingPremi)
//
//					'tambahkan main premi dan additional premi untuk menghitung total premi per tahunnya'
//					BigDecimal resultTotalPremiPerYear = Result+resultFloodPremi+resultAOGPremi+resultTPLPremi+resultSRCCPremi+resultTJHTPPremi+resultKDUPPremi+resultTerroristPremi+resultLoadingPremi
//
//					Object totalPremiPerYearObject = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabInsuranceData/td_TotalPremiPerYear'),'xpath','equals',"//*[@id='insuranceCoverage']/div[5]/table/tbody["+i+"]/tr[11]/td[9]",true)
//
//					'ambil nilai total premi per tahun dari confins'
//					String textTotalPremiPerYear = WebUI.getText(totalPremiPerYearObject).replace(",","")
//
//					'verifikasi nilai total premi per tahun dari confins sesuai dengan perhitungan'
//					WebUI.verifyMatch(textTotalPremiPerYear, String.format("%.2f", resultTotalPremiPerYear),false)
//
//					if(counterCap==1){
//						capitalizeAmountResult+=resultTotalPremiPerYear
//					}
//
//
//				}
//				ArrayList<BigDecimal> totalResult = new ArrayList<BigDecimal>()
//				totalResult.add(totalMainPremiumResult)
//				totalResult.add(totalAdditionalPremiumResult)
//				totalResult.add(counterPaidByMF)
//				totalResult.add(capitalizeAmountResult)
//				return totalResult
//	}
//}
