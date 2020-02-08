package com.mattrubacky.monet2.backend.api.splatnet;


/**
 * Created by mattr on 12/6/2017.
 */

//public class CoopSchedulesRequest extends SplatnetRequest {
//
//    private Context context;
//    private SalmonSchedule salmonSchedule;
//    private boolean override;
//
//    public CoopSchedulesRequest(Context context,boolean override){
//        this.context = context;
//        this.override = override;
//    }
//
//    @Override
//    protected void manageResponse(Response response) {
//        salmonSchedule = (SalmonSchedule) response.body();
//        if(!override){
//            salmonSchedule.getTimes().remove(0);
//            salmonSchedule.times.remove(0);
//        }
//        SplatnetDatabase db = SplatnetDatabase.getInstance(context);
//        SalmonShiftDao shiftDao = db.getSalmonShiftDao();
//        SalmonStageDao stageDao = db.getSalmonStageDao();
//        SalmonWeaponDao salmonWeaponDao = db.getSalmonWeaponDao();
//        WeaponDao weaponDao = db.getWeaponDao();
//        for(SalmonRunDetail salmonRunDetail:salmonSchedule.details) {
//            shiftDao.insertSalmonShift(salmonRunDetail,stageDao,salmonWeaponDao,weaponDao);
//        }
//        for(SalmonRun salmonRun:salmonSchedule.times){
//            shiftDao.insertSalmonShift(salmonRun);
//        }
//        db.close();
//    }
//
//    @Override
//    public void setup(Splatnet splatnet, String cookie, String uniqueID) {
//        call = splatnet.getSalmonSchedule(cookie,uniqueID);
//    }
//
//
//    @Override
//    public boolean shouldUpdate(){
//        SplatnetDatabase db = SplatnetDatabase.getInstance(context);
//        int count = db.getSalmonShiftDao().countUpcoming(Calendar.getInstance().getTimeInMillis()/1000);
//        db.close();
//        return !(count==6);
//    }
//
//    @Override
//    public Bundle result(Bundle bundle) {
//        bundle.putParcelable("salmonSchedule",salmonSchedule);
//        return bundle;
//    }
//}
