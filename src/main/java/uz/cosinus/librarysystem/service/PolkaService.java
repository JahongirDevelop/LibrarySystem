package uz.cosinus.librarysystem.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.cosinus.librarysystem.dto.request.PolkaCR;
import uz.cosinus.librarysystem.dto.response.PolkaResponse;
import uz.cosinus.librarysystem.enitity.PolkaEntity;
import uz.cosinus.librarysystem.enitity.ShelfEntity;
import uz.cosinus.librarysystem.repository.PolkaRepository;
import uz.cosinus.librarysystem.repository.ShelfRepository;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PolkaService {
    private final PolkaRepository polkaRepository;
    private final ShelfRepository shelfRepository;

    public PolkaResponse createPolka(PolkaCR polkaCR) {
        PolkaEntity polkaEntity = new PolkaEntity();
        polkaEntity.setNumber(polkaCR.getNumber());
        ShelfEntity shkaf = shelfRepository.findById(polkaCR.getShelfId())
                .orElseThrow(() -> new EntityNotFoundException("shelf with id " + polkaCR.getShelfId() + " not found."));
        polkaEntity.setShelf(shkaf);

        PolkaEntity savedPolka = polkaRepository.save(polkaEntity);
        return mapToPolkaResponse(savedPolka);

    }
    private PolkaResponse mapToPolkaResponse(PolkaEntity PolkaEntity) {
        PolkaResponse polkaResponse = PolkaResponse.builder()
                .Id(PolkaEntity.getId())
                .number(PolkaEntity.getNumber())
                .build();
        return polkaResponse;
    }

        public List<PolkaEntity> getAllPolkas () {
            return polkaRepository.findAll();
        }

        public PolkaEntity getPolkaById (UUID polkaId){
            return polkaRepository.findById(polkaId)
                    .orElseThrow(() -> new EntityNotFoundException("Polka not found with id: " + polkaId));
        }

        public PolkaEntity updatePolka (UUID polkaId, PolkaCR updatedPolkaCR){
            PolkaEntity existingPolka = getPolkaById(polkaId);
            existingPolka.setNumber(updatedPolkaCR.getNumber());
            existingPolka.setBookCount(updatedPolkaCR.getBookCount());
            return polkaRepository.save(existingPolka);
        }

        public void deletePolkaById (UUID polkaId){
            if (!polkaRepository.existsById(polkaId)) {
                throw new EntityNotFoundException("Polka not found with id: " + polkaId);
            }
            polkaRepository.deleteById(polkaId);
        }


}
