package com.template.email.report;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ExtentFactory {

    public static ExtentReports getInstance() {

        String fechaHora = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss"));
        ExtentSparkReporter sparkReporter = new ExtentSparkReporter("target/reports/TemplateEmail_" + fechaHora + ".html");


        ExtentReports extentReports = new ExtentReports();
        extentReports.attachReporter(sparkReporter);

        // Información adicional del sistema
        extentReports.setSystemInfo("OS", System.getProperty("os.name"));
        extentReports.setSystemInfo("Ambiente", "QA");
        extentReports.setSystemInfo("Navegador", "Chrome");

        return extentReports;
    }

}
