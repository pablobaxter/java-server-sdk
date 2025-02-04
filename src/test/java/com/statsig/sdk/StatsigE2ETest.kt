package com.statsig.sdk

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withTimeout
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import org.junit.Before
import org.junit.Test

private const val DOWNLOAD_CONFIG_SPECS =
    "{\"dynamic_configs\":[{\"name\":\"test_config\",\"type\":\"dynamic_config\",\"salt\":\"50ad5c60-9e7a-42ce-86c6-c49035185b14\",\"enabled\":true,\"defaultValue\":{\"number\":4,\"string\":\"default\",\"boolean\":true},\"rules\":[{\"name\":\"1kNmlB23wylPFZi1M0Divl\",\"groupName\":\"statsig email\",\"passPercentage\":100,\"conditions\":[{\"type\":\"user_field\",\"targetValue\":[\"@statsig.com\"],\"operator\":\"str_contains_any\",\"field\":\"email\",\"additionalValues\":{}}],\"returnValue\":{\"number\":7,\"string\":\"statsig\",\"boolean\":false},\"id\":\"1kNmlB23wylPFZi1M0Divl\",\"salt\":\"f2ac6975-174d-497e-be7f-599fea626132\"}]},{\"name\":\"sample_experiment\",\"type\":\"dynamic_config\",\"salt\":\"f8aeba58-18fb-4f36-9bbd-4c611447a912\",\"enabled\":true,\"defaultValue\":{\"experiment_param\":\"control\"},\"rules\":[{\"name\":\"\",\"groupName\":\"experimentSize\",\"passPercentage\":100,\"conditions\":[{\"type\":\"user_bucket\",\"targetValue\":[0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,41,42,43,44,45,46,47,48,49,50,51,52,53,54,55,56,57,58,59,60,61,62,63,64,65,66,67,68,69,70,71,72,73,74,75,76,77,78,79,80,81,82,83,84,85,86,87,88,89,90,91,92,93,94,95,96,97,98,99,100,101,102,103,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,134,135,136,137,138,139,140,141,142,143,144,145,146,147,148,149,150,151,152,153,154,155,156,157,158,159,160,161,162,163,164,165,166,167,168,169,170,171,172,173,174,175,176,177,178,179,180,181,182,183,184,185,186,187,188,189,190,191,192,193,194,195,196,197,198,199,200,201,202,203,204,205,206,207,208,209,210,211,212,213,214,215,216,217,218,219,220,221,222,223,224,225,226,227,228,229,230,231,232,233,234,235,236,237,238,239,240,241,242,243,244,245,246,247,248,249,250,251,252,253,254,255,256,257,258,259,260,261,262,263,264,265,266,267,268,269,270,271,272,273,274,275,276,277,278,279,280,281,282,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,313,314,315,316,317,318,319,320,321,322,323,324,325,326,327,328,329,330,331,332,333,334,335,336,337,338,339,340,341,342,343,344,345,346,347,348,349,350,351,352,353,354,355,356,357,358,359,360,361,362,363,364,365,366,367,368,369,370,371,372,373,374,375,376,377,378,379,380,381,382,383,384,385,386,387,388,389,390,391,392,393,394,395,396,397,398,399,400,401,402,403,404,405,406,407,408,409,410,411,412,413,414,415,416,417,418,419,420,421,422,423,424,425,426,427,428,429,430,431,432,433,434,435,436,437,438,439,440,441,442,443,444,445,446,447,448,449,450,451,452,453,454,455,456,457,458,459,460,461,462,463,464,465,466,467,468,469,470,471,472,473,474,475,476,477,478,479,480,481,482,483,484,485,486,487,488,489,490,491,492,493,494,495,496,497,498,499,500,501,502,503,504,505,506,507,508,509,510,511,512,513,514,515,516,517,518,519,520,521,522,523,524,525,526,527,528,529,530,531,532,533,534,535,536,537,538,539,540,541,542,543,544,545,546,547,548,549,550,551,552,553,554,555,556,557,558,559,560,561,562,563,564,565,566,567,568,569,570,571,572,573,574,575,576,577,578,579,580,581,582,583,584,585,586,587,588,589,590,591,592,593,594,595,596,597,598,599,600,601,602,603,604,605,606,607,608,609,610,611,612,613,614,615,616,617,618,619,620,621,622,623,624,625,626,627,628,629,630,631,632,633,634,635,636,637,638,639,640,641,642,643,644,645,646,647,648,649,650,651,652,653,654,655,656,657,658,659,660,661,662,663,664,665,666,667,668,669,670,671,672,673,674,675,676,677,678,679,680,681,682,683,684,685,686,687,688,689,690,691,692,693,694,695,696,697,698,699,700,701,702,703,704,705,706,707,708,709,710,711,712,713,714,715,716,717,718,719,720,721,722,723,724,725,726,727,728,729,730,731,732,733,734,735,736,737,738,739,740,741,742,743,744,745,746,747,748,749,750,751,752,753,754,755,756,757,758,759,760,761,762,763,764,765,766,767,768,769,770,771,772,773,774,775,776,777,778,779,780,781,782,783,784,785,786,787,788,789,790,791,792,793,794,795,796,797,798,799,800,801,802,803,804,805,806,807,808,809,810,811,812,813,814,815,816,817,818,819,820,821,822,823,824,825,826,827,828,829,830,831,832,833,834,835,836,837,838,839,840,841,842,843,844,845,846,847,848,849,850,851,852,853,854,855,856,857,858,859,860,861,862,863,864,865,866,867,868,869,870,871,872,873,874,875,876,877,878,879,880,881,882,883,884,885,886,887,888,889,890,891,892,893,894,895,896,897,898,899,900,901,902,903,904,905,906,907,908,909,910,911,912,913,914,915,916,917,918,919,920,921,922,923,924,925,926,927,928,929,930,931,932,933,934,935,936,937,938,939,940,941,942,943,944,945,946,947,948,949,950,951,952,953,954,955,956,957,958,959,960,961,962,963,964,965,966,967,968,969,970,971,972,973,974,975,976,977,978,979,980,981,982,983,984,985,986,987,988,989,990,991,992,993,994,995,996,997,998,999],\"operator\":\"none\",\"field\":null,\"additionalValues\":{\"salt\":\"00cddb4b-69f5-47c6-aeaa-5bac43cf45a4\"}}],\"returnValue\":{},\"id\":\"\",\"salt\":\"\"},{\"name\":\"2RamGsERWbWMIMnSfOlQuX\",\"groupName\":\"Control\",\"passPercentage\":100,\"conditions\":[{\"type\":\"user_bucket\",\"targetValue\":500,\"operator\":\"lt\",\"field\":null,\"additionalValues\":{\"salt\":\"f8aeba58-18fb-4f36-9bbd-4c611447a912\"}}],\"returnValue\":{\"experiment_param\":\"control\"},\"id\":\"2RamGsERWbWMIMnSfOlQuX\",\"salt\":\"2RamGsERWbWMIMnSfOlQuX\"},{\"name\":\"2RamGujUou6h2bVNQWhtNZ\",\"groupName\":\"Test\",\"passPercentage\":100,\"conditions\":[{\"type\":\"user_bucket\",\"targetValue\":1000,\"operator\":\"lt\",\"field\":null,\"additionalValues\":{\"salt\":\"f8aeba58-18fb-4f36-9bbd-4c611447a912\"}}],\"returnValue\":{\"experiment_param\":\"test\"},\"id\":\"2RamGujUou6h2bVNQWhtNZ\",\"salt\":\"2RamGujUou6h2bVNQWhtNZ\"}]}],\"feature_gates\":[{\"name\":\"always_on_gate\",\"type\":\"feature_gate\",\"salt\":\"47403b4e-7829-43d1-b1ac-3992a5c1b4ac\",\"enabled\":true,\"defaultValue\":false,\"rules\":[{\"name\":\"6N6Z8ODekNYZ7F8gFdoLP5\",\"groupName\":\"everyone\",\"passPercentage\":100,\"conditions\":[{\"type\":\"public\",\"targetValue\":null,\"operator\":null,\"field\":null,\"additionalValues\":{}}],\"returnValue\":true,\"id\":\"6N6Z8ODekNYZ7F8gFdoLP5\",\"salt\":\"14862979-1468-4e49-9b2a-c8bb100eed8f\"}]},{\"name\":\"on_for_statsig_email\",\"type\":\"feature_gate\",\"salt\":\"4ab7fc7b-c8a0-4ef1-b869-889467678688\",\"enabled\":true,\"defaultValue\":false,\"rules\":[{\"name\":\"7w9rbTSffLT89pxqpyhuqK\",\"groupName\":\"on for statsig emails\",\"passPercentage\":100,\"conditions\":[{\"type\":\"user_field\",\"targetValue\":[\"@statsig.com\"],\"operator\":\"str_contains_any\",\"field\":\"email\",\"additionalValues\":{}}],\"returnValue\":true,\"id\":\"7w9rbTSffLT89pxqpyhuqK\",\"salt\":\"e452510f-bd5b-42cb-a71e-00498a7903fc\"}]}],\"has_updates\":true,\"time\":1631638014811,\"id_lists\":{\"list_1\":true,\"list_2\":true}}"

