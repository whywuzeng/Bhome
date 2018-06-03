package com.system.bhouse.bhouse.phone.utlis;

import static com.system.bhouse.base.App.hasProjectCostPermission;
import static com.system.bhouse.base.App.hasProjectManagePermission;
import static com.system.bhouse.base.App.isNonsupportMap;
import static com.system.bhouse.base.App.isProjectCheckAuditOpen;
import static com.system.bhouse.base.App.isProjectCostStatisticsOpen;
import static com.system.bhouse.base.App.isProjectFillCheckAuditOpen;
import static com.system.bhouse.base.App.isProjectFillCheckOpen;
import static com.system.bhouse.base.App.isProjectWorkAuditOpen;
import static com.system.bhouse.base.App.isProjectWorkOpen;

public class FunctionConfigUtil {
    public static void funcConfig(long j) {

//        App.isAttendOpen = isOpen(j, 19);
//        App.isLeaveApplyOpen = isOpen(j, 0);
//        App.isLeaveAuditOpen = isOpen(j, 20);
//        App.isLeaveUnauditNotifyOpen = isOpen(j, 25);
//        App.isOverWorkApplyOpen = isOpen(j, 8);
//        App.isOverWorkAuditOpen = isOpen(j, 21);
//        App.isBusinessApplyOpen = isOpen(j, 9);
//        App.isBusinessAuditOpen = isOpen(j, 22);
//        App.isPatcheckApplyOpen = isOpen(j, 4);
//        App.isPatcheckAuditOpen = isOpen(j, 23);
//        App.isPatchClockApplyOpen = isOpen(j, 31);
//        App.isPatchClockAuditOpen = isOpen(j, 32);
//        CAMLog.e(PatcheckCon.TAG, "isPatchClockApplyOpen=" + App.isPatchClockApplyOpen);
//        CAMLog.e(PatcheckCon.TAG, "isPatchClockAuditOpen=" + App.isPatchClockAuditOpen);
//        App.isAttendAuditOpen = isOpen(j, 3);
//        App.isAttendCheckOpen = isOpen(j, 24);
//        App.isFaceOpen = isOpen(j, 16);
//        App.isProjectPickFaceOpen = isOpen(j, 38);
//        App.isScheduleOpen = isOpen(j, 46);
//        App.isMyScheduleOpen = isOpen(j, 47);
//        App.isChangeShiftOpen = isOpen(j, 47);
//        App.isChangeShiftAuditOpen = isOpen(j, 48);
//        App.isMeetingOpen = isOpen(j, 6);
//        App.isMissionOpen = isOpen(j, 5);
//        App.isWorklogApplyOpen = isOpen(j, 14);
//        App.isCloudOpen = isOpen(j, 15);
//        App.isWorkApplyOpen = isOpen(j, 13);
//        App.isWorkAuditOpen = isOpen(j, 26);



        hasProjectManagePermission = isOpen(j, 1);
        hasProjectCostPermission = isOpen(j, 2);
        isProjectWorkOpen = hasProjectCostPermission;
        isProjectWorkAuditOpen = hasProjectManagePermission;
        isProjectCostStatisticsOpen = isProjectCostStatisticsOpen(hasProjectManagePermission, hasProjectCostPermission);

//        App.isPhoneNOAuditOpen = isOpen(j, 29);

        isProjectFillCheckOpen = isOpen(j, 40);
        isProjectFillCheckAuditOpen = isOpen(j, 41);


//        App.isEvaStaffOpen = isOpen(j, 50);
//        App.isEvaStaffVisible = isOpen(j, 53);
//        App.isChatOpen = isOpen(j, 11);
//        App.isMatesLocationOpen = isOpen(j, 12);
//        App.isNoticeSendOpen = isOpen(j, 27);
//        App.isChatWaterMarkOpen = isOpen(j, 45);
//        App.isCustomerOpen = isOpen(j, 7);
//        App.isCustomerVisitOpen = isOpen(j, 7);
//        App.isCustomerVisitForcePhoto = isOpen(j, 17);
//        App.isCustomerVisitForceLocation = isOpen(j, 18);
//        App.isFaceAuditOpen = isOpen(j, 28);
//        App.isDoorControlOpen = isOpen(j, 10);
//        App.isMR = isOpen(j, 30);
//        App.isOvertimeNonsupportChooseType = isOpen(j, 33);
//        App.isApplyReimbursementOpen = isOpen(j, 34);
//        App.isApplySalesOpen = isOpen(j, 35);
//        App.isApplyBuyOpen = isOpen(j, 36);
//        App.isApplyGenerlOpen = isOpen(j, 37);



        isProjectCheckAuditOpen = isOpen(j, 39);


//        App.isProjectOpen = isProjectRelevanceOpen();
//        App.isWifiPickOpen = isOpen(j, 42);

        isNonsupportMap = isOpen(j, 51);

//        App.isOvertimeNonsupportWriteReason = isOpen(j, 49);
//        App.isGHStartOpen = isOpen(j, 54);
//        App.isMyActivityOpen = isOpen(j, 55);
//        App.isResumeLeaveOpen = isOpen(j, 56);
//        App.isShareApk2OtherOpen = isOpen(j, 57);
//        App.isLeaveAgreeNeedReason = isOpen(j, 61);
//        App.isAllographOpen = CAMActivity.isHR;
//        App.isChangeFaceOpen = isOpen(j, 58);
//        App.isGeneralNoticeOpen = isOpen(j, 59);
//        App.isEIPNoticeOpen = isOpen(j, 60);
//        App.hasUnbindPermission = isOpen(j, 62);
//        App.isExperience = isOpen(j, 52);
    }

//    public static boolean isAttendSuperior() {
//        return App.isAttendCheckOpen || App.isAttendAuditOpen;
//    }
//
//    public static boolean isKQHDViewVisible() {
//        return App.isAttendCheckOpen || App.isAttendAuditOpen;
//    }

    private static boolean isOpen(long j, int i) {
        return ((1 << i) & j) > 0;
    }

    private static boolean isProjectCostStatisticsOpen(boolean z, boolean z2) {
        return z || z2;
    }

    private static boolean isProjectRelevanceOpen() {
        return hasProjectManagePermission || hasProjectCostPermission || isProjectWorkOpen || isProjectWorkAuditOpen || isProjectCostStatisticsOpen || isProjectCheckAuditOpen || isProjectFillCheckOpen || isProjectFillCheckAuditOpen;
    }

//    public static boolean isSPViewVisible() {
//        if (App.isLeaveAuditOpen || App.isOverWorkAuditOpen || App.isBusinessAuditOpen || App.isPatcheckAuditOpen || App.isPatchClockAuditOpen) {
//        }
//        return true;
//    }


}
