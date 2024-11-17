package tagCloudock.domain.stock.presentation;

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
    public ResponseDto save(@RequestPart AddStockRequest request) {
        stockService.save(request);
        return ResponseDto.of(OK.value(), ResponseMessage.SUCCESS_CREATE.getMessage());
    }

    @GetMapping("/list")
    public ResponseDto<StocksResponse> getList() {
        StocksResponse response = stockService.getList();
        return ResponseDto.of(OK.value(), ResponseMessage.SUCCESS_READ.getMessage(), response);
    }
}