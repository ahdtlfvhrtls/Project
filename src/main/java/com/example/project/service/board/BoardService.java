package com.example.project.service.board;

import com.example.project.dto.board.BoardListDTO;
import com.example.project.dto.board.BoardUpdateDTO;
import com.example.project.dto.board.BoardViewDTO;
import com.example.project.dto.board.BoardWriteDTO;
import com.example.project.dto.file.FileDTO;
import com.example.project.dto.page.Criteria;
import com.example.project.mapper.board.BoardMapper;
import com.example.project.mapper.file.FileMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class BoardService {
    private final BoardMapper boardMapper;
    private final FileMapper fileMapper;

    //application.properties에 저장해둔 file.dir 프로퍼티 값을 가져와서 아래 필드에 넣어준다
    @Value("C:/upload/")
    private String fileDir;

    public void registerBoard(BoardWriteDTO boardWriteDTO){
        boardMapper.insertBoard(boardWriteDTO);
    }

    //MultipartFile : 업로드된 파일을 처리할 때(파일업로드) 사용하는 인터페이스
    public void registerBoardWithFile(BoardWriteDTO boardWriteDTO, List<MultipartFile> files) throws IOException {
        boardMapper.insertBoard(boardWriteDTO);
        Long boardId = boardWriteDTO.getBoardId();

        for(MultipartFile file : files){
            if(file.isEmpty()){
                break;
            }

            FileDTO fileDTO = saveFile(file);
            fileDTO.setBoardId(boardId);
            fileMapper.insertFile(fileDTO);

        }

    }

    public FileDTO saveFile(MultipartFile files) throws IOException {
        //사용자가 올린 파일 이름(확장자를 포함한다)
        String originalFileName = files.getOriginalFilename();

        //파일이름에 붙여줄 uuid 생성
        UUID uuid = UUID.randomUUID();

        //uuid와 파일이름을 합쳐준다
        String systemName = uuid.toString() + "_" + originalFileName;

        //상위경로와 하위 경로를 합쳐준다
        File uploadPath = new File(fileDir + getUploadPath());

        //경로가 존재하지 않는다면(폴더가 만들어지지 않았다면)
        if(!uploadPath.exists()){
            //경로에 필요한 모든 폴더를 생성한다
            uploadPath.mkdirs();
        }

        //전체경로와 파일이름을 연결한다
        File uploadFile = new File(uploadPath, systemName);

        //매개변수로 받은 Multipart 객체가 가진 파일을 우리가 만든 경로와 이름으로 저장한다
        files.transferTo(uploadFile);

        FileDTO fileDTO = new FileDTO();
        fileDTO.setUuid(uuid.toString());
        fileDTO.setName(originalFileName);
        fileDTO.setUploadPath(getUploadPath());
        return fileDTO;
    }

    private String getUploadPath() {
        return new SimpleDateFormat("yyyy/MM/dd").format(new Date());
    }

    public void removeBoard(Long boardId){
        List<FileDTO> fileList = fileMapper.selectList(boardId);
        fileMapper.deleteFile(boardId);
        boardMapper.deleteBoard(boardId);

        for(FileDTO file : fileList){
            if(fileDir == null || file.getUploadPath() == null){
                log.error("fileDir 또는 uploadPath가 null입니다");
                continue; //다음 파일로 진행
            }

            Path targetPath = Paths.get(fileDir, file.getUploadPath(), file.getUuid() + "_" + file.getName());
            //fileDir : C:/upload
            //file.getUploadPath() : /2024/11
            //file.getUuid() : 파일의 고유 식별자(UUID)를 가져옴
            //file.getName() : dog.jpg

            try {
                if(Files.exists(targetPath)){
                    Files.delete(targetPath);
                    log.info("삭제된 파일 : " + targetPath.toString());
                } else {
                    log.info("파일 존재하지 않습니다 : " + targetPath.toString());
                }
            } catch (IOException e) {
                log.error("파일 삭제 실패 : " + targetPath.toString(), e);
                e.printStackTrace();
            }
        }
    }

    public void modifyBoard(BoardUpdateDTO boardUpdateDTO, List<MultipartFile> files) throws IOException {
        boardMapper.updateBoard(boardUpdateDTO);
        Long boardId = boardUpdateDTO.getBoardId();

        fileMapper.deleteFile(boardId);

        for(MultipartFile file : files){
            if(file.isEmpty()){
                break;
            }

            FileDTO fileDTO = saveFile(file);
            fileDTO.setBoardId(boardId);
            fileMapper.insertFile(fileDTO);
        }
    }

    public BoardViewDTO findById(Long boardId){
        return boardMapper.selectById(boardId).orElseThrow(() -> new IllegalStateException("유효하지 않은 게시물"));
    }

    public List<BoardListDTO> findAll(){
        return boardMapper.selectAll();
    }

    public List<BoardListDTO> findAllPage(Criteria criteria){
        return boardMapper.selectAllPage(criteria);
    }

    public int findTotal(){
        return boardMapper.selectTotal();
    }

    public void setFileDir(String fileDir) {
    }

    public File getFile(String dir, String fileName){
        return new File(dir, fileName);
    }

}