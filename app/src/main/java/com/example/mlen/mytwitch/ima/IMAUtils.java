package com.example.mlen.mytwitch.ima;

import com.example.mlen.mytwitch.model.Stream;

import java.net.URLEncoder;

/**
 * Created by mlen on 2/19/17.
 */

public class IMAUtils {

    // To get cust params working here, I need to parcelize the model, so PlayerActivity can get all info
    public static String generateAdTag(String base, String channelName) {
//https://pubads.g.doubleclick.net/gampad/live/ads?iu=[iu]&ius_szs=300x250&sz=640x480&gdfp_req=1&env=vp&output=xml_vast3&url=[url]&correlator=[timestamp]&cust_params=[cust_params]&unviewed_position_start=1&eid=567890291&sdkv=h.3.158.1&sdki=3c0d&scor=1584445166772916&adk=2615417281&osd=2&frm=0&sdr=1&afvsz=200x200%2C250x250%2C300x250%2C336x280%2C450x50%2C468x60%2C480x70&ciu_szs=300x60%2C300x250&ged=ve4_td9_tt2_pd9_la9000_er238.80.394.380_vi0.0.926.735_vp100_eb24171
        StringBuilder builder = new StringBuilder(base);
        /**
         *
         iu = %2F3576121%2Ftwitch%2Fchannels%2Fcohhcarnage
         [url] = https%3A%2F%2Fwww.twitch.tv%2Fcohhcarnage
         [cust_params] = partner%3Dtrue%26game%3Dmetro%3A_last_light%26chan%3Dcohhcarnage%26embed%3Dfalse%26platform%3Dweb%26playerType%3Dsite%26mature%3Dfalse%26pos%3D1%26timebreak%3D30%26adblock%3Dfalse%26sdk%3Dhtml5%26kuid%3Drkengex45
          */
        try {
            replaceStringInBuilder(builder, "[iu]", URLEncoder.encode("/3576121/twitch/channels/" + channelName, "utf-8"));
            replaceStringInBuilder(builder, "[url]", URLEncoder.encode("https://www.twitch.tv/" + channelName, "utf-8"));
            replaceStringInBuilder(builder, "[cust_params]", "partner%3Dtrue%26game%3Dmetro%3A_last_light%26chan%3D" + channelName + "%26embed%3Dfalse%26platform%3Dapp%26playerType%3Dsite%26mature%3Dfalse%26pos%3D1%26timebreak%3D30%26adblock%3Dfalse%26sdk%3Dhtml5%26kuid%3Drkengex45");

        } catch (Exception e) {

        }

        return builder.toString();
    }

    private static void replaceStringInBuilder(StringBuilder builder, String src, String repl) {

        int replI = builder.indexOf(src);
        builder.replace(replI, replI + src.length(), repl);
    }
}
