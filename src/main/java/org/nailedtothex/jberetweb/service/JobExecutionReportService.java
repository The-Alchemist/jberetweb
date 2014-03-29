package org.nailedtothex.jberetweb.service;

import org.nailedtothex.jberetweb.model.bean.DataTablesBean;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.Tuple;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kyle on 2014/03/29.
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
@Path("/JobExecutionReportService")
public class JobExecutionReportService {
    @EJB
    JobRepositoryService repositoryService;
    @EJB
    DataTablesService dataTablesService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public DataTablesBean getJobExecutionReport(
            @QueryParam("sEcho") Integer sEcho,
            @QueryParam("iDisplayLength") Integer iDisplayLength,
            @QueryParam("iDisplayStart") Integer iDisplayStart,
            @QueryParam("sSortDir_0") String sSortDir_0) {

        List<Tuple> tuples = repositoryService.findJobListReport(sSortDir_0, iDisplayStart, iDisplayLength);
        List<List<String>> aaData = dataTablesService.createAaDataWithDateTime(tuples);
        Long count = repositoryService.countJobExecution();

        DataTablesBean dataTablesBean = new DataTablesBean();
        dataTablesBean.setsEcho(sEcho);
        dataTablesBean.setAaData(aaData);
        dataTablesBean.setiTotalRecords(String.valueOf(count));
        dataTablesBean.setiTotalDisplayRecords(String.valueOf(count));

        return dataTablesBean;
    }
}