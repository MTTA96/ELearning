package com.eways.elearning.Handler.Adapter;

/**
 * Created by yowin on 10/11/2017.
 */
<<<<<<< HEAD
//xtends BaseAdapter
public class KhoaHocDaHoanTatAdapter {

//    Context mContext;
//    int mLayout;
//    List<KhoaHocDaHoanTat> khoaHocDaHoanTatList;
//
//    public KhoaHocDaHoanTatAdapter(Context mContext, int mLayout, List<KhoaHocDaHoanTat> khoaHocDaHoanTatList) {
//        this.mContext = mContext;
//        this.mLayout = mLayout;
//        this.khoaHocDaHoanTatList = khoaHocDaHoanTatList;
//    }
//
//    @Override
//    public int getCount() {
//        return khoaHocDaHoanTatList.size();
//    }
//
//    @Override
//    public Object getItem(int position) {
//        return null;
//    }
//
//    @Override
//    public long getItemId(int position) {
//        return 0;
//    }
//
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
=======

public class KhoaHocDaHoanTatAdapter extends BaseAdapter {

    Context mContext;
    int mLayout;
    List<KhoaHocDaHoanTat> khoaHocDaHoanTatList;

    public KhoaHocDaHoanTatAdapter(Context mContext, int mLayout, List<KhoaHocDaHoanTat> khoaHocDaHoanTatList) {
        this.mContext = mContext;
        this.mLayout = mLayout;
        this.khoaHocDaHoanTatList = khoaHocDaHoanTatList;
    }

    @Override
    public int getCount() {
        return khoaHocDaHoanTatList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
>>>>>>> d05a5ab0b035229301d499e6d0b953eeb4ff8c9c
//        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        convertView = inflater.inflate(mLayout, null);
//
//        ImageView imvAvatar = (ImageView) convertView.findViewById(R.id.imvAvatar);
////        imvAvatar.setImageURI(url);
////        lấy UserID của người đăng khóa học => lấy link avatar trong tài khoản
//
//        RatingBar rbBaiDang = (RatingBar) convertView.findViewById(R.id.rbBaiDang);
////        rbBaiDang.setRating( );
////        lấy UserID của người đăng khóa học => lấy rating trong tài khoản
//
//        TextView tvTenNguoiDang = (TextView) convertView.findViewById(R.id.tvTenNguoiDang);
//        tvTenNguoiDang.setText(khoaHocDaHoanTatList.get(position).getNguoiDang());
//
//        TextView tvBuoiHoc = (TextView) convertView.findViewById(R.id.tvBuoiHoc);
//        tvTenNguoiDang.setText(khoaHocDaHoanTatList.get(position).getSoBuoiHoc());
//
//        TextView tvMonHoc = (TextView) convertView.findViewById(R.id.tvMonHoc);
//        tvTenNguoiDang.setText(Arrays.toString(khoaHocDaHoanTatList.get(position).getMon().toArray()));
////        non-recommended! Không chắc đúng hay không? Với lại hiện thị kiểu này hông đẹp
//
//        TextView tvXemThem = (TextView) convertView.findViewById(R.id.tvXemThem);
<<<<<<< HEAD
//
//        return convertView;
//    }
=======

        return convertView;
    }
>>>>>>> d05a5ab0b035229301d499e6d0b953eeb4ff8c9c
}
