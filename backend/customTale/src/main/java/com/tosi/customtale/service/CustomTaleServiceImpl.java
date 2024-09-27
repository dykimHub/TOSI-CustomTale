package com.tosi.customtale.service;

public class CustomTaleServiceImpl implements CustomTaleService {


//    private final CustomTaleRepository customTaleRepository;
//
//    @Autowired
//    public CustomTaleService(CustomTaleRepository customTaleRepository) {
//        this.customTaleRepository = customTaleRepository;
//    }
//
//
//    //customTaleId에 해당하는 CustomTale 엔터티 (커스텀 동화 상세조회)
//    public Optional<CustomTale> getCustomTale(Integer customTaleId) {
//        return customTaleRepository.findById(customTaleId);
//    }
//
//    //userId에 해당하는 CustomTale 엔터티를 조회 (나의 책장 - 내가 만든 동화)
//    public List<CustomTale> getCustomTalesByUserId(Integer userId) {
//        // Implement your custom query method in the repository if needed
//        return customTaleRepository.findByUserId(userId);
//    }
//
//    //opened가 true인 CustomTale 엔터티를 조회 (친구들이 만든 동화보기 목록)
//    public List<CustomTale> getCustomTales() {
//        return customTaleRepository.findByOpened(true);
//    }
//
//    //CustomTale 엔터티를 저장 (내가 만드는 동화 저장)
//    public CustomTale postCustomTale(CustomTale customTale) {
//        return customTaleRepository.save(customTale);
//    }
//
//    //customTaleId에 해당하는 CustomTale 엔터티의 opened 값 변경 (나의 책장 - 내가 만든 동화)
//    public CustomTale putCustomTale(Integer customTaleId, boolean opened) {
//        Optional<CustomTale> optionalCustomTale = customTaleRepository.findById(customTaleId);
//
//        if (optionalCustomTale.isPresent()) {
//            CustomTale customTale = optionalCustomTale.get();
//            customTale.setOpened(opened);
//            return customTaleRepository.save(customTale);
//        } else {
//            return null;
//        }
//    }
//
//    //customTaleId에 해당하는 CustomTale 엔터티를 삭제
//    public boolean deleteCustomTale(Integer customTaleId) {
//        customTaleRepository.deleteById(customTaleId);
//        return true;
//    }
//
//    public String split_sentences(String string) {
//        string = string.replace("n", "");
//        string = string = string.replace("\\", "");
//
//        StringBuilder sb = new StringBuilder();
//        char[] str2char = string.toCharArray();
//
//        boolean flag = false;
//        for (char c : str2char) {
//            sb.append(c);
//
//            if (!Character.isLetterOrDigit(c) && !Character.isWhitespace(c) && (c != ',')) {
//                if (c == '"' && !flag) { // 첫번째 따옴표이면
//                    flag = true; // 따옴표 flag
//                } else if (c == '"' && flag) { // 두번째 따옴표라면
//                    flag = false; // 따옴표 flag 취소
//                    sb.append("\n"); // 띄우기
//                } else if (!flag) { // 따옴표 안 문장이 아니라면
//                    sb.append("\n"); // 띄우기
//                }
//
//            }
//        }
//
//        return sb.toString();
//    }
//
//    public List<Page> paging(String splitted_contents) {
//        int p = 1;
//        List<Page> pages = new ArrayList<>();
//
//        String[] sentences = splitted_contents.split("\n");
//
//        for (int j = 0; j < sentences.length - 1; j += 2) {
//            Page page = Page.builder()
//                    .leftNo(p)
//                    .rightNo(p + 1)
//                    .right(sentences[j] + "\n" + sentences[j + 1])
//                    .flipped(false)
//                    .build();
//            pages.add(page);
//            p += 2;
//        }
//// 문장이 홀수개 일 때
//        if (sentences.length % 2 != 0) {
//            Page page = Page.builder()
//                    .leftNo(p)
//                    .rightNo(p + 1)
//                    .right(sentences[sentences.length - 1])
//                    .build();
//
//            pages.add(page);
//        }
//
//        return pages;
//    }

}
