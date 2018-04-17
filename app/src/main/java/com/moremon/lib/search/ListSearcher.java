package com.moremon.lib.search;

import java.util.ArrayList;

/**
 * Created by evilstorm on 18. 2. 2.
 */

public class ListSearcher {

    private final char FIRST_CHAR[] = new char[]{'ㄱ','ㄲ','ㄴ','ㄷ','ㄸ','ㄹ','ㅁ','ㅂ','ㅃ','ㅅ','ㅆ','ㅇ','ㅈ','ㅉ','ㅊ','ㅋ','ㅌ','ㅍ','ㅎ'};
    private final int[] FIRST_CHAR_START_POSITION = new int[]{44032, 44620, 45208, 45796, 46384,
            46972, 47560, 48148, 48736, 49324,
            49912, 50500, 51088, 51676, 52264,
            52852, 53440, 54028, 54616};


    private ArrayList<SearchInterface> searchList = new ArrayList<SearchInterface>();
    private int personStartIndex = 0;
    private static final ListSearcher instance = new ListSearcher();
    private ListSearcher() {
    }
    public static ListSearcher getInstance() {
        return instance;
    }

    private ArrayList list;
    public void setList(ArrayList list) {
        this.list = list;
    }

    public ArrayList<SearchInterface> getSearchList(String searchWord_Str){
        searchList.clear();
        char[] searchWord =  searchWord_Str.toCharArray();
        int searchWordSize = searchWord.length;
        for (int i = 0; i < searchWordSize; i++) {
            // 첫 글자 검색 후 list를 만듬.
            int choSung = isChoSung(searchWord[i]);
            if(i == 0 ){
                if(choSung < 100){
                    //초성.
                    makeSearchList(choSung);
                }else{
                    //일반.
                    makeSearchList(searchWord[i]+"");
//					searchPerson(searchWord[i]+"");
                }
            }else{
                if(choSung < 100){
                    addList(choSung);
                }else{
                    addList(searchWord[i]+"");
                }
            }
        }
        return searchList;
    }

    public int isChoSung(char charNum){
        int result = 100;
        for(int i=0; i<19; i++){
            if(charNum == FIRST_CHAR[i]){
                result = i;
            }
        }
        return result;
    }

    @SuppressWarnings("unchecked")
    private void addList(int position){
        ArrayList<SearchInterface> arrayList = new ArrayList<>();
        arrayList = (ArrayList<SearchInterface>) searchList.clone();
        searchList.clear();
        int startPos =	FIRST_CHAR_START_POSITION[position];
        int endPos = startPos+587;
        int size = arrayList.size();
        for (int i = 0; i < size; i++) {
            SearchInterface data = arrayList.get(i);
            char[] temp_person = data.getWord().toCharArray();
            int person_size = temp_person.length;
            int start = person_size;
            personStartIndex = data.getSearchPersonPos();
            if(person_size > personStartIndex){
                start = personStartIndex;
            }else{
                start = personStartIndex+1;
            }
            for (int j = start; j < personStartIndex+1; j++) {
                if(temp_person[j] >= startPos && temp_person[j] <= endPos){
                    data.setSearchPersonPos(j+1);
                    searchList.add(data);
                    break;
                }
            }
        }
    }

    @SuppressWarnings("unchecked")
    private void addList(String searchWord){
        ArrayList<SearchInterface> arrayList = new ArrayList<>();
        arrayList = (ArrayList<SearchInterface>) searchList.clone();
        searchList.clear();
        int size = arrayList.size();
        for (int i = 0; i < size; i++) {
            SearchInterface data = arrayList.get(i);
            String temp_person = data.getWord();

            int matchPos = temp_person.indexOf(searchWord, data.getSearchPersonPos());
            personStartIndex = data.getSearchPersonPos();
            if(matchPos == personStartIndex ){
                if(matchPos != -1){
                    data.setSearchPersonPos(matchPos+1);
                    searchList.add(data);
//					continue;
                }
            }
        }
    }


    private void makeSearchList(int position){
        //리스트 전부다 서치.
        searchList.clear();
        allListSearch(list, position);
    }
    private void makeSearchList(String searchWord){
        searchList.clear();
        allListSearch(list, searchWord);
    }

    private void allListSearch(ArrayList<SearchInterface> targetList, int position){
        int startPos = FIRST_CHAR_START_POSITION[position];
        int endPos = startPos+587;
        int size = targetList.size();
        for (int i = 0; i < size; i++) {
            SearchInterface data = targetList.get(i);
            char[] temp_person = data.getWord().toCharArray();

            int person_size = temp_person.length;
            for (int j = 0; j < person_size; j++) {
                if(temp_person[j] >= startPos && temp_person[j] <= endPos){
                    data.setSearchPersonPos(j+1);
                    searchList.add(data);
                    break;
                }
            }
        }
    }

    private void allListSearch(ArrayList<SearchInterface> listData, String searchWord){
        int size = listData.size();
        for(int i=0; i<size; i++){
            SearchInterface data = listData.get(i);
            String temp_person = data.getWord();
            int wordIndex = temp_person.indexOf(searchWord);
            if(wordIndex != -1){
                data.setSearchPersonPos(wordIndex+1);
                searchList.add(data);
            }

        }
    }

}
