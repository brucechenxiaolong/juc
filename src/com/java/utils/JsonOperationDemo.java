package com.java.utils;


import org.json.JSONArray;
import org.json.JSONObject;

import java.util.concurrent.*;

/**
 *
 * JSON数据的格式，大家有没有发现，其实JSON数据就是一个map，为什么JSON可以用来不同语言的通信呢?是因为在不同的语言当中，都可以将JSON数据转换成JSON对象
 *
 */
public class JsonOperationDemo {

    public static ExecutorService fixedThreadPool = Executors.newFixedThreadPool(5);

    public static ExecutorService threadPool = new ThreadPoolExecutor(2,
            5,
            2L, TimeUnit.SECONDS,
            new LinkedBlockingQueue<>(3),
            Executors.defaultThreadFactory(),
            new ThreadPoolExecutor.AbortPolicy());


    public static void main(String[] args) throws Exception{

        String jsonStr = "{\n" +
                "  \"result\": \"success\",\n" +
                "  \"msg\": \"查询值长日志成功!\",\n" +
                "  \"dataArry\": [\n" +
                "    {\n" +
                "      \"dataDate\": \"2021-05-23\",\n" +
                "      \"shiftName\": \"终班\",\n" +
                "      \"dutyName\": \"集控C值\",\n" +
                "      \"usrNam\": \"周宏伟\",\n" +
                "      \"dataSta\": \"审核完毕\",\n" +
                "      \"logArry\": [\n" +
                "        {\n" +
                "          \"logName\": \"日常记事\",\n" +
                "          \"detailArry\": [\n" +
                "            {\n" +
                "              \"logDateTime\": \"2021-05-23 16:00:57\",\n" +
                "              \"logDesc\": \"1、全厂存煤163417吨，#1机组负荷480MW，#2机组负荷480MW，调度通讯正常。\\n2、保持良好的精神状态，按要求佩戴防护用具。\"\n" +
                "            },\n" +
                "            {\n" +
                "              \"logDateTime\": \"2021-05-23 18:10:23\",\n" +
                "              \"logDesc\": \"本班加仓方式：\\n#1锅炉：1A//1B/1D/1E仓加12航次，1C仓加10航次；\\n#2锅炉：2A/2B/2D/2E仓加12航次，2F仓加10航次。\"\n" +
                "            },\n" +
                "            {\n" +
                "              \"logDateTime\": \"2021-05-23 18:45:49\",\n" +
                "              \"logDesc\": \"#1发电机漏氢试验结束，氢压：0.397MPa，热氢温度A：52.91℃ B：53.79℃ C：54.52℃ D：51.81℃，冷氢温度：A：40.04℃ B：40.43 ℃ C：40.74℃ D：41.06℃，漏氢量：10.09立方米/天，投入#1机氢气纯度仪。\"\n" +
                "            },\n" +
                "            {\n" +
                "              \"logDateTime\": \"2021-05-23 19:06:15\",\n" +
                "              \"logDesc\": \"配合电气专业测量#1机1A密封风机电机绝缘，相对地绝缘在39MΩ、吸收比1.03，电气专业回复待明日开票处理，现暂且保持备用，将情况告知部门电气主管蔡工。\"\n" +
                "            },\n" +
                "            {\n" +
                "              \"logDateTime\": \"2021-05-23 19:45:41\",\n" +
                "              \"logDesc\": \"二期值长联系#3机晚高峰后调停，需暂停止给我厂补汽，待#3机停机后恢复供汽，汇报部门吴主任同意后；关闭低压备用汽源减温水手动门及低压备用汽源供汽电动门，开启低压备用汽源供汽电动门旁路一、二次手动门，并开启低压备用汽源管道延程疏水手动门。现一、二期辅汽联络管靠一期侧电动门关小至5%，保持一、二期辅汽联络管暖管状态。\"\n" +
                "            },\n" +
                "            {\n" +
                "              \"logDateTime\": \"2021-05-23 21:36:30\",\n" +
                "              \"logDesc\": \"本班执行#2机组定期工作：#2发电机漏氢试验，结束时间5月23日18:31分，漏氢量11.02立方米/天，详见MIS汽机台账，投入#2机氢气纯度仪。\"\n" +
                "            }\n" +
                "          ]\n" +
                "        },\n" +
                "        {\n" +
                "          \"logName\": \"缺陷跟踪\",\n" +
                "          \"detailArry\": [\n" +
                "            {\n" +
                "              \"logDateTime\": \"2021-05-23 16:39:21\",\n" +
                "              \"logDesc\": \"电气专业告脱硫废水间#2石灰乳计量泵已更换变频器，恢复安措后启动#2石灰乳计量泵正常，终结《380V 脱硫废水间#2石灰乳计量泵变频器检查》工作票。\"\n" +
                "            },\n" +
                "            {\n" +
                "              \"logDateTime\": \"2021-05-23 19:55:05\",\n" +
                "              \"logDesc\": \"电气专业押回工作票《85kV #2炉电除尘2A左3高频柜检查》至#04柜，告已更换85kV #2炉电除尘2A左3高频柜的油枕，恢复安措后，投入#2炉电除尘2A左3高频开关，本班观察该油枕暂无漏油现象，烦请当班值关注。\"\n" +
                "            }\n" +
                "          ]\n" +
                "        }\n" +
                "      ]\n" +
                "    },\n" +
                "    {\n" +
                "      \"dataDate\": \"2021-05-23\",\n" +
                "      \"shiftName\": \"夜班\",\n" +
                "      \"dutyName\": \"集控D值\",\n" +
                "      \"usrNam\": \"李智伟\",\n" +
                "      \"dataSta\": \"审核完毕\",\n" +
                "      \"logArry\": [\n" +
                "        {\n" +
                "          \"logName\": \"日常记事\",\n" +
                "          \"detailArry\": [\n" +
                "            {\n" +
                "              \"logDateTime\": \"2021-05-23 00:00:00\",\n" +
                "              \"logDesc\": \"1、全厂存煤163417吨，#1机组负荷500MW，#2机组负荷490MW，调度通讯正常。\\n2、保持良好的精神状态，按要求佩戴防护用具。\"\n" +
                "            },\n" +
                "            {\n" +
                "              \"logDateTime\": \"2021-05-23 01:29:08\",\n" +
                "              \"logDesc\": \"本班加仓方式：\\n#1锅炉：1A//1B/1D/1E仓加12航次，其余仓加10航次；\\n#2锅炉：2A/2B/2D/2E仓加12航次，其余仓加10航次。\"\n" +
                "            }\n" +
                "          ]\n" +
                "        }\n" +
                "      ]\n" +
                "    },\n" +
                "    {\n" +
                "      \"dataDate\": \"2021-05-23\",\n" +
                "      \"shiftName\": \"白班\",\n" +
                "      \"dutyName\": \"集控E值\",\n" +
                "      \"usrNam\": \"叶培玮\",\n" +
                "      \"dataSta\": \"审核完毕\",\n" +
                "      \"logArry\": [\n" +
                "        {\n" +
                "          \"logName\": \"日常记事\",\n" +
                "          \"detailArry\": [\n" +
                "            {\n" +
                "              \"logDateTime\": \"2021-05-23 08:00:00\",\n" +
                "              \"logDesc\": \"1、全厂存煤163417吨，#1机组负荷490MW，#2机组负荷490MW，调度通讯正常。\\n2、保持良好的精神状态，按要求佩戴防护用具。\"\n" +
                "            },\n" +
                "            {\n" +
                "              \"logDateTime\": \"2021-05-23 09:56:53\",\n" +
                "              \"logDesc\": \"本班加仓方式：\\n#1锅炉：1A//1B/1D/1E仓加12航次，1C仓加10航次；\\n#2锅炉：2A/2B/2D/2E仓加12航次，2F仓加10航次。\"\n" +
                "            },\n" +
                "            {\n" +
                "              \"logDateTime\": \"2021-05-23 10:00:26\",\n" +
                "              \"logDesc\": \"黄昇监盘发现#1炉电除尘输灰二电场C127仓泵圆顶阀无法关闭，就地检查仓泵圆顶阀汽源管脱落。登缺后电话通知热控专业检查处理，热控专业就地更换脱落汽源管，试开关该圆顶阀，正常。\"\n" +
                "            },\n" +
                "            {\n" +
                "              \"logDateTime\": \"2021-05-23 10:42:21\",\n" +
                "              \"logDesc\": \"李进巡检发现#2炉电除尘高频电源2A左3有渗油，停止该高频电源运行，登记缺陷并通知电气专业检查处理。电气专业检查后回复可能是油枕渗油，需开票检查处理。\"\n" +
                "            }\n" +
                "          ]\n" +
                "        },\n" +
                "        {\n" +
                "          \"logName\": \"定期工作\",\n" +
                "          \"detailArry\": [\n" +
                "            {\n" +
                "              \"logDateTime\": \"2021-05-23 15:30:26\",\n" +
                "              \"logDesc\": \"#1机组定期工作：\\n1、 #1锅炉结焦检查：本班无大渣块掉落；\\n2、 #1锅炉1A一次风机液压油泵切换：由B切至A，正常；\\n3、 #1锅炉1B一次风机液压油泵切换：由B切至A，正常；\\n4、 #1锅炉1A一次风机润滑油泵切换：由B切至A，正常；\\n5、 #1锅炉1B一次风机润滑油泵切换：由B切至A，正常；\\n6、 #1锅炉1A送风机润滑油泵切换：由B切至A，正常；\\n7、 #1锅炉1B送风机润滑油泵切换：由B切至A，正常；\\n8、 #1锅炉石膏排浆泵切换：已执行；\\n9、 #1锅炉电除尘整流器检查：已执行，正常；\\n10、#1机组缺陷验收：已验收。\"\n" +
                "            },\n" +
                "            {\n" +
                "              \"logDateTime\": \"2021-05-23 15:32:04\",\n" +
                "              \"logDesc\": \"#2机组定期工作：\\n1、#2锅炉结焦检查，本班观察无大渣块掉落；\\n2、2A一次风机润滑油泵切换，A切B，正常；\\n3、2B一次风机润滑油泵切换：B切A，正常；\\n4、2A一次风机液压油泵切换：A切B，正常；\\n5、2B一次风机液压油泵切换：B切A，正常；\\n6、2A送风机液压油泵切换：A切B，正常；\\n7、2B送风机液压油泵切换：A切B，正常。\"\n" +
                "            }\n" +
                "          ]\n" +
                "        }\n" +
                "      ]\n" +
                "    }\n" +
                "  ]\n" +
                "}";

        threadPool.execute(() ->{

            try {
//                TimeUnit.SECONDS.sleep(10);

                System.out.println("11111当前正在执行的线程是：" + Thread.currentThread().getName());
                JSONObject jsonObject = new JSONObject(jsonStr);
                String msg = jsonObject.getString("msg");//获取json中某个key的值
                System.out.println(msg);

                JSONArray dataArry = jsonObject.getJSONArray("dataArry");//获取json内list数据
                for (int i = 0; i < dataArry.length(); i++) {
                    JSONObject dataArryDetail = (JSONObject) dataArry.get(i);
                    String dutyName = dataArryDetail.getString("dutyName");
                    System.out.println("dutyName=" + dutyName);

                    JSONArray logArry = dataArryDetail.getJSONArray("logArry");//获取json内list数据
                    for (int j = 0; j < logArry.length(); j++) {
                        JSONObject logArrayDetail = (JSONObject) logArry.get(j);
                        String logName = logArrayDetail.getString("logName");
                        System.out.println("logName=" + logName);

                        JSONArray detailArry = logArrayDetail.getJSONArray("detailArry");//获取json内list数据
                        for (int k = 0; k < detailArry.length(); k++) {
                            JSONObject detailArryDetail = (JSONObject) detailArry.get(k);
                            String logDateTime = detailArryDetail.getString("logDateTime");
                            System.out.println("logDateTime=" + logDateTime);

                        }



                    }

                }
            }catch (Exception e){
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + "我终于执行完了。。。");
        });


        //操作多线程：线程执行
        /*threadPool.execute(() ->{
            try {
                TimeUnit.SECONDS.sleep(3);

                System.out.println("2222当前正在执行的线程是：" + Thread.currentThread().getName());
                JSONObject jsonObject = new JSONObject(jsonStr);
                String msg = jsonObject.getString("msg");//获取json中某个key的值
                System.out.println(msg);

                JSONArray array = jsonObject.getJSONArray("dataArry");//获取json内list数据
                for (int i = 0; i < array.length(); i++) {
                    JSONObject childJson = (JSONObject) array.get(i);
                    String dutyName = childJson.getString("dutyName");
                    System.out.println(dutyName);
                }
            }catch (Exception e){
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + "我终于执行完了。。。");
        });

        System.out.println(Thread.currentThread().getName() + "我是主线程！先执行我！");

        fixedThreadPool.execute(() ->{
            try {
                TimeUnit.SECONDS.sleep(20);
                System.out.println("3333当前正在执行的线程是：" + Thread.currentThread().getName());
            }catch (Exception e){
                e.printStackTrace();
            }
        });*/
    }
}
