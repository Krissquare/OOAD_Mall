package cn.edu.xmu.oomall.shop.controller;

import cn.edu.xmu.oomall.core.util.Common;
import cn.edu.xmu.oomall.core.util.ReturnNo;
import cn.edu.xmu.oomall.core.util.ReturnObject;
import cn.edu.xmu.oomall.shop.model.bo.Category;
import cn.edu.xmu.oomall.shop.model.vo.CategoryRetVo;
import cn.edu.xmu.oomall.shop.model.vo.CategoryVo;
import cn.edu.xmu.oomall.shop.service.CategoryService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * 商品分类Controller
 *
 * @author Zhiliang Li 22920192204235
 * @date 2021/11/12
 */
@Api(value = "商品类别API", tags = "商品类别API")
@RestController
@RequestMapping(value = "/", produces = "application/json;charset=UTF-8")
public class CategoryController {
    @Autowired
    HttpServletResponse httpServletResponse;

    @Autowired
    CategoryService categoryService;

    @ApiOperation(value = "查询商品分类关系")
    @ApiImplicitParams({
            @ApiImplicitParam(name="id", value = "种类id",required = true, dataType="Integer", paramType="path")
    })
    @ApiResponses({
            @ApiResponse(code = 0, message = "成功"),
            @ApiResponse(code = 504, message = "资源不存在"),
            @ApiResponse(code = 500, message = "服务器内部错误")
    })
    @GetMapping("/categories/{id}/subcategories")
    public Object selectCategories(@PathVariable Long id){
        if(id <= 0){
            return Common.decorateReturnObject(new ReturnObject(ReturnNo.RESOURCE_ID_NOTEXIST));
        }

        return selectSubCategories(id);
    }


    @ApiOperation(value = "查询没有一级分类的二级分类")
    @ApiImplicitParams({
            @ApiImplicitParam(name="id", value = "种类id",required = true, dataType="Integer", paramType="path")
    })
    @ApiResponses({
            @ApiResponse(code = 0, message = "成功"),
            @ApiResponse(code = 504, message = "资源不存在"),
            @ApiResponse(code = 500, message = "服务器内部错误")
    })
    @GetMapping("/orphoncategories")
    public Object selectOrphoncategories(){
        return selectSubCategories(-1L);
    }


    @ApiOperation(value = "管理员新增商品类目")
    @ApiImplicitParams({
            @ApiImplicitParam(name="authorization ", value = "token",required = true, dataType="String", paramType="header"),
            @ApiImplicitParam(name="id", value = "父类别id",required = true, dataType="Integer", paramType="path"),
            @ApiImplicitParam(name="shopId", value = "商户id",required = true, dataType="Integer", paramType="path")
    })
    @ApiResponses({
            @ApiResponse(code = 0, message = "成功"),
            @ApiResponse(code = 500, message = "服务器内部错误"),
            @ApiResponse(code = 504, message = "资源不存在"),
            @ApiResponse(code = 901, message = "类目名称已存在"),
            @ApiResponse(code = 967, message = "不允许增加新的下级分类")
    })
    @PostMapping("/shops/{shopId}/categories/{id}/subcategories")
    public Object addCategories(@PathVariable("id") Long id, @Valid @RequestBody CategoryVo vo, BindingResult bindingResult){
        String createName="admin";
        Long createId=1L;

        // 非法输入
        if(id < 0){
            return Common.decorateReturnObject(new ReturnObject(ReturnNo.RESOURCE_ID_NOTEXIST));
        }
        // vo合法性检查
        var res = Common.processFieldErrors(bindingResult, httpServletResponse);
        if(res != null){
            return res;
        }

        ReturnObject ret=categoryService.newCategory(id,vo.createCategory(),createId,createName);

        if (ret.getCode() == ReturnNo.OK){
            httpServletResponse.setStatus(HttpStatus.CREATED.value());
        }
        if (ret.getData()!= null){
            CategoryRetVo category=new CategoryRetVo((Category) (ret.getData()));
            ret = new ReturnObject(category);
        }
        return Common.decorateReturnObject(ret);
    }


    @ApiOperation(value = "管理员修改商品类目")
    @ApiImplicitParams({
            @ApiImplicitParam(name="authorization ", value = "token",required = true, dataType="String", paramType="header"),
            @ApiImplicitParam(name="id", value = "类别id",required = true, dataType="Integer", paramType="path"),
            @ApiImplicitParam(name="shopId", value = "商户id",required = true, dataType="Integer", paramType="path")
    })
    @ApiResponses({
            @ApiResponse(code = 0, message = "成功"),
            @ApiResponse(code = 500, message = "服务器内部错误"),
            @ApiResponse(code = 504, message = "资源不存在"),
            @ApiResponse(code = 901, message = "类目名称已存在")
    })
    @PutMapping("/shops/{shopId}/categories/{id}")
    public Object changeCategories(@PathVariable("id") Long id, @Valid @RequestBody CategoryVo vo, BindingResult bindingResult){
        String modiName="admin";
        Long modifyId=1L;
        // vo合法性检查
        var res = Common.processFieldErrors(bindingResult, httpServletResponse);
        if(res != null){
            return res;
        }

        ReturnObject ret=categoryService.changeCategory(id,vo.createCategory(),modifyId,modiName);
        return Common.decorateReturnObject(ret);
    }

    @ApiOperation(value = "管理员删除商品类目")
    @ApiImplicitParams({
            @ApiImplicitParam(name="shopId", value = "商户id",required = true, dataType="Integer", paramType="path"),
            @ApiImplicitParam(name="authorization", value="Token", required = true, dataType="String", paramType="header"),
            @ApiImplicitParam(name="id", value = "种类id",required = true, dataType="Integer", paramType="path")
    })
    @ApiResponses({
            @ApiResponse(code = 0, message = "成功"),
            @ApiResponse(code = 504, message = "资源不存在"),
            @ApiResponse(code = 500, message = "服务器内部错误"),
    })
    @DeleteMapping("/shops/{shopId}/categories/{id}")
    public Object deleteCategories(@PathVariable("id") Long id){
        // 若id为0或-1时不允许删除
        if(id<=0){
            return Common.decorateReturnObject(new ReturnObject(ReturnNo.RESOURCE_ID_NOTEXIST)) ;
        }

        ReturnObject ret=categoryService.deleteCategoryById(id);
        return Common.decorateReturnObject(ret);
    }

    private Object selectSubCategories(Long id){
        ReturnObject ret=categoryService.getSubCategories(id);
        if(ret.getData()!=null){
            List<Category> categories = (List<Category>)ret.getData();
            List<CategoryRetVo> categoryRetVos = new ArrayList<>();
            for(Category category:categories){
                CategoryRetVo categoryRetVo=new CategoryRetVo(category);
                categoryRetVos.add(categoryRetVo);
            }
            ret = new ReturnObject(categoryRetVos);
        }
        return Common.decorateReturnObject(ret);
    }

}
