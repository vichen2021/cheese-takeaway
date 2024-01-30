package com.cheese.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.extension.plugins.inner.BlockAttackInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.cheese.context.BaseContext;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.baomidou.mybatisplus.autoconfigure.ConfigurationCustomizer;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.handler.TenantLineHandler;
import com.baomidou.mybatisplus.extension.plugins.inner.TenantLineInnerInterceptor;

import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.LongValue;

import java.util.Arrays;

/**
 * @author miemie
 * @since 2018-08-10
 */
@Configuration
@MapperScan("*com.cheese.mapper")
public class MybatisPlusConfig
{

    /**
     * 新多租户插件配置,一缓和二缓遵循mybatis的规则,需要设置 MybatisConfiguration#useDeprecatedExecutor = false 避免缓存万一出现问题
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor()
    {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new TenantLineInnerInterceptor(new TenantLineHandler()
        {

            // 设置当前租户Id
            @Override
            public Expression getTenantId()
            {
                return new LongValue(BaseContext.getCurrentId());
            }

            @Override
            public String getTenantIdColumn()
            {
                // 对应数据库租户ID的列名
                return "merchant_id";
            }

            // 这是 default 方法,默认返回 false 表示所有表都需要拼多租户条件
            @Override
            public boolean ignoreTable(String tableName)
            {
                String[] ignoreTables = {"users", "system_user", "merchant", "address_book", "shopping_cart", "rider"};
                return Arrays.asList(ignoreTables).contains(tableName.toLowerCase());
//                for (String ignoreName : ignoreTables)
//                {
//                    if (!ignoreName.equalsIgnoreCase(tableName))
//                    {
//                        return false;
//                    }
//                }
            }
        }));

        // 针对 update 和 delete 语句 作用: 阻止恶意的全表更新删除
        interceptor.addInnerInterceptor(new BlockAttackInnerInterceptor());
        // 分页插件
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.POSTGRE_SQL));
        // 用了分页插件必须设置 MybatisConfiguration#useDeprecatedExecutor = false  // 已弃用！！！


        return interceptor;
    }
}

