package pagether.domain.council.application;

import pagether.domain.council.domain.Council;
import pagether.domain.council.dto.req.AddCouncilRequest;
import pagether.domain.council.dto.req.UpdateCouncilRequest;
import pagether.domain.council.dto.res.CouncilResponse;
import pagether.domain.council.dto.res.CouncilsResponse;
import pagether.domain.council.dto.res.SearchResponse;
import pagether.domain.council.exception.CouncilNotFoundException;
import pagether.domain.council.repository.CouncilRepository;
import pagether.domain.council_item.domain.ItemType;
import pagether.domain.council_item.repository.CouncilItemRepository;
import pagether.domain.count.application.CountService;
import pagether.domain.user.application.UserService;
import pagether.domain.user.domain.Role;
import pagether.domain.user.domain.User;
import pagether.domain.user.dto.req.UserRequest;
import pagether.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CouncilService {

    private final CouncilRepository councilRepository;
    private final CouncilItemRepository councilItemRepository;
    private final UserRepository userRepository;
    private final UserService userService;
    private final CountService countService;


    private static final String INIT_COUNCIL_ID = "council";
    private static final String INIT_COUNCIL_PW = "0000";
    private static final String GLOBAL = "global";
    private static final String PREFIX_GLOBAL = "G";
    private static final String PREFIX_MEDICAL = "M";

    public CouncilResponse save(AddCouncilRequest request, MultipartFile pic) throws Exception {
        Council council = Council.builder()
                .name(request.getName())
                .college(request.getCollege())
                .location(request.getLocation())
                .operatingHours(request.getOperatingHours())
                .usageGuidelines(request.getUsageGuidelines())
                .latitude(request.getLatitude())
                .longitude(request.getLongitude())
                .isCouncilSelfManage(false)
                .isVisible(false)
                .build();

        Council savedCouncil = councilRepository.save(council);
        UserRequest userRequest = new UserRequest();
        userRequest.setNickname(request.getName());
        userRequest.setUserid(INIT_COUNCIL_ID + savedCouncil.getCouncilId());
        userRequest.setPassword(INIT_COUNCIL_PW);
        userRequest.setRole(Role.MANAGER);
        userService.register(userRequest, pic);
        User user = userRepository.findByUserId(INIT_COUNCIL_ID + savedCouncil.getCouncilId()).get();
        savedCouncil.setManager(user);
        savedCouncil = councilRepository.save(savedCouncil);
        return new CouncilResponse(savedCouncil);
    }

    public CouncilResponse get(Integer id) {
        if (!councilRepository.existsById(id)) {
            throw new CouncilNotFoundException();
        }
        Council council = councilRepository.findById(id).get();
        if (!council.getIsVisible()) {
            throw new CouncilNotFoundException();

        }
        countService.count(council.getName());
        return new CouncilResponse(council, council.getManager().getImgPath());
    }

    public CouncilResponse getCouncilByManager(String manager) {
        User user = userRepository.findByUserId(manager).get();
        Council council = councilRepository.findByManager(user);
        return new CouncilResponse(council, council.getManager().getImgPath());
    }

    // 모두 조회
    public List<CouncilsResponse> getAll() {
        List<CouncilsResponse> councilsDTO = new ArrayList<>();
        List<Council> councils = councilRepository.findAllByIsVisibleOrderByCollege(true);
        for (Council council : councils) {
            CouncilsResponse dto = new CouncilsResponse();
            dto.setCouncilId(council.getCouncilId());
            dto.setCollege(council.getCollege());
            dto.setName(council.getName());
            dto.setHeart(council.getHeart());
            dto.setProvidedItemCount(councilItemRepository.countByCouncilAndType(council, ItemType.PROVIDED));
            dto.setRentalItemCount(councilItemRepository.countByCouncilAndType(council, ItemType.RENTAL));
            dto.setImgPath(council.getManager().getImgPath());
            councilsDTO.add(dto);
        }
        return councilsDTO;
    }

    // 모두 조회
    public List<CouncilsResponse> getAllByCampus(String campus) {
        List<CouncilsResponse> councilsDTO = new ArrayList<>();
        List<Council> councils = councilRepository.findAllByCollegeStartingWithAndIsVisibleOrderByCollege(campus.equals(GLOBAL) ? PREFIX_GLOBAL : PREFIX_MEDICAL, true);
        for (Council council : councils) {
            CouncilsResponse dto = new CouncilsResponse();
            dto.setCouncilId(council.getCouncilId());
            dto.setCollege(council.getCollege());
            dto.setHeart(council.getHeart());
            dto.setIsCouncilSelfManage(council.getIsCouncilSelfManage());
            dto.setName(council.getName());
            dto.setProvidedItemCount(councilItemRepository.countByCouncilAndType(council, ItemType.PROVIDED));
            dto.setRentalItemCount(councilItemRepository.countByCouncilAndType(council, ItemType.RENTAL));
            dto.setImgPath(council.getManager().getImgPath());
            councilsDTO.add(dto);
        }
        return councilsDTO;
    }

    public List<SearchResponse> getByKeyword(String keyword) {
        List<SearchResponse> councilItemsDTO = new ArrayList<>();
        List<Council> councils = councilRepository.findAllByNameContaining(keyword);
        for (Council council : councils) {
            SearchResponse dto = new SearchResponse(council);
            councilItemsDTO.add(dto);
        }
        return councilItemsDTO;
    }

    public void delete(Integer councilId) {
        if (!councilRepository.existsById(councilId)) {
            throw new CouncilNotFoundException();
        }
        councilRepository.deleteByCouncilId(councilId);
    }


    public void update(Integer councilId, UpdateCouncilRequest request) {
        if (!councilRepository.existsById(councilId)) {
            throw new CouncilNotFoundException();
        }
        Council council = councilRepository.findById(councilId).get();
        council.setLocation(request.getLocation());
        council.setOperatingHours(request.getOperatingHours());
        council.setUsageGuidelines(request.getUsageGuidelines());
        councilRepository.save(council);
    }

    public void countHeart(Integer councilId) {
        if (!councilRepository.existsById(councilId)) {
            throw new CouncilNotFoundException();
        }
        Council council = councilRepository.findById(councilId).get();
        council.setHeart(council.getHeart() + 1);
        councilRepository.save(council);
    }

    public void changeManager(Integer councilId, Boolean isCouncilSelfManage) {
        if (!councilRepository.existsById(councilId)) {
            throw new CouncilNotFoundException();
        }
        Council council = councilRepository.findById(councilId).get();
        council.setIsCouncilSelfManage(isCouncilSelfManage);
    }
}