package org.firstinspires.ftc.teamcode._Auto;

import android.view.ViewDebug;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.teamcode._Libs.BNO055IMUHeadingSensor;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer.CameraDirection;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.Position;
import org.firstinspires.ftc.robotcore.external.navigation.Velocity;

import java.util.List;
import java.util.Locale;

@Autonomous(name="BackupDepotAuto", group="Autonomous")
public class BackupDepotAuto extends LinearOpMode {

    private ElapsedTime runtime = new ElapsedTime();

    private DcMotor leftfrontDrive = null;
    private DcMotor rightfrontDrive = null;
    private DcMotor leftbackDrive = null;
    private DcMotor rightbackDrive = null;
    private DcMotor armActivator = null;
    private DcMotor lift = null;

    private Servo markerArm;
    private BNO055IMU imu;

    boolean bDebug = false;
    float targetAngle;

    @Override
    public void runOpMode() {
        //this code runs after the init button is pressed
        try {
            leftfrontDrive = hardwareMap.get(DcMotor.class, "frontLeft");
            leftfrontDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

            rightfrontDrive = hardwareMap.get(DcMotor.class, "frontRight");
            rightfrontDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

            leftbackDrive = hardwareMap.get(DcMotor.class, "backLeft");
            leftbackDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            leftbackDrive.setDirection(DcMotor.Direction.REVERSE);

            rightbackDrive = hardwareMap.get(DcMotor.class, "backRight");
            rightbackDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

            lift = hardwareMap.get(DcMotor.class, "Lift");
            lift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            lift.setDirection(DcMotor.Direction.REVERSE);

            markerArm = hardwareMap.get(Servo.class, "markerArm");

            armActivator = hardwareMap.get(DcMotor.class, "armActivator");
            armActivator.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        } catch (IllegalArgumentException iax) {
            bDebug = true;
        }

        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit = BNO055IMU.AngleUnit.DEGREES;
        parameters.accelUnit = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        parameters.calibrationDataFile = "BNO055IMUCalibration.json";
        parameters.loggingEnabled = true;
        parameters.loggingTag = "IMU";
        parameters.accelerationIntegrationAlgorithm = new JustLoggingAccelerationIntegrator();

        imu = hardwareMap.get(BNO055IMU.class, "imu");
        imu.initialize(parameters);

        waitForStart(); //the rest of the code begins after the play button is pressed

        sleep(3000);

        drive(0.35, 0.5);

        turn(90.0f);

        drive(1.8, 0.5);

        turn(45.0f);

        drive(2.5, -0.5);

        sleep(1000);

        markerArm.setPosition(1);

        sleep(1000);

        markerArm.setPosition(0);

        sleep(1000);

        drive(2.5,.75);

        turn(179.0f);

        drive(1.5, 1.0);

        requestOpModeStop(); //end of autonomous
    }

    public void drive(double time, double power){
        runtime.reset();
        while(runtime.seconds() < time){
            leftfrontDrive.setPower(power);
            leftbackDrive.setPower(power);
            rightfrontDrive.setPower(power);
            rightbackDrive.setPower(power);
        }
    }

    double mod(double a, double b)
    {
        if(a < 0){
            a += b;
        }
        return a;
    }

    public void turn (double turnAngle){ //left turn
        final Orientation angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);

        double angle = Double.parseDouble(formatAngle(angles.angleUnit, angles.firstAngle)); //turns the angle from the imu which is a string into a double

        double startAngle = mod(angle, 360.0); //clips range from 0 - 359

        double targetAngle = mod((startAngle + turnAngle), 360.0);

        while(opModeIsActive()){

            if(targetAngle - mod(Double.parseDouble(formatAngle(imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES).angleUnit, imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES).firstAngle)), 360.0) < 3.0) { //3 degree margin of error
                leftfrontDrive.setPower(0);
                leftbackDrive.setPower(0);
                rightfrontDrive.setPower(0);
                rightbackDrive.setPower(0);
                break;
            }

            leftfrontDrive.setPower(-.3);
            leftbackDrive.setPower(-.3);
            rightfrontDrive.setPower(.3);
            rightbackDrive.setPower(.3);
        }
    }

    String formatAngle (AngleUnit angleUnit,double angle){
        return formatDegrees(AngleUnit.DEGREES.fromUnit(angleUnit, angle));
    }

    String formatDegrees ( double degrees){
        return String.format(Locale.getDefault(), "%.1f", AngleUnit.DEGREES.normalize(degrees));
    }
}
