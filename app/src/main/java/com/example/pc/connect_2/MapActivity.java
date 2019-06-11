package com.example.pc.connect_2;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.AMapOptions;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.LocationSource;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.UiSettings;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.CameraPosition;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.Marker;
import com.amap.api.maps2d.model.MarkerOptions;
import com.amap.api.maps2d.model.MyLocationStyle;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/*
地图模块
 */

public class MapActivity extends BaseActivity implements LocationSource,
        AMapLocationListener, PoiSearch.OnPoiSearchListener, AMap.OnCameraChangeListener {


    private MapView mMapView;


    //标识，用于判断是否只显示一次定位信息和用户重新定位
    private boolean isFirstLoc = true;

    //声明AMapLocationClient类对象
    public AMapLocationClient mLocationClient = null;

    //声明AMapLocationClientOption对象
    public AMapLocationClientOption mLocationOption = null;

    //经纬度 默认为天安门39.9088691069,116.3973823161
    private double lat = 39.9088691069;
    private double lon = 116.3973823161;

    private AMap aMap;

    //地图放大级别
    private float zoomlevel = 17f;

    //声明mListener对象，定位监听器
    private OnLocationChangedListener mListener = null;
    private MapView map;
    private android.widget.TextView tvLocation;
    private PoiSearch.Query query;
    private Button search;
    private TextView locationMessage;
    private PoiSearch poiSearch;
    private String selectedAdress;
    private LatLng target;

    public String getSelectedAdress(){

        return this.selectedAdress;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        this.tvLocation = (TextView) findViewById(R.id.tvLocation);
        this.map = (MapView) findViewById(R.id.map);

        mMapView = (MapView) findViewById(R.id.map);

        //自定义的回到当前位置的事件
        tvLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startLocation();
            }
        });

        //在activity执行onCreate时执行mMapView.onCreate(savedInstanceState)，实现地图生命周期管理
        mMapView.onCreate(savedInstanceState);

        if (aMap == null) {
            aMap =  mMapView.getMap();
            //设置显示定位按钮 并且可以点击
            UiSettings settings =  aMap.getUiSettings();
            aMap.setLocationSource(this);//设置了定位的监听,这里要实现LocationSource接口

            // 是否显示定位按钮
            settings.setMyLocationButtonEnabled(false);
            //添加指南针
            settings.setCompassEnabled(true);

//            aMap.getCameraPosition(); 方法可以获取地图的旋转角度


            //管理缩放控件
            settings.setZoomControlsEnabled(true);
            //设置logo位置，左下，底部居中，右下
            settings.setLogoPosition(AMapOptions.LOGO_POSITION_BOTTOM_LEFT);
            //设置显示地图的默认比例尺
            settings.setScaleControlsEnabled(true);
            //每像素代表几米
            float scale = aMap.getScalePerPixel();

            aMap.setMyLocationEnabled(true);//显示定位层并且可以触发定位,默认是flase

        }

        //开始定位
        location();

        MyLocationStyle myLocationStyle = new MyLocationStyle();//初始化定位蓝点样式类
        myLocationStyle.myLocationIcon(BitmapDescriptorFactory
                .fromResource(R.drawable.gps_point));// 设置小蓝点的图标
        myLocationStyle.strokeColor(Color.GRAY);
        int color = Color.argb(65, 0, 0, 0);
        myLocationStyle.radiusFillColor(color);
        myLocationStyle.interval(2000); //设置连续定位模式下的定位间隔，只在连续定位模式下生效，单次定位模式下不会生效。单位为毫秒。
        aMap.setMyLocationStyle(myLocationStyle);//设置定位蓝点的Style
        //aMap.getUiSettings().setMyLocationButtonEnabled(true);设置默认定位按钮是否显示，非必需设置。
        aMap.setMyLocationEnabled(true);// 设置为true表示启动显示定位蓝点，false表示隐藏定位蓝点并不进行定位，默认是false。




        search = (Button) findViewById(R.id.Search);
        locationMessage = (TextView) findViewById(R.id.LocationMessage);

        final Context context = this.getApplicationContext();
        final PoiSearch.OnPoiSearchListener listener = this;


        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                query = new PoiSearch.Query(locationMessage.getText().toString(), "", "南京");
