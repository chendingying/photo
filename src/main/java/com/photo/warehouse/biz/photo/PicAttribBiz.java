package com.photo.warehouse.biz.photo;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.photo.warehouse.constants.CommonConstants;
import com.photo.warehouse.mapper.photo.PicAttribMapper;
import com.photo.warehouse.model.photo.PicAttrib;
import com.photo.warehouse.util.BaseBiz;
import com.photo.warehouse.util.Query;
import com.photo.warehouse.util.TableResultResponse;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import tk.mybatis.mapper.entity.Example;

import javax.imageio.ImageIO;
import javax.persistence.Table;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.*;
import java.lang.reflect.ParameterizedType;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 图片管理（Service）
 * Created by CDZ on 2018/12/8.
 */
@Service
@Component
@Transactional(rollbackFor = Exception.class)
public class PicAttribBiz extends BaseBiz<PicAttribMapper,PicAttrib> {

    public void updatePicById(PicAttrib picAttrib){
        mapper.updatePicById(picAttrib);
    }

    /**
     * 首页图片明细
     * @param vcGroupid
     * @return
     */
    public List<PicAttrib> getPicAttribByGid(String vcGroupid){
        return mapper.getPicAttribByGid(vcGroupid);
    }

    /**
     * 图片置顶
     * @param picAttrib
     * @throws ParseException
     */
    public void updateStick(PicAttrib picAttrib) throws ParseException {
        List<PicAttrib> picAttribList = mapper.getPicAttribByGid(picAttrib.getVcGroupid());
        for(PicAttrib pic : picAttribList){
            if(picAttrib.getPicStick() == 1){
                pic.setPicStickCreateTime(new Date());
                Integer stickTime = picAttrib.getPicStickTime();
                Date newDate = addDate(pic.getPicStickCreateTime(),stickTime);
                pic.setPicStickEndTime(newDate);
                pic.setPicStick(picAttrib.getPicStick());
                pic.setPicStickTime(picAttrib.getPicStickTime());
            }else{
                pic.setPicStick(picAttrib.getPicStick());
                pic.setPicStickEndTime(null);
                pic.setPicStickTime(picAttrib.getPicStickTime());
            }
            mapper.updateStick(pic);
        }

    }

    public static Date addDate(Date date, Integer day) throws ParseException {
        long time = date.getTime(); // 得到指定日期的毫秒数
        day = day * 24 * 60 * 60 * 1000; // 要加上的天数转换成毫秒数
        time += day; // 相加得到新的毫秒数
        return new Date(time); // 将毫秒数转换成日期
    }

    public PicAttrib selectByVcPid(String vcPid){
        PicAttrib picAttrib = mapper.selectByVcPid(vcPid);
        if(picAttrib == null){
            return null;
        }
//        if(picAttrib.getVcThumb() != null && !picAttrib.getVcThumb().equals("")){
//            try{
//                // 创建文件输入流对象
//                FileInputStream is = new FileInputStream(picAttrib.getVcThumb());
//                byte[] byt = new byte[is.available()];
//                is.read(byt);
//                // 关闭输入流
//                is.close();
//                picAttrib.setVcThumb(new String(byt));
//            }catch(Exception e){
//                e.printStackTrace();
//            }
//        }
        return picAttrib;
    }

    public void addNewPicture(PicAttrib picAttrib,String pid, String upload, String thumb, Integer bytes, Integer main,String gid){
        picAttrib.setVcPid(pid);
        if(main == 0){
            picAttrib.setcMain("1");
        }
        else{
            picAttrib.setcMain("0");
        }
        picAttrib.setVcGroupid(gid);
        picAttrib.setcMain(main.toString());
        picAttrib.setVcBytes(bytes.toString());
        picAttrib.setVcThumb(thumb);
        picAttrib.setVcUpload(upload);
        mapper.insertSelective(picAttrib);
    }

    public PicAttrib selectTopPicAttrib(PicAttrib picAttrib){
        return mapper.selectTopPicAttrib(picAttrib);
    }

