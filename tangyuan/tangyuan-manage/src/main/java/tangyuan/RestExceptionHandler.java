package tangyuan;

import com.tangyuan.exception.InternalServerException;
import com.tangyuan.exception.NotFoundException;
import com.tangyuan.exception.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 作者：sunna
 * 时间: 2018/6/5 10:37
 */
@ControllerAdvice
public class RestExceptionHandler
{
    private final static Logger logger = LoggerFactory.getLogger(RestExceptionHandler.class);

    @ExceptionHandler(value = InternalServerException.class)
    @ResponseBody
    public Result handleInternalServerException(InternalServerException e)
    {
        logger.error(e.getMessage(), e);
        return Result.get(null, e.getMessage(), false, e.getCode());
    }

    @ExceptionHandler(value = NotFoundException.class)
    @ResponseBody
    public Result handleResourceNotFoundException(NotFoundException e)
    {
        logger.error(e.getMessage(), e);
        return Result.get(null, e.getMessage(), false, e.getCode());
    }
}