//keyWord表示搜索字符串，
//第二个参数表示POI搜索类型，二者选填其一，选用POI搜索类型时建议填写类型代码，码表可以参考下方（而非文字）
//cityCode表示POI搜索区域，可以是城市编码也可以是城市名称，也可以传空字符串，空字符串代表全国在全国范围内进行搜索
                query.setPageSize(1000);// 设置每页最多返回多少条poiitem
                query.setPageNum(1);//设置查询页码
                poiSearch = new PoiSearch(context, query);
                poiSearch.setOnPoiSearchListener(listener);
                poiSearch.searchPOIAsyn();
            }
        });

        AMap.OnMarkerClickListener markerClickListener = new AMap.OnMarkerClickListener() {
            // marker 对象被点击时回调的接口
            // 返回 true 则表示接口已响应事件，否则返回false
            @Override
            public boolean onMarkerClick(Marker marker) {
//                moveToTar(target, marker.getPosition(), 11f);
                selectedAdress = marker.getSnippet().toString();
                Toast.makeText(getApplicationContext(), "当前选定的位置为: " + selectedAdress, Toast.LENGTH_LONG).show();
                Intent uid = getIntent();
                String str = uid.getStringExtra("user");
                Intent intent = new Intent(MapActivity.this,TradeActivity.class);
                intent.putExtra("address",selectedAdress);
                intent.putExtra("user",str);
                startActivity(intent);
                return false;
            }
        };