private data class LogEventInput(
    @SerializedName("events") val events: Array<StatsigEvent>,
)

private const val TEST_TIMEOUT = 10L

/**
 * There are 2 mock gates, 1 mock config, and 1 mock experiment
 * always_on_gate has a single group, Everyone 100%
 * on_for_statsig_email has a single group, email contains "@statsig.com" 100%
 * test_config has a single group, email contains "@statsig.com" 100%
 * - passing returns {number: 7, string: "statsig", boolean: false}
 * - failing (default) returns {number: 4, string: "default", boolean: true}
 * sample_experiment is a 50/50 experiment with a single parameter, experiment_param
 * - ("test" or "control" depending on the user's group)
 */
class StatsigE2ETest {

    private lateinit var gson: Gson
    private lateinit var eventLogInputCompletable: CompletableDeferred<LogEventInput>
    private lateinit var server: MockWebServer
    private lateinit var options: StatsigOptions

    private lateinit var statsigUser: StatsigUser
    private lateinit var randomUser: StatsigUser
    private lateinit var driver: StatsigServer

    private var download_config_count: Int = 0
    private var download_id_list_count: Int = 0

    @Before
    fun setup() {
        gson = Gson()

        eventLogInputCompletable = CompletableDeferred()

        server = MockWebServer().apply {
            dispatcher = object : Dispatcher() {
                @Throws(InterruptedException::class)
                override fun dispatch(request: RecordedRequest): MockResponse {
                    when (request.path) {
                        "/v1/download_config_specs" -> {
                            download_config_count++
                            if (request.getHeader("Content-Type") != "application/json; charset=utf-8") {
                                throw Exception("No content type set!")
                            }
                            return MockResponse().setResponseCode(200).setBody(DOWNLOAD_CONFIG_SPECS)
                        }
                        "/v1/log_event" -> {
                            val logBody = request.body.readUtf8()
                            if (request.getHeader("Content-Type") != "application/json; charset=utf-8") {
                                throw Exception("No content type set!")
                            }
                            eventLogInputCompletable.complete(gson.fromJson(logBody, LogEventInput::class.java))
                            return MockResponse().setResponseCode(200).setBody(DOWNLOAD_CONFIG_SPECS)
                        }
                        "/v1/download_id_list" -> {
                            download_id_list_count++
                            if (request.getHeader("Content-Type") != "application/json; charset=utf-8") {
                                throw Exception("No content type set!")
                            }
                            val body = request.body.readUtf8()
                            var list: Map<String, Any>
                            if (body.contains("list_1")) {
                                list = mapOf("add_ids" to arrayOf("1", "2"), "remove_ids" to arrayOf<String>(), "time" to 1)
                                if (download_id_list_count > 2) {
                                    list = mapOf("add_ids" to arrayOf("3", "4"), "remove_ids" to arrayOf("1"), "time" to download_id_list_count)
                                }
                            } else {
                                list = mapOf("add_ids" to arrayOf("a", "b"), "remove_ids" to arrayOf<String>(), "time" to 1)
                                if (download_id_list_count > 1) {
                                    list = mapOf("add_ids" to arrayOf("c", "d"), "remove_ids" to arrayOf("a", "b"), "time" to download_id_list_count)
                                }
                            }

                            return MockResponse().setResponseCode(200).setBody(gson.toJson(list))
                        }
                    }
                    return MockResponse().setResponseCode(404)
                }
            }
        }

        options = StatsigOptions().apply {
            api = server.url("/v1").toString()
        }

        statsigUser = StatsigUser("123").apply {
            email = "testuser@statsig.com"
        }

        randomUser = StatsigUser("random")
        driver = StatsigServer.create("secret-testcase", options)
    }

