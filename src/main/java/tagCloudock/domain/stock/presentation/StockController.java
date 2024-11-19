package tagCloudock.domain.stock.presentation;

import org.springframework.security.core.Authentication;
import tagCloudock.domain.stock.dto.req.AddStockRequest;
import tagCloudock.domain.stock.dto.res.StocksResponse;
import tagCloudock.domain.stock.presentation.constant.ResponseMessage;
import tagCloudock.domain.stock.application.StockService;
import tagCloudock.global.config.dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/stock")
@RequiredArgsConstructor
public class StockController {
    private final StockService stockService;

    @PostMapping
    public ResponseDto save(@RequestBody AddStockRequest request, Authentication authentication) {
        stockService.save(request, authentication.getName());
        return ResponseDto.of(OK.value(), ResponseMessage.SUCCESS_CREATE.getMessage());
    }
    @GetMapping("/list")
    public ResponseDto<StocksResponse> getList(Authentication authentication) {
        StocksResponse response = stockService.getList(authentication.getName());
        return ResponseDto.of(OK.value(), ResponseMessage.SUCCESS_READ.getMessage(), response);
    }
    @GetMapping("/search/{keyword}")
    public ResponseDto<StocksResponse> search(@PathVariable String keyword) {
        StocksResponse response = stockService.search(keyword);
        return ResponseDto.of(OK.value(), ResponseMessage.SUCCESS_READ.getMessage(), response);
    }

    @DeleteMapping("/{stockCode}")
    public ResponseDto delete(@PathVariable String stockCode, Authentication authentication) {
        stockService.delete(stockCode, authentication.getName());
        return ResponseDto.of(OK.value(), ResponseMessage.SUCCESS_DELETE.getMessage());
    }
}