package RestAPITesting;

import com.google.gson.Gson;
public class CovertJSON {
    public static MobilePhone getMobile(MobilePhone mobile) {
        mobile.setBrand("SAMSUNG");
        mobile.setName("J2 Core");
        mobile.setRam(2);
        mobile.setRom(4);
        return mobile;
    }
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        MobilePhone mobilePhone = new MobilePhone();
        mobilePhone = getMobile(mobilePhone);
        System.out.println("The JSON representation of Object mobilePhone is ");
        System.out.println(new Gson().toJson(mobilePhone));
    }


}
