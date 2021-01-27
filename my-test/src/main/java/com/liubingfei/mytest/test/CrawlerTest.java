package com.liubingfei.mytest.test;

import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.apache.commons.lang.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

import java.io.*;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @Author: LiuBingFei
 * @Date: 2021-01-21 16:52
 * @Description:
 */
public class CrawlerTest {
    @Test
    public void crawlAreaInfo() {
        //保存结果：<区域id，区域名称>
        Map<String, String> areaMap = new HashMap<>();
        //国家统计局，全国行政区划12位网址，省列表
        String baseUrl = "http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2020/";
        //遍历省，并获取市网页链接
        Map<String, String> provinceAreaMap = new HashMap<>();
        Map<String, String> cityUrlMap = new HashMap<>();
        setDataOfProvince(provinceAreaMap, cityUrlMap, baseUrl);
        writeToFile(provinceAreaMap, "D:\\行政区划代码-省.txt");
        //遍历市，并获取区（县）网页链接
        Map<String, String> cityAreaMap = new HashMap<>();
        Map<String, String> countryUrlMap = new HashMap<>();
        doLoopSetDataOfCity(cityAreaMap, cityUrlMap, countryUrlMap, baseUrl);
        writeToFile(cityAreaMap, "D:\\行政区划代码-市.txt");
        //遍历区（县），并获取各街道（镇）网页链接
        Map<String, String> countryAreaMap = new HashMap<>();
        Map<String, String> townUrlMap = new HashMap<>();
        doLoopSetDataOfTown(countryAreaMap, countryUrlMap, townUrlMap, baseUrl);
        writeToFile(countryAreaMap, "D:\\行政区划代码-县.txt");
        //遍历街道、镇
        Map<String, String> villageAreaMap = new HashMap<>();
        Map<String, String> villageUrlMap = new HashMap<>();
        doLoopSetDataOfVillage(villageAreaMap, countryUrlMap, villageUrlMap, baseUrl);
        writeToFile(villageAreaMap, "D:\\行政区划代码-镇.txt");
        //遍历所有居委会
        Map<String, String> neighborhoodAreaMap = new HashMap<>();
        setDataOfNeighborhood(neighborhoodAreaMap, villageUrlMap);
        writeToFile(neighborhoodAreaMap, "D:\\行政区划代码-居委会.txt");
        //写入文件
        areaMap.putAll(provinceAreaMap);
        areaMap.putAll(cityAreaMap);
        areaMap.putAll(countryAreaMap);
        areaMap.putAll(villageAreaMap);
        areaMap.putAll(neighborhoodAreaMap);
        writeToFile(areaMap, "D:\\行政区划代码.txt");
    }

