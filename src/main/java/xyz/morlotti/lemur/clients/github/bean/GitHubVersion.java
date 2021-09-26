package xyz.morlotti.lemur.clients.github.bean;

import lombok.*;

import java.util.Date;

@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class GitHubVersion
{
	@Data
	@Setter
	@Getter
	@AllArgsConstructor
	@NoArgsConstructor
	@ToString
	public class Commit
	{
		/*------------------------------------------------------------------------------------------------------------*/

		@Data
		@Setter
		@Getter
		@AllArgsConstructor
		@NoArgsConstructor
		@ToString
		public class Author
		{
			private String name;

			private String email;

			private Date date;
		}

		/*------------------------------------------------------------------------------------------------------------*/

		@Data
		@Setter
		@Getter
		@AllArgsConstructor
		@NoArgsConstructor
		@ToString
		public class Committer
		{
			private String name;

			private String email;

			private Date date;
		}

		/*------------------------------------------------------------------------------------------------------------*/

		private Author author;

		private Committer committer;

		private String message;

		/*------------------------------------------------------------------------------------------------------------*/
	}

	private String sha;

	private Commit commit;

	private GitHubUser author;

	private GitHubUser committer;
}
