package challenge.uol.controller;

import challenge.uol.service.UserService;
import challenge.uol.model.dto.UserDto;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/client")
public class UserController {

	private final UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	@ApiOperation(value = "Lista todos os clientes salvos", response = UserDto[].class)
	@RequestMapping(method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<List<UserDto>> getUsers() {
		return ResponseEntity.ok(userService.getAllUsers());
	}

	@ApiOperation(value = "Consulta um cliente por id", response = UserDto.class)
	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<UserDto> getUser(@PathVariable("id") Integer id) {
		return ResponseEntity.ok(userService.getUserById(id));
	}

	@ApiOperation(value = "Cria um cliente")
	@RequestMapping(method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<?> createUser(
			@RequestHeader(value = "x-data-ip") String ip,
			@RequestBody UserDto userDto) {
		userService.createUser(userDto, ip);
		return ResponseEntity.ok().build();
	}

	@ApiOperation("Altera um cliente")
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, produces = "application/json")
	public ResponseEntity<?> updateUser(@RequestAttribute(value = "id") Integer id, @RequestBody UserDto userDto) {
		userService.updateUser(id, userDto);
		return ResponseEntity.ok().build();
	}

	@ApiOperation("Remove um cliente por id")
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "application/json")
	public ResponseEntity<?> deleteUser(@RequestAttribute(value = "id") Integer id) {
		userService.delete(id);
		return null;
	}
}