    @Test
    fun testFeatureGate() = runBlocking {
        driver.initialize()
        assert(driver.checkGate(statsigUser, "always_on_gate"))
        assert(driver.checkGate(statsigUser, "on_for_statsig_email"))
        assert(!driver.checkGate(randomUser, "on_for_statsig_email"))
        driver.shutdown()

        val eventLogInput = withTimeout(TEST_TIMEOUT) {
            eventLogInputCompletable.await()
        }

        assert(eventLogInput.events.size == 3)
        assert(eventLogInput.events[0].eventName == "statsig::gate_exposure")
        assert(eventLogInput.events[0].eventMetadata!!["gate"].equals("always_on_gate"))
        assert(eventLogInput.events[0].eventMetadata!!["gateValue"].equals("true"))
        assert(eventLogInput.events[0].eventMetadata!!["ruleID"].equals("6N6Z8ODekNYZ7F8gFdoLP5"))

        assert(eventLogInput.events[1].eventName == "statsig::gate_exposure")
        assert(eventLogInput.events[1].eventMetadata!!["gate"].equals("on_for_statsig_email"))
        assert(eventLogInput.events[1].eventMetadata!!["gateValue"].equals("true"))
        assert(eventLogInput.events[1].eventMetadata!!["ruleID"].equals("7w9rbTSffLT89pxqpyhuqK"))

        assert(eventLogInput.events[2].eventName == "statsig::gate_exposure")
        assert(eventLogInput.events[2].eventMetadata!!["gate"].equals("on_for_statsig_email"))
        assert(eventLogInput.events[2].eventMetadata!!["gateValue"].equals("false"))
        assert(eventLogInput.events[2].eventMetadata!!["ruleID"].equals("default"))
    }