    public void writeToFile(Map<String, String> areaMap, String filePath){
        Map<String, String> map = areaMap.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (oleValue, newValue) -> oleValue, LinkedHashMap::new));
        try{
            File file = new File(filePath);
            if(!file.exists()){
                file.createNewFile();
            }
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream, "UTF-8");
            PrintWriter printWriter = new PrintWriter(outputStreamWriter, true);
            map.forEach((key, value) -> printWriter.println(key + "," + value));
            printWriter.close();
        }catch (IOException ioe){
            ioe.printStackTrace();
        }finally {

        }
    }

    public void setDataOfProvince(Map<String, String> areaMap, Map<String, String> cityUrlMap, String baseUrl){
        HtmlPage htmlPage = loopReadHtmlPage(new WebClient(), baseUrl + "index.html", 1);
        Document document = Jsoup.parse(htmlPage.asXml());
        Elements elements = document.getElementsByClass("provincetr");
        //遍历省，并获取各市的url链接
        for (Element element : elements) {
            Elements tds = element.getElementsByTag("td");
            String provinceId = null;
            String provinceName = null;
            String provinceUrl = null;
            for(Element ele : tds){
                Elements eleas = ele.getElementsByTag("a");
                for(Element area : eleas){
                    provinceName = area.text();
                    String href = area.attr("href");
                    provinceId = href.replace(".html", "") + "0000000000";
                    provinceUrl = baseUrl + href;
                }
                //根据公司入库规则，直辖市和自治区等，名称做修改。
                provinceName = provinceName
                        .replace("市", "")
                        .replace("自治区", "")
                        .replace("壮族", "")
                        .replace("回族", "")
                        .replace("维吾尔", "");
                areaMap.put(provinceId, provinceName);
                cityUrlMap.put(provinceName, provinceUrl);
            }

        }
    }

    public void doLoopSetDataOfCity(Map<String, String> areaMap, Map<String, String> cityUrlMap, Map<String, String> countryUrlMap, String baseUrl){
        Map<String, String> cityAreaMap = new HashMap<>();
        Map<String, String> countryUrlAreaMap = new HashMap<>();
        if(countryUrlMap.isEmpty()){
            setDataOfCity(cityAreaMap, cityUrlMap, countryUrlAreaMap, baseUrl);
        }
        countryUrlAreaMap.forEach((key, value) ->{
            if(StringUtils.isBlank(key) || StringUtils.isBlank(value)){
                doLoopSetDataOfCity(areaMap, cityUrlMap, countryUrlMap, baseUrl);
            }
        });
        areaMap.putAll(cityAreaMap);
        countryUrlMap.putAll(countryUrlAreaMap);
    }

    public void setDataOfCity(Map<String, String> areaMap, Map<String, String> cityUrlMap, Map<String, String> countryUrlMap, String baseUrl){
        //遍历市，并获取各区（县）网页链接
        cityUrlMap.forEach((provinceName, provinceUrl) ->{
            HtmlPage cityPage = loopReadHtmlPage(new WebClient(), provinceUrl, 1);
            Document cityDocument = Jsoup.parse(cityPage.asXml());
            Elements cityElements = cityDocument.getElementsByClass("citytr");
            for(Element cityElement : cityElements){
                Elements cityTds = cityElement.getElementsByTag("td");
                String cityId = null;
                String cityName = null;
                String cityUrl = null;
                for(int i = 0; i < cityTds.size(); i ++){
                    Elements cityAs = cityTds.get(i).getElementsByTag("a");
                    for(Element cityArea : cityAs){
                        cityUrl = baseUrl + cityArea.attr("href");
                        if(i == 0){
                            cityId = cityArea.text();
                        }
                        if(i == 1){
                            cityName = cityArea.text();
                        }
                    }
                    if("市辖区".equals(cityName)){
                        cityName = provinceName + "市";
                    }
                }
                areaMap.put(cityId, cityName);
                countryUrlMap.put(cityName, cityUrl);
            }
        });
    }

    public void doLoopSetDataOfTown(Map<String, String> areaMap, Map<String, String> countryUrlMap, Map<String, String> townUrlMap, String baseUrl){
        Map<String, String> townAreaMap = new HashMap<>();
        Map<String, String> townUrlAreaMap = new HashMap<>();
        if(townUrlMap.isEmpty()){
            setDataOfTown(townAreaMap, countryUrlMap, townUrlAreaMap, baseUrl);
        }
        townUrlAreaMap.forEach((key, value) ->{
            if(StringUtils.isBlank(key) || StringUtils.isBlank(value)){
                doLoopSetDataOfTown(areaMap, countryUrlMap, townUrlMap, baseUrl);
            }
        });
        areaMap.putAll(townAreaMap);
        townUrlMap.putAll(townUrlAreaMap);
    }

    public void setDataOfTown(Map<String, String> areaMap, Map<String, String> countryUrlMap, Map<String, String> townUrlMap, String baseUrl){
        countryUrlMap.forEach((cityName, cityUrl) ->{
            HtmlPage countryPage = loopReadHtmlPage(new WebClient(), cityUrl, 1);
            Document countryDocument = Jsoup.parse(countryPage.asXml());
            Elements countryElements = countryDocument.getElementsByClass("countytr");
            for(int i = 0; i < countryElements.size(); i++){
                Elements countryTds = countryElements.get(i).getElementsByTag("td");
                String countryId = null;
                String countryName = null;
                String countryUrl = null;
                if(i == 0){
                    countryId = countryTds.get(0).text();
                    countryName = cityName + countryTds.get(1).text();
                    areaMap.put(countryId, countryName);
                    continue;
                }
                for(int j = 0; j < countryTds.size(); j ++){
                    Elements countryAs = countryTds.get(j).getElementsByTag("a");
                    for(Element countryArea : countryAs){
                        countryUrl = baseUrl + countryArea.attr("href");
                        if(j == 0){
                            countryId = countryArea.text();
                        }
                        if(j == 1){
                            countryName = countryArea.text();
                        }
                    }
                }
                if(StringUtils.isBlank(countryId) || StringUtils.isBlank(countryName)){
                    countryId = countryTds.get(0).text();
                    countryName = countryTds.get(1).text();
                    areaMap.put(countryId, countryName);
                    continue;
                }
                areaMap.put(countryId, countryName);
                townUrlMap.put(countryName, countryUrl);
            }

        });
    }

    public void doLoopSetDataOfVillage(Map<String, String> areaMap, Map<String, String> townUrlMap, Map<String, String> villageUrlMap, String baseUrl){
        Map<String, String> villageAreaMap = new HashMap<>();
        Map<String, String> villageUrlAreaMap = new HashMap<>();
        if(villageUrlMap.isEmpty()){
            setDataOfVillage(villageAreaMap, townUrlMap, villageUrlAreaMap, baseUrl);
        }
        villageUrlAreaMap.forEach((key, value) ->{
            if(StringUtils.isBlank(key) || StringUtils.isBlank(value)){
                doLoopSetDataOfVillage(areaMap, townUrlMap, villageUrlMap, baseUrl);
            }
        });
        areaMap.putAll(villageAreaMap);
        villageUrlMap.putAll(villageUrlAreaMap);
    }

    public void setDataOfVillage(Map<String, String> areaMap, Map<String, String> townUrlMap, Map<String, String> villageUrlMap, String baseUrl){
        townUrlMap.forEach((countryName, countryUrl) ->{
            HtmlPage villagePage = loopReadHtmlPage(new WebClient(), countryUrl, 1);
            Document villageDocument = Jsoup.parse(villagePage.asXml());
            Elements villageElements = villageDocument.getElementsByClass("towntr");
            for(int i = 0; i < villageElements.size(); i++){
                Elements villageTds = villageElements.get(i).getElementsByTag("td");
                String villageId = null;
                String villageName = null;
                String villageUrl = null;
                for(int j = 0; j < villageTds.size(); j ++){
                    Elements villageAs = villageTds.get(j).getElementsByTag("a");
                    for(Element villageArea : villageAs){
                        villageUrl = baseUrl + villageArea.attr("href");
                        if(j == 0){
                            villageId = villageArea.text();
                        }
                        if(j == 1){
                            villageName = villageArea.text();
                        }
                    }
                }
                if(StringUtils.isBlank(villageId) || StringUtils.isBlank(villageName)){
                    villageId = villageTds.get(0).text();
                    villageName = villageTds.get(1).text();
                    areaMap.put(villageId, villageName);
                    continue;
                }
                areaMap.put(villageId, villageName);
                villageUrlMap.put(villageName, villageUrl);
            }
        });
    }

    public void setDataOfNeighborhood(Map<String, String> areaMap, Map<String, String> villageUrlMap){
        villageUrlMap.forEach((villageName, villageUrl) ->{
            HtmlPage neighborhoodPage = loopReadHtmlPage(new WebClient(), villageUrl, 1);
            Document neighborhoodDocument = Jsoup.parse(neighborhoodPage.asXml());
            Elements neighborhoodElements = neighborhoodDocument.getElementsByClass("villagetr");
            for(int i = 0; i < neighborhoodElements.size(); i++){
                Elements neighborhoodTds = neighborhoodElements.get(i).getElementsByTag("td");
                String neighborhoodId = null;
                String neighborhoodName = null;
                for(int j = 0; j < neighborhoodTds.size(); j ++){
                    if(j == 0){
                        neighborhoodId = neighborhoodTds.get(j).text();
                    }
                    if(j == 2){
                        neighborhoodName = neighborhoodTds.get(j).text();
                    }
                }
                areaMap.put(neighborhoodId, neighborhoodName);
            }
        });
    }

    public HtmlPage loopReadHtmlPage(WebClient webClient, String url, int count){
        if(count == 4){
            int readCount = count -1;
            System.out.println("连续"+readCount+"次访问失败，不再访问"+url);
            return null;
        }
        HtmlPage page = null;
        page = getHtmlPageResponse(webClient, url);

        if(Objects.isNull(page)){
            count++;
            page = loopReadHtmlPage(webClient, url, count);
        }
        return page;
    }

    /**
     * 读取网页内容
     * @param webClient
     * @param url
     * @return
     * @throws Exception
     */
    public HtmlPage getHtmlPageResponse(WebClient webClient, String url){
        webClient.getOptions().setThrowExceptionOnScriptError(false);//当JS执行出错的时候是否抛出异常
        webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);//当HTTP的状态非200时是否抛出异常
        webClient.getOptions().setActiveXNative(false);
        //是否启用CSS
        webClient.getOptions().setCssEnabled(true);
        //启用JS
        webClient.getOptions().setJavaScriptEnabled(true);
        webClient.getOptions().setRedirectEnabled(true);
        //设置支持AJAX
        webClient.setAjaxController(new NicelyResynchronizingAjaxController());
        //支持cookie
        webClient.getCookieManager().setCookiesEnabled(true);
        //浏览器超时时间
        webClient.getOptions().setTimeout(6000);
        //js超时时间
        webClient.setJavaScriptTimeout(3000);

        HtmlPage page = null;
        try {
            page = webClient.getPage(url);
        } catch (Exception e) {
            webClient.close();
            e.printStackTrace();
        }
        //等待js执行完成
        webClient.waitForBackgroundJavaScript(3000);
        return page;
    }
}
