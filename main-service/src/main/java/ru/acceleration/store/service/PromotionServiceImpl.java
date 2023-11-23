package ru.acceleration.store.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.acceleration.store.dto.ProductShortDto;
import ru.acceleration.store.dto.PromotionDto;
import ru.acceleration.store.mapper.PromotionMapper;
import ru.acceleration.store.model.Promotion;
import ru.acceleration.store.model.Sale;
import ru.acceleration.store.repository.PromotionRepository;
import ru.acceleration.store.repository.SaleRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class PromotionServiceImpl implements PromotionService {

    private final PromotionRepository promotionRepository;
    private final PromotionMapper promotionMapper;
    private final ProductService productService;
    private final SaleRepository saleRepository;

    @Override
    public PromotionDto createPromotion(PromotionDto promotionDto) {
        Promotion newPromotion = promotionRepository.save(promotionMapper.toPromotion(promotionDto));
        log.info("Created new Promotion: {}", newPromotion);
        return promotionMapper.toPromotionDto(newPromotion);
    }

    @Transactional
    @Override
    public void deletePromotion(Long promotionId) {
        Promotion promotion = promotionRepository.getExistingPromotion(promotionId);
        List<Sale> salesInPromotion = saleRepository.getSalesByPromotion(promotionId);
        if (salesInPromotion.isEmpty()) {
            promotionRepository.delete(promotion);
            log.info("Deleted promotion with ID: {}", promotionId);
        } else {
            for (Sale sale : salesInPromotion) {
                sale.setPromotion(null);
                saleRepository.save(sale);
            }
            promotionRepository.delete(promotion);
            log.info("Deleted promotion with ID: {}. Unlinked sales from promotion. ", promotionId);
        }
    }

    @Override
    public List<PromotionDto> getPromotions() {
        return promotionRepository
                .findAll()
                .stream()
                .map(promotionMapper::toPromotionDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductShortDto> getPromotion(Long promotionId) {
        return productService.productsInPromotion(promotionId);
    }

    @Override
    public PromotionDto editPromotion(Long promotionId, PromotionDto newPromotionDto) {
        Promotion existingPromotion = promotionRepository.getExistingPromotion(promotionId);
        existingPromotion.setName(newPromotionDto.getName());
        Promotion updatedPromotion = promotionRepository.save(existingPromotion);
        return promotionMapper.toPromotionDto(updatedPromotion);
    }

    @Override
    public Promotion getPromotionById(Long promotionId) {
        return promotionRepository.getReferenceById(promotionId);
    }
}
