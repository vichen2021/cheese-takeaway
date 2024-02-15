package com.cheese.controller.user;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cheese.context.BaseContext;
import com.cheese.entity.AddressBook;
import com.cheese.result.Result;
import com.cheese.service.AddressBookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author WEI CHEN GUANG
 * @date 2024/2/13 14:45
 * @projectName cheese-takeaway
 */
@RestController
@RequestMapping("/user/addressBook")
@Tag(name = "收货地址管理")
public class AddressBookController
{
    @Autowired
    private AddressBookService addressBookService;

    /**
     * 查询当前登录用户的所有地址信息
     *
     * @return
     */
    @GetMapping("/list")
        @Operation(summary = "查询当前登录用户的所有地址信息")
    public Result<List<AddressBook>> list() {
        AddressBook addressBook = new AddressBook();
        addressBook.setUserId((Long) BaseContext.getCurrentId());
        List<AddressBook> list = addressBookService.list(new LambdaQueryWrapper<AddressBook>().eq(AddressBook::getUserId, addressBook.getUserId()));
        return Result.success(list);
    }

    /**
     * 新增地址
     *
     * @param addressBook
     * @return
     */
    @PostMapping
    @Operation(summary = "新增地址")
    public Result save(@RequestBody AddressBook addressBook) {
        addressBook.setUserId((Long) BaseContext.getCurrentId());
        addressBook.setIsDefault(0);
        addressBookService.save(addressBook);
        return Result.success();
    }
    /**
     * 查询默认地址
     *
     * @return
     */
    @GetMapping("/default")
    @Operation(summary = "查询默认地址")
    public Result isDefault() {
        //按道理来说应该用selectOne,这里是跟随黑马的代码用的，都一样,因为只会有一个默认
        List<AddressBook> list = addressBookService.list(new LambdaQueryWrapper<AddressBook>()
                .eq(AddressBook::getUserId, BaseContext.getCurrentId())
                .eq(AddressBook::getIsDefault, 1)
        );
        if (list != null && list.size() > 0) {
            return Result.success(list.get(0));
        }
        return Result.error("没有查询到默认地址");
    }

    /**
     * 根据id修改地址
     *
     * @param addressBook
     * @return
     */
    @PutMapping
    @Operation(summary = "根据id修改地址")
    //根据id修改如果用mp这样简单点，直接传对象,然后该方法根据主键注解自己找id
    public Result update(@RequestBody AddressBook addressBook) {
        addressBookService.updateById(addressBook);
        return Result.success();
    }

    /**
     * 根据id删除地址
     *
     * @param id
     * @return
     */
    @DeleteMapping
    @Operation(summary = "根据id删除地址")
    public Result delete(Long id) {
        addressBookService.removeById(id);
        return Result.success();
    }

    /**
     * 根据id查询地址
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    @Operation(summary = "根据id查询地址")
    public Result getById(@PathVariable Long id) {
        return Result.success(addressBookService.getById(id));
    }

    /**
     * 设置默认地址
     *
     * @param addressBook
     * @return
     */
    @PutMapping("/default")
    @Operation(summary = "设置默认地址")
    @Transactional
    public Result setIsDefault(@RequestBody AddressBook addressBook) {
        //先将所有地址设置为普通地址
        addressBook.setIsDefault(0);
        addressBookService.update(addressBook, new LambdaQueryWrapper<AddressBook>().eq(AddressBook::getUserId, BaseContext.getCurrentId()));
        //再将当前地址设置为默认
        addressBook.setIsDefault(1);
        addressBookService.updateById(addressBook);
        return Result.success();
    }
}
