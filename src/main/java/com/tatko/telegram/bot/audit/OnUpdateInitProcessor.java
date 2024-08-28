package com.tatko.telegram.bot.audit;

import com.tatko.telegram.bot.service.TelegramBotConfiguratorService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

@Aspect
@Component
@Slf4j
public class OnUpdateInitProcessor {

    /**
     * Autowired by Spring TelegramBotConfiguratorService bean.
     */
    @Autowired
    private TelegramBotConfiguratorService telegramBotConfiguratorService;

    /**
     * Pointcut for On Update Init Processor.
     */
    @Pointcut("@annotation(com.tatko.telegram.bot.audit"
            + ".OnUpdateReceivedBeforeProcessorAnnotation)")
    public void auditPoint() {
    }

    /**
     * Launch some init processes for On Update Event.
     * @param proceedingJoinPoint
     * @return Object.
     * @throws Throwable
     */
    @Around("auditPoint()")
    public Object doAround(final ProceedingJoinPoint proceedingJoinPoint)
            throws Throwable {

        log.debug("Audit @Around advice: {}", proceedingJoinPoint);

        Object[] args = proceedingJoinPoint.getArgs();
        Update update = (Update) args[0];

        telegramBotConfiguratorService.configureServiceData(update);

        return proceedingJoinPoint.proceed();

    }


}
