/**
 * 
 */
package com.cc.util.code;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import android.annotation.SuppressLint;

/**
 * 
 *
 * @Desc: <p></p>
 */
public class StringUtils {

    /**
     * 
     * @param maptest
     * @return 对key键值按字典升序排序 并urlencode编码参数
     */
    public static String mSort(HashMap<String, String> maptest) {
        StringBuilder sb = new StringBuilder();
        Collection<String> keyset = maptest.keySet();
        List<String> list = new ArrayList<String>(keyset);
        if (list == null || list.isEmpty()) {
            return null;
        }
        // 对key键值按字典升序排序
        Collections.sort(list);

        for (int i = 0; i < list.size(); i++) {
            String urlen = maptest.get(list.get(i));
            try {
                urlen = URLEncoder.encode(maptest.get(list.get(i)), "utf8");
                sb.append(list.get(i));
                sb.append("=");
                sb.append(urlen);
                sb.append("&");
                // Log.i("wzh", "参数url加密=" + urlen);
            } catch (Exception e) {
                e.printStackTrace();
            }
         
        }
        if (sb.length() > 0) {
            sb.deleteCharAt(sb.length() - 1);
        }
        return sb.toString();
    }

	/**
	 * 四舍五入 保留两位小数
	 * @param ft
	 * @return
	 */
	@SuppressLint("DefaultLocale")
	public static String floatTo2(float ft) {
		return String.format("%.2f", ft);
	}
	@SuppressLint("DefaultLocale")
	public static String doubleTo2(double ft) {
		return String.format("%.2f", ft);
	}
}