    /**
     *审核图片管理分页查询
     * @param query
     * @param response
     * @return
     * @throws IOException
     * @throws ParseException
     */
    public TableResultResponse<PicAttrib> PlicDuditlist(Query query, HttpServletResponse response) throws IOException, ParseException {
        Class<PicAttrib> clazz = (Class<PicAttrib>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[1];
        Example example = new Example(clazz);
        example.setOrderByClause(CommonConstants.DUDIT_SETORDERBYCLAUSE);
        Example.Criteria criteria = example.createCriteria();
        criteria.andLike("cMain",CommonConstants.CMAIN);
        if(query.entrySet().size()>0) {
            for (Map.Entry<String, Object> entry : query.entrySet()) {
                if(entry.getKey().equals("span")){
                    continue;
                }
                criteria.andLike(entry.getKey(), "%" + entry.getValue().toString() + "%");
            }
        }
        Page<Object> result = PageHelper.startPage(query.getPage(), query.getLimit());
        List<PicAttrib> list = mapper.selectByExample(example);
        return new TableResultResponse<PicAttrib>(result.getTotal(), list);
    }

    /**
     * 首页分页查询
     * @param query
     * @param response
     * @return
     * @throws IOException
     * @throws ParseException
     */
    public TableResultResponse<PicAttrib> selectByQuery(Query query, HttpServletResponse response) throws IOException, ParseException {
        DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
        Class<PicAttrib> clazz = (Class<PicAttrib>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[1];
        Example example = new Example(clazz);
        example.setOrderByClause(CommonConstants.PICATTRIB_SETORDERBYCLAUSE);
        Example.Criteria criteria = example.createCriteria();
        criteria.andLike("cMain", CommonConstants.CMAIN);
        if(query.entrySet().size()>0) {
            Date dtPostimeBegin = null;
            Date dtPostimeEnd = null;
            Date vcPhotimeBegin = null;
            Date vcPhotimeEnd = null;
            for (Map.Entry<String, Object> entry : query.entrySet()) {
                if(entry.getValue().equals("")){
                    continue;
                }
                if(entry.getKey().equals("span")){
                    continue;
                }
                if(entry.getKey().equals("dtPostimeBegin")){
                    dtPostimeBegin = format1.parse(entry.getValue().toString());
                    continue;
                }if(entry.getKey().equals("dtPostimeEnd")){
                    dtPostimeEnd = format1.parse(entry.getValue().toString());
                    continue;
                }if(entry.getKey().equals("vcPhotimeBegin")){
                    vcPhotimeBegin = format1.parse(entry.getValue().toString());
                    continue;
                }if(entry.getKey().equals("vcPhotimeEnd")){
                    vcPhotimeEnd = format1.parse(entry.getValue().toString());
                    continue;
                }
                criteria.andLike(entry.getKey(), "%" + entry.getValue().toString() + "%");
            }
            if(dtPostimeBegin != null && dtPostimeEnd != null){
                criteria.andBetween("dtPostime",dtPostimeBegin,dtPostimeEnd);
            }if(vcPhotimeBegin != null && vcPhotimeEnd != null){
                criteria.andBetween("vcPhotime",vcPhotimeBegin,vcPhotimeEnd);
            }
        }
        Page<Object> result = PageHelper.startPage(query.getPage(), query.getLimit());
        List<PicAttrib> list = mapper.selectByExample(example);
        return new TableResultResponse<PicAttrib>(result.getTotal(), list);
    }

    private byte[] toByteArray(InputStream in) throws IOException {

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024 * 4];
        int n = 0;
        while ((n = in.read(buffer)) != -1) {
            out.write(buffer, 0, n);
        }
        return out.toByteArray();
    }

    public PicAttrib nextCluster(String vcPid,Query query) throws ParseException {
//        DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
//        if(query.entrySet().size()>0) {
//            PicAttrib picAttrib = new PicAttrib();
//            for (Map.Entry<String, Object> entry : query.entrySet()) {
//                if(entry.getValue().equals("")){
//                    continue;
//                }
//                if(entry.getKey().equals("vcName")){
//                    picAttrib.setVcName(entry.getValue().toString());
//                    continue;
//                }if(entry.getKey().equals("vcPid")){
//                    picAttrib.setVcPid(entry.getValue().toString());
//                    continue;
//                }if(entry.getKey().equals("vcAuthor")){
//                    picAttrib.setVcAuthor(entry.getValue().toString());
//                    continue;
//                }if(entry.getKey().equals("vcKeyword")){
//                    picAttrib.setVcKeyword(entry.getValue().toString());
//                    continue;
//                }if(entry.getKey().equals("vcPhoposi")){
//                    picAttrib.setVcPhoposi(entry.getValue().toString());
//                    continue;
//                }if(entry.getKey().equals("vcKind")){
//                    picAttrib.setVcKind(entry.getValue().toString());
//                    continue;
//                }
//                if(entry.getKey().equals("dtPostimeBegin")){
//                    picAttrib.setDtPostimeBegin(format1.parse(entry.getValue().toString()));
//                    continue;
//                }if(entry.getKey().equals("dtPostimeEnd")){
//                    picAttrib.setDtPostimeEnd(format1.parse(entry.getValue().toString()));
//                    continue;
//                }if(entry.getKey().equals("vcPhotimeBegin")){
//                    picAttrib.setVcPhotimeBegin(format1.parse(entry.getValue().toString()));
//                    continue;
//                }if(entry.getKey().equals("vcPhotimeEnd")){
//                    picAttrib.setVcPhotimeEnd(format1.parse(entry.getValue().toString()));
//                    continue;
//                }
//            }
//
//        }
        return mapper.nextCluster(vcPid);
    }

    public PicAttrib downCluster(String vcPid){
        return mapper.downCluster(vcPid);
    }

    public Integer deletePicAttribByGid(PicAttrib picAttrib){
       return mapper.deletePicAttribByGid(picAttrib);
    }


//    @Scheduled(cron = "0 0 */1 * * ?")
    @Scheduled(cron = "0 0 0 * * ?")
    public void selectForPicStickEndTime(){
        System.out.println("------定时器任务中-------");
        List<PicAttrib> picAttribList = mapper.selectForPicStickEndTime();
        if(picAttribList == null){
            return;
        }
        for(PicAttrib picAttrib : picAttribList){
            picAttrib.setPicStick(0);
            picAttrib.setPicStickTime(0);
            picAttrib.setPicStickEndTime(null);
            picAttrib.setPicStickCreateTime(null);
            mapper.updateByPrimaryKey(picAttrib);
        }
        System.out.println("-----定时器结束---------");
    }
}
