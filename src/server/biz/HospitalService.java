package server.biz;

import server.dao.DoctorDao;
import server.dao.MedicalAdviceDao;
import vo.Doctor;
import vo.MedicalAdvice;

import java.sql.SQLException;

public class HospitalService {
    private DoctorDao doctorDao = new DoctorDao();
    private MedicalAdviceDao medicalAdviceDao = new MedicalAdviceDao();

    public void docRank(MedicalAdvice medicalAdvice, String rank) throws SQLException {//这个rank是单独一个数字0-5
        Double rank_new = Double.valueOf(rank);
        Doctor doctor = doctorDao.query(medicalAdvice.getDocId());
        String[] reSplit = doctor.getRenown().split("/");
        Double totalPoints = Double.valueOf(reSplit[0]);
        Double totalPerson = Double.valueOf(reSplit[1]);
        totalPerson = totalPerson+1;
        totalPoints = totalPoints + rank_new;
        String newRenown = String.valueOf(totalPoints) + "/" + String.valueOf(totalPerson);
        doctor.setRenown(newRenown);
        doctorDao.update(doctor);
        medicalAdvice.setRank(true);
        medicalAdviceDao.update(medicalAdvice);
    }
}