    @Test
    fun testDynamicConfig() = runBlocking {
        driver.initialize()
        var config = driver.getConfig(statsigUser, "test_config")
        assert(config.getInt("number", 0) == 7)
        assert(config.getString("string", "") == "statsig")
        assert(!config.getBoolean("boolean", true))
        config = driver.getConfig(randomUser, "test_config")
        assert(config.getInt("number", 0) == 4)
        assert(config.getString("string", "") == "default")
        assert(config.getBoolean("boolean", true))

        var groups = driver._getExperimentGroups("test_config")
        assert(groups["statsig email"]!!.get("value")!!.equals("{number=7.0, string=statsig, boolean=false}"))

        driver.shutdown()

        val eventLogInput = withTimeout(TEST_TIMEOUT) {
            eventLogInputCompletable.await()
        }
        assert(eventLogInput.events.size == 2)
        assert(eventLogInput.events[0].eventName == "statsig::config_exposure")
        assert(eventLogInput.events[0].eventMetadata!!["config"].equals("test_config"))
        assert(eventLogInput.events[0].eventMetadata!!["ruleID"].equals("1kNmlB23wylPFZi1M0Divl"))

        assert(eventLogInput.events[1].eventName == "statsig::config_exposure")
        assert(eventLogInput.events[1].eventMetadata!!["config"].equals("test_config"))
        assert(eventLogInput.events[1].eventMetadata!!["ruleID"].equals("default"))
    }

    @Test
    fun testExperiment() = runBlocking {
        driver.initialize()
        var config = driver.getExperiment(statsigUser, "sample_experiment")
        assert(config.getString("experiment_param", "") == "test")
        config = driver.getExperiment(randomUser, "sample_experiment")
        assert(config.getString("experiment_param", "") == "control")
        driver.shutdown()

        val eventLogInput = withTimeout(TEST_TIMEOUT) {
            eventLogInputCompletable.await()
        }
        assert(eventLogInput.events.size == 2)
        assert(eventLogInput.events[0].eventName == "statsig::config_exposure")
        assert(eventLogInput.events[0].eventMetadata!!["config"].equals("sample_experiment"))
        assert(eventLogInput.events[0].eventMetadata!!["ruleID"].equals("2RamGujUou6h2bVNQWhtNZ"))

        assert(eventLogInput.events[1].eventName == "statsig::config_exposure")
        assert(eventLogInput.events[1].eventMetadata!!["config"].equals("sample_experiment"))
        assert(eventLogInput.events[1].eventMetadata!!["ruleID"].equals("2RamGsERWbWMIMnSfOlQuX"))
    }

    @Test
    fun testLogEvent() = runBlocking {
        driver.initialize()
        driver.logEvent(statsigUser, "purchase", 2.99, mapOf("item_name" to "remove_ads"))
        driver.shutdown()

        val eventLogInput = withTimeout(TEST_TIMEOUT) {
            eventLogInputCompletable.await()
        }

        assert(eventLogInput.events.size == 1)
        assert(eventLogInput.events[0].eventName == "purchase")
        assert(eventLogInput.events[0].eventValue == 2.99)
        assert(eventLogInput.events[0].eventMetadata!!["item_name"].equals("remove_ads"))
    }

    @Test
    fun testBackgroundSync() = runBlocking {
        download_config_count = 0
        download_id_list_count = 0

        CONFIG_SYNC_INTERVAL_MS = 1000
        ID_LISTS_SYNC_INTERVAL_MS = 1000

        driver.initialize()
        val privateEvaluatorField = driver.javaClass.getDeclaredField("configEvaluator")
        privateEvaluatorField.isAccessible = true

        val evaluator = privateEvaluatorField[driver] as Evaluator

        assert(download_config_count == 1)
        assert(download_id_list_count == 2)

        assert(evaluator.idLists["list_1"]?.ids == mapOf("1" to true, "2" to true))
        assert(evaluator.idLists["list_2"]?.ids == mapOf("a" to true, "b" to true))

        Thread.sleep(1100)
        assert(download_config_count == 2)
        assert(download_id_list_count == 4)
        assert(evaluator.idLists["list_1"]?.ids == mapOf("2" to true, "3" to true, "4" to true))
        assert(evaluator.idLists["list_2"]?.ids == mapOf("c" to true, "d" to true))

        Thread.sleep(1100)
        assert(download_config_count == 3)
        assert(download_id_list_count == 6)

        driver.shutdown()

        Thread.sleep(2000)
        assert(download_config_count == 3)
        assert(download_id_list_count == 6)
    }
}