// 绑定 Marker 被点击事件
        aMap.setOnMarkerClickListener(markerClickListener);

    }

    private void location() {
        //初始化定位
        mLocationClient = new AMapLocationClient(getApplicationContext());
        //设置定位回调监听
        mLocationClient.setLocationListener(this);
        //初始化定位参数
        mLocationOption = new AMapLocationClientOption();
        //设置定位模式为Hight_Accuracy高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //设置是否返回地址信息（默认返回地址信息）
        mLocationOption.setNeedAddress(true);
        //设置是否只定位一次,默认为false
        mLocationOption.setOnceLocation(false);
        //设置是否强制刷新WIFI，默认为强制刷新
        mLocationOption.setWifiActiveScan(true);
        //设置是否允许模拟位置,默认为false，不允许模拟位置
        mLocationOption.setMockEnable(true);
        //设置定位间隔,单位毫秒,默认为2000ms
        mLocationOption.setInterval(2000);
        //给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);
        //启动定位
        mLocationClient.startLocation();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mMapView.onDestroy();
        mLocationClient.stopLocation();//停止定位
        mLocationClient.onDestroy();//销毁定位客户端。
    }
    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView.onResume ()，实现地图生命周期管理
        mMapView.onResume();
    }
    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView.onPause ()，实现地图生命周期管理
        mMapView.onPause();
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，实现地图生命周期管理
        mMapView.onSaveInstanceState(outState);
    }

    /**
     * 定位成功后回调函数
     */
    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {

        if (aMapLocation != null) {
            if (aMapLocation.getErrorCode() == 0) {
                //可在其中解析amapLocation获取相应内容。

                aMapLocation.getLocationType();//获取当前定位结果来源，如网络定位结果，详见定位类型表
                lat = aMapLocation.getLatitude();//获取纬度
                lon = aMapLocation.getLongitude();//获取经度
                aMapLocation.getAccuracy();//获取精度信息
                aMapLocation.getAddress();//地址，如果option中设置isNeedAddress为false，则没有此结果，网络定位结果中会有地址信息，GPS定位不返回地址信息。
                aMapLocation.getCountry();//国家信息
                aMapLocation.getProvince();//省信息
                aMapLocation.getCity();//城市信息
                aMapLocation.getDistrict();//城区信息
                aMapLocation.getStreet();//街道信息
                aMapLocation.getStreetNum();//街道门牌号信息
                aMapLocation.getCityCode();//城市编码
                aMapLocation.getAdCode();//地区编码
                aMapLocation.getAoiName();//获取当前定位点的AOI信息
                //获取定位时间
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = new Date(aMapLocation.getTime());
                df.format(date);


                // 如果不设置标志位，此时再拖动地图时，它会不断将地图移动到当前的位置
                if (isFirstLoc) {

                    //设置缩放级别
                    aMap.moveCamera(CameraUpdateFactory.zoomTo(zoomlevel));
                    //将地图移动到定位点
                    aMap.moveCamera(CameraUpdateFactory.changeLatLng(new LatLng(aMapLocation.getLatitude(), aMapLocation.getLongitude())));
                    //点击定位按钮 能够将地图的中心移动到定位点
                    mListener.onLocationChanged(aMapLocation);
                    //获取定位信息
                    StringBuffer buffer = new StringBuffer();
                    buffer.append(aMapLocation.getCountry() + ""
                            + aMapLocation.getProvince() + ""
                            + aMapLocation.getCity() + ""
                            + aMapLocation.getProvince() + ""
                            + aMapLocation.getDistrict() + ""
                            + aMapLocation.getStreet() + ""
                            + aMapLocation.getStreetNum());
                    Toast.makeText(getApplicationContext(), buffer.toString(), Toast.LENGTH_LONG).show();
                    isFirstLoc = false;
                }

            }else {
                //定位失败时，可通过ErrCode（错误码）信息来确定失败的原因，errInfo是错误信息，详见错误码表。
                Log.e("地图错误","定位失败, 错误码:" + aMapLocation.getErrorCode() + ", 错误信息:"
                        + aMapLocation.getErrorInfo());
            }
        }

    }

    @Override
    public void activate(OnLocationChangedListener onLocationChangedListener) {
        mListener = onLocationChangedListener;
    }

    @Override
    public void deactivate() {
        mListener = null;
    }

    private void startLocation(){

        if(mLocationClient != null){
            //19f代表地图放大的级别
            aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat,lon),zoomlevel));
        }

    }

    String lastLocation = "";
    @Override
    public void onPoiSearched(PoiResult poiResult, int i) {

        //获取地图上所有Marker
        List<Marker> mapScreenMarkers = aMap.getMapScreenMarkers();
        for (int j = 1; j < mapScreenMarkers.size(); j++) {
            Marker marker = mapScreenMarkers.get(j);
            marker.remove();
        }
        lastLocation = locationMessage.getText().toString();
        aMap.invalidate();//刷新地图

        ArrayList<PoiItem> list = poiResult.getPois();
        float lat =  0;
        float lon = 0;

        for (int j = 0; j < list.size(); j++) {
            PoiItem poiItem = list.get(j);
            LatLonPoint point = poiItem.getLatLonPoint();
            MarkerOptions markerOption = new MarkerOptions();
            markerOption.position(new LatLng(point.getLatitude(), point.getLongitude()));
            markerOption.title("[" + lastLocation + "]");
            markerOption.snippet(poiItem.getCityName() + poiItem.getAdName() + poiItem.getSnippet());
//            markerOption.icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory
//                    .decodeResource(getResources(),R.drawable.r1)));
            markerOption.draggable(false);
            aMap.addMarker(markerOption);
            lat += point.getLatitude();
            lon += point.getLongitude();
        }

        lat /= list.size();
        lon /= list.size();

        aMap.moveCamera(CameraUpdateFactory.zoomTo(11f));
        aMap.moveCamera(CameraUpdateFactory.changeLatLng(new LatLng(lat, lon)));
    }

    @Override
    public void onPoiItemSearched(PoiItem poiItem, int i) {

    }

    @Override
    public void onCameraChange(CameraPosition position) {
        target = position.target;
    }

    @Override
    public void onCameraChangeFinish(CameraPosition cameraPosition) {

    }

    public void moveToTar(LatLng past, LatLng tar, float zoom) {
        double y1 = past.latitude;
        double y2 = tar.latitude;
        double x1 = past.longitude;
        double x2 = past.longitude;
        double k = (past.latitude - tar.latitude) / (past.longitude - past.longitude);

        if (x1 > x2 && y1 > y2) {
            while (x2 < x1) {
                x2 += 0.0001;
                y2 += 0.0001 * k;
                aMap.moveCamera(CameraUpdateFactory.zoomTo(zoom));
                aMap.moveCamera(CameraUpdateFactory.changeLatLng(new LatLng(y2, x2)));
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } else if (x1 <= x2 && y1 > y2){
            while (x2 < x1){
                x1 += 0.0001;
                y2 += 0.0001 * k;
                aMap.moveCamera(CameraUpdateFactory.zoomTo(zoom));
                aMap.moveCamera(CameraUpdateFactory.changeLatLng(new LatLng(y2, x1)));
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }else if (x1 <= x2 && y1 <= y2){
            while (x2 < x1){
                x1 += 0.0001;
                y1 += 0.0001 * k;
                aMap.moveCamera(CameraUpdateFactory.zoomTo(zoom));
                aMap.moveCamera(CameraUpdateFactory.changeLatLng(new LatLng(y1, x1)));
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }else if (x1 > x2 && y1 <= y2){
            while (x2 < x1){
                x2 += 0.0001;
                y1 += 0.0001 * k;
                aMap.moveCamera(CameraUpdateFactory.zoomTo(zoom));
                aMap.moveCamera(CameraUpdateFactory.changeLatLng(new LatLng(y1, x2)));
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
