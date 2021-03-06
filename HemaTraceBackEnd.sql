USE [master]
GO
/****** Object:  Database [hematrace]    Script Date: 4/12/20 1:34:27 PM ******/
CREATE DATABASE [hematrace]
 CONTAINMENT = NONE
 ON  PRIMARY 
( NAME = N'hematrace', FILENAME = N'D:\rdsdbdata\DATA\hematrace.mdf' , SIZE = 8192KB , MAXSIZE = UNLIMITED, FILEGROWTH = 10%)
 LOG ON 
( NAME = N'hematrace_log', FILENAME = N'D:\rdsdbdata\DATA\hematrace_log.ldf' , SIZE = 1024KB , MAXSIZE = 2048GB , FILEGROWTH = 10%)
GO
ALTER DATABASE [hematrace] SET COMPATIBILITY_LEVEL = 140
GO
IF (1 = FULLTEXTSERVICEPROPERTY('IsFullTextInstalled'))
begin
EXEC [hematrace].[dbo].[sp_fulltext_database] @action = 'enable'
end
GO
ALTER DATABASE [hematrace] SET ANSI_NULL_DEFAULT OFF 
GO
ALTER DATABASE [hematrace] SET ANSI_NULLS OFF 
GO
ALTER DATABASE [hematrace] SET ANSI_PADDING OFF 
GO
ALTER DATABASE [hematrace] SET ANSI_WARNINGS OFF 
GO
ALTER DATABASE [hematrace] SET ARITHABORT OFF 
GO
ALTER DATABASE [hematrace] SET AUTO_CLOSE OFF 
GO
ALTER DATABASE [hematrace] SET AUTO_SHRINK OFF 
GO
ALTER DATABASE [hematrace] SET AUTO_UPDATE_STATISTICS ON 
GO
ALTER DATABASE [hematrace] SET CURSOR_CLOSE_ON_COMMIT OFF 
GO
ALTER DATABASE [hematrace] SET CURSOR_DEFAULT  GLOBAL 
GO
ALTER DATABASE [hematrace] SET CONCAT_NULL_YIELDS_NULL OFF 
GO
ALTER DATABASE [hematrace] SET NUMERIC_ROUNDABORT OFF 
GO
ALTER DATABASE [hematrace] SET QUOTED_IDENTIFIER OFF 
GO
ALTER DATABASE [hematrace] SET RECURSIVE_TRIGGERS OFF 
GO
ALTER DATABASE [hematrace] SET  DISABLE_BROKER 
GO
ALTER DATABASE [hematrace] SET AUTO_UPDATE_STATISTICS_ASYNC OFF 
GO
ALTER DATABASE [hematrace] SET DATE_CORRELATION_OPTIMIZATION OFF 
GO
ALTER DATABASE [hematrace] SET TRUSTWORTHY OFF 
GO
ALTER DATABASE [hematrace] SET ALLOW_SNAPSHOT_ISOLATION OFF 
GO
ALTER DATABASE [hematrace] SET PARAMETERIZATION SIMPLE 
GO
ALTER DATABASE [hematrace] SET READ_COMMITTED_SNAPSHOT OFF 
GO
ALTER DATABASE [hematrace] SET HONOR_BROKER_PRIORITY OFF 
GO
ALTER DATABASE [hematrace] SET RECOVERY FULL 
GO
ALTER DATABASE [hematrace] SET  MULTI_USER 
GO
ALTER DATABASE [hematrace] SET PAGE_VERIFY CHECKSUM  
GO
ALTER DATABASE [hematrace] SET DB_CHAINING OFF 
GO
ALTER DATABASE [hematrace] SET FILESTREAM( NON_TRANSACTED_ACCESS = OFF ) 
GO
ALTER DATABASE [hematrace] SET TARGET_RECOVERY_TIME = 0 SECONDS 
GO
ALTER DATABASE [hematrace] SET DELAYED_DURABILITY = DISABLED 
GO
ALTER DATABASE [hematrace] SET QUERY_STORE = OFF
GO
USE [hematrace]
GO
ALTER DATABASE SCOPED CONFIGURATION SET IDENTITY_CACHE = ON;
GO
ALTER DATABASE SCOPED CONFIGURATION SET LEGACY_CARDINALITY_ESTIMATION = OFF;
GO
ALTER DATABASE SCOPED CONFIGURATION FOR SECONDARY SET LEGACY_CARDINALITY_ESTIMATION = PRIMARY;
GO
ALTER DATABASE SCOPED CONFIGURATION SET MAXDOP = 0;
GO
ALTER DATABASE SCOPED CONFIGURATION FOR SECONDARY SET MAXDOP = PRIMARY;
GO
ALTER DATABASE SCOPED CONFIGURATION SET PARAMETER_SNIFFING = ON;
GO
ALTER DATABASE SCOPED CONFIGURATION FOR SECONDARY SET PARAMETER_SNIFFING = PRIMARY;
GO
ALTER DATABASE SCOPED CONFIGURATION SET QUERY_OPTIMIZER_HOTFIXES = OFF;
GO
ALTER DATABASE SCOPED CONFIGURATION FOR SECONDARY SET QUERY_OPTIMIZER_HOTFIXES = PRIMARY;
GO
USE [hematrace]
GO
/****** Object:  User [pis7ftw]    Script Date: 4/12/20 1:34:29 PM ******/
CREATE USER [pis7ftw] FOR LOGIN [pis7ftw] WITH DEFAULT_SCHEMA=[dbo]
GO
/****** Object:  User [admin]    Script Date: 4/12/20 1:34:29 PM ******/
CREATE USER [admin] FOR LOGIN [admin] WITH DEFAULT_SCHEMA=[dbo]
GO
ALTER ROLE [db_owner] ADD MEMBER [admin]
GO
/****** Object:  Table [dbo].[base]    Script Date: 4/12/20 1:34:29 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[base](
	[base_id] [int] IDENTITY(1,1) NOT NULL,
	[name] [nvarchar](50) NOT NULL,
	[address] [nvarchar](50) NULL,
	[city] [nvarchar](50) NOT NULL,
	[state] [nvarchar](50) NOT NULL,
	[zipcode] [nvarchar](5) NOT NULL,
	[max_num_products] [int] NULL,
	[current_num_products] [int] NULL,
 CONSTRAINT [PK_base] PRIMARY KEY CLUSTERED 
(
	[base_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[lu_product_status]    Script Date: 4/12/20 1:34:31 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[lu_product_status](
	[product_status_id] [int] NOT NULL,
	[product_status] [nvarchar](50) NULL,
	[description] [nvarchar](25) NOT NULL
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[lu_product_type]    Script Date: 4/12/20 1:34:31 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[lu_product_type](
	[product_type_id] [int] NOT NULL,
	[product_type] [nvarchar](50) NULL,
	[description] [nvarchar](50) NOT NULL
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[lu_transaction_type]    Script Date: 4/12/20 1:34:31 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[lu_transaction_type](
	[transaction_type_id] [int] NOT NULL,
	[transaction_type] [nvarchar](50) NULL,
	[description] [varchar](50) NOT NULL
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[product]    Script Date: 4/12/20 1:34:31 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[product](
	[product_id] [int] IDENTITY(1,1) NOT NULL,
	[base_id_fk] [int] NOT NULL,
	[product_type_id] [int] NOT NULL,
	[product_status_id] [int] NOT NULL,
	[donor_number] [nvarchar](25) NOT NULL,
	[expiration_date] [date] NOT NULL,
	[obtained_datetime] [datetime] NOT NULL,
	[is_expiring] [bit] NOT NULL,
	[is_expired] [bit] NOT NULL,
 CONSTRAINT [PK_product] PRIMARY KEY CLUSTERED 
(
	[product_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[transaction]    Script Date: 4/12/20 1:34:31 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[transaction](
	[transaction_id] [int] IDENTITY(1,1) NOT NULL,
	[base_id_fk] [int] NOT NULL,
	[user_id_fk] [int] NOT NULL,
	[product_id_fk] [int] NOT NULL,
	[transaction_type_fk] [int] NOT NULL,
	[transaction_datetime] [datetime] NOT NULL,
	[product_status_id_fk] [int] NULL,
 CONSTRAINT [PK_transaction] PRIMARY KEY CLUSTERED 
(
	[transaction_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[user]    Script Date: 4/12/20 1:34:31 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[user](
	[user_id] [int] IDENTITY(1,1) NOT NULL,
	[first_name] [nvarchar](50) NOT NULL,
	[last_name] [nvarchar](50) NOT NULL,
	[email] [nvarchar](50) NOT NULL,
	[username] [nvarchar](50) NOT NULL,
	[password] [nvarchar](255) NOT NULL,
	[crewId] [nvarchar](255) NULL,
 CONSTRAINT [PK_user] PRIMARY KEY CLUSTERED 
(
	[user_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
ALTER TABLE [dbo].[product]  WITH CHECK ADD  CONSTRAINT [FK_product_base] FOREIGN KEY([base_id_fk])
REFERENCES [dbo].[base] ([base_id])
GO
ALTER TABLE [dbo].[product] CHECK CONSTRAINT [FK_product_base]
GO
ALTER TABLE [dbo].[transaction]  WITH CHECK ADD  CONSTRAINT [FK_transaction_base] FOREIGN KEY([base_id_fk])
REFERENCES [dbo].[base] ([base_id])
GO
ALTER TABLE [dbo].[transaction] CHECK CONSTRAINT [FK_transaction_base]
GO
ALTER TABLE [dbo].[transaction]  WITH CHECK ADD  CONSTRAINT [FK_transaction_user] FOREIGN KEY([user_id_fk])
REFERENCES [dbo].[user] ([user_id])
GO
ALTER TABLE [dbo].[transaction] CHECK CONSTRAINT [FK_transaction_user]
GO
/****** Object:  StoredProcedure [dbo].[sp_deleteUser]    Script Date: 4/12/20 1:34:32 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		Christopher Dierolf
-- Create date: 4-12-2020
-- Description:	Deletes a user from the user table
-- =============================================
CREATE PROCEDURE [dbo].[sp_deleteUser]
	@userId int,
	@RESULT INT OUTPUT
AS
BEGIN
	SET NOCOUNT ON;

    DELETE FROM dbo.[user] WHERE user_id = @userId;

	IF @@ERROR = 0 
	SET @result  = 1
	ELSE SET @result = 0
	RETURN @result
END
GO
/****** Object:  StoredProcedure [dbo].[sp_getCurrentNumProductsForBase]    Script Date: 4/12/20 1:34:32 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		Christopher Dierolf
-- Create date: 2/11/2020
-- Description:	Returns the current number of products assigned to a given base.
-- Inserts it into base table's current_num_products column
-- =============================================
CREATE PROCEDURE [dbo].[sp_getCurrentNumProductsForBase]
	-- Add the parameters for the stored procedure here
	@baseId INT,
	@productCount INT OUTPUT
AS
BEGIN
	SET NOCOUNT ON;

    -- Insert statements for procedure here
	SELECT @productCount = COUNT(*) FROM product WHERE base_id_fk = @baseId
END
GO
/****** Object:  StoredProcedure [dbo].[sp_getMaxNumProductsForBase]    Script Date: 4/12/20 1:34:32 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		Christopher Dierolf
-- Create date: 2-11-2020
-- Description:	Returns the maximum number of products for a given base.
-- =============================================
CREATE PROCEDURE [dbo].[sp_getMaxNumProductsForBase]
	-- Add the parameters for the stored procedure here
	@baseId INT
AS
BEGIN
	SET NOCOUNT ON;

    -- Insert statements for procedure here
	SELECT max_num_products FROM base WHERE base_id = @baseId;
END
GO
/****** Object:  StoredProcedure [dbo].[sp_insertBase]    Script Date: 4/12/20 1:34:32 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		Christopher Dierolf
-- Create date: 1-4-2020
-- Description:	Inserts new base into base table
-- =============================================
CREATE PROCEDURE [dbo].[sp_insertBase]
	@name nvarchar(50),
	@address nvarchar(50),
	@city nvarchar(50),
	@state nvarchar(50),
	@zipcode nvarchar(50)
AS
BEGIN
	SET NOCOUNT ON;

    INSERT INTO dbo.[base] (name, address, city, state, zipcode) VALUES(@name, @address, @city, @state, @zipcode)
END
GO
/****** Object:  StoredProcedure [dbo].[sp_insertProduct]    Script Date: 4/12/20 1:34:32 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[sp_insertProduct]
	@base INT,
	@productType INT,
	@productStatus INT,
	@donorNumber nvarchar(25),
	@expDate date,
	@obtainedDateTime datetime,
	@isExpiring bit,
	@isExpired bit
AS
BEGIN
	SET NOCOUNT ON;

    INSERT INTO dbo.product (base_id_fk, product_type_id, product_status_id, donor_number, expiration_date, obtained_datetime,
	is_expiring, is_expired) VALUES(@base, @productType, @productStatus, @donorNumber, @expDate, @obtainedDateTime, @isExpired, @isExpiring);
END
GO
/****** Object:  StoredProcedure [dbo].[sp_insertProductStatus]    Script Date: 4/12/20 1:34:32 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		Christopher Dierolf
-- Create date: <1-4-2020
-- Description:	Inserts product status into lu_product_status table
-- =============================================
CREATE PROCEDURE [dbo].[sp_insertProductStatus]
	@productStatus nvarchar(50),
	@description nvarchar(50)
AS
BEGIN
	SET NOCOUNT ON;

    INSERT INTO dbo.[lu_product_status] (product_status, description)
	VALUES (@productStatus, @description);
END
GO
/****** Object:  StoredProcedure [dbo].[sp_insertProductType]    Script Date: 4/12/20 1:34:32 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		Christopher Dierolf
-- Create date: 2-4-2020
-- Description:	Inserts into lu_product_type table
-- =============================================
CREATE PROCEDURE [dbo].[sp_insertProductType]
	@productType nvarchar(50),
	@description nvarchar(50)
AS
BEGIN
	SET NOCOUNT ON;

    INSERT INTO dbo.[lu_product_type](product_type, description)
	VALUES (@productType, @description)
END
GO
/****** Object:  StoredProcedure [dbo].[sp_insertTransaction]    Script Date: 4/12/20 1:34:32 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		Christopher Dierolf
-- Create date: 2-4-2020
-- Description:	Inserts into transaction table
-- =============================================
CREATE PROCEDURE [dbo].[sp_insertTransaction]
	@base NVARCHAR(25),
	@user NVARCHAR(50),
	@donorNumber NVARCHAR(50),
	@transactionType NVARCHAR(50),
	@productStatus NVARCHAR(50),
	@Result INT OUTPUT
AS
BEGIN
	SET NOCOUNT ON;

	DECLARE
	@userId INT,
	@productId INT,
	@transactionTypeId INT,
	@productStatusId INT;

	SELECT @userId = [user_id] FROM [user] WHERE crewId = @user;
	SELECT @transactionTypeId = transaction_type_id FROM lu_transaction_type WHERE transaction_type = @transactionType;
	SELECT @productId = product_id FROM product WHERE donor_number = @donorNumber;
	SELECT @productStatusId = product_status_id FROM lu_product_status WHERE product_status = @productStatus;


    INSERT INTO dbo.[transaction](base_id_fk, user_id_fk, product_id_fk, transaction_type_fk, transaction_datetime, product_status_id_fk)
	VALUES(@base, @userId, @productId, @transactionTypeId, GETDATE(), @productStatusId);

	IF @@ERROR = 0 
		SET @result  = 1
	ELSE SET @result = 0
	RETURN @result
END
GO
/****** Object:  StoredProcedure [dbo].[sp_insertTransactionType]    Script Date: 4/12/20 1:34:32 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		Christopher Dierolf
-- Create date: 2-4-2020
-- Description:	Inserts into lu_transaction_type
-- =============================================
CREATE PROCEDURE [dbo].[sp_insertTransactionType]
	@transaction_type nvarchar(50),
	@description nvarchar(50)
AS
BEGIN
	SET NOCOUNT ON;

    INSERT INTO dbo.[lu_transaction_type](transaction_type, description)
	VALUES(@transaction_type, @description);
END
GO
/****** Object:  StoredProcedure [dbo].[sp_insertUser]    Script Date: 4/12/20 1:34:32 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		Christopher Dierolf
-- Create date: 1-4-2020
-- Description:	Inserts a user into the user table.
-- =============================================
CREATE PROCEDURE [dbo].[sp_insertUser]
	-- Add the parameters for the stored procedure here
	@firstName nvarchar(50),
	@lastName nvarchar(50),
	@email nvarchar(50),
	@username nvarchar(50),
	@password nvarchar(255),
    @crewId nvarchar(255),
	@RESULT INT OUTPUT

AS
BEGIN
	SET NOCOUNT ON;

	INSERT INTO dbo.[user] (first_name, last_name, email, username, [password], crewId) VALUES(@firstName, @lastName, @email,
	@username, @password, @crewId);

	IF @@ERROR = 0 
		SET @result  = 1
	ELSE SET @result = 0
	RETURN @result
END
GO
/****** Object:  StoredProcedure [dbo].[sp_loginUser]    Script Date: 4/12/20 1:34:32 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO



CREATE PROCEDURE [dbo].[sp_loginUser] (
	@username nvarchar(50), 
	@password nvarchar(255), 
	@loggedin int OUTPUT) AS

	SELECT @loggedin = user_id  from dbo.[user] where username = @username and password = @password

	IF @@ROWCOUNT = 0 return 0
	-- if query returns a row, login is successful
	RETURN @loggedin
GO
/****** Object:  StoredProcedure [dbo].[sp_retrieveAllTransactionData]    Script Date: 4/12/20 1:34:32 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		Christopher Dierolf
-- Create date: 4-3-2020
-- Description:	Retrieve all transactions from the database.
-- =============================================
CREATE PROCEDURE [dbo].[sp_retrieveAllTransactionData]
AS
BEGIN
	SET NOCOUNT ON;
	SELECT T.transaction_id,
	 P.product_id, 
	 B.[name],
	  CONCAT(U.first_name, ' ', U.last_name) AS 'crew_member',
	   P.donor_number, 
	   PT.product_type, 
	   TT.transaction_type, 
	   T.transaction_datetime
		FROM [transaction] AS T
		JOIN [product] AS P ON P.product_id = T.product_id_fk
		JOIN [base] AS B ON B.base_id = T.base_id_fk
		JOIN [user] AS U ON U.user_id = T.user_id_fk
		JOIN [lu_product_type] AS PT ON PT.product_type_id = P.product_type_id
		JOIN [lu_transaction_type] AS TT ON TT.transaction_type_id = T.transaction_type_fk

END
GO
/****** Object:  StoredProcedure [dbo].[sp_retrieveBaseBloodProducts]    Script Date: 4/12/20 1:34:32 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		Christopher Dierolf
-- Create date: 2-4-2020
-- Description:	Retrieve list of products based on baseID
-- =============================================
CReATE PROCEDURE [dbo].[sp_retrieveBaseBloodProducts]
	-- Add the parameters for the stored procedure here
	@baseId int
AS
BEGIN
	SET NOCOUNT ON;

	SELECT * FROM dbo.[product] WHERE base_id_fk = @baseId

END
GO
/****** Object:  StoredProcedure [dbo].[sp_retrieveBaseNames]    Script Date: 4/12/20 1:34:32 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		Christopher Dierolf
-- Create date: 1/30/2020
-- Description:	Return a list of bases
-- =============================================
CREATE PROCEDURE [dbo].[sp_retrieveBaseNames]
AS
BEGIN
	SET NOCOUNT ON;

	SELECT name FROM base AS Base WHERE name <> 'LifeFlight 9'
END
GO
/****** Object:  StoredProcedure [dbo].[sp_retrieveBaseResultSet]    Script Date: 4/12/20 1:34:32 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		Christopher Dierolf
-- Create date: 2-11-2020
-- Description:	Retrieve the base result set.
-- =============================================
CREATE PROCEDURE [dbo].[sp_retrieveBaseResultSet]
AS
BEGIN
	SET NOCOUNT ON;
	SELECT * FROM base WHERE name <> 'LifeFlight 9';
END
GO
/****** Object:  StoredProcedure [dbo].[sp_retrieveProductDispositions]    Script Date: 4/12/20 1:34:32 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		Christopher Dierolf
-- Create date: 3/1/2020
-- Description:	Retrieve list of valid product dispositions
-- =============================================
CREATE PROCEDURE [dbo].[sp_retrieveProductDispositions]
AS
BEGIN
	SET NOCOUNT ON;

   SELECT product_status FROM lu_product_status WHERE product_status <> 'Checked Out';
END
GO
/****** Object:  StoredProcedure [dbo].[sp_retrieveProductTypes]    Script Date: 4/12/20 1:34:32 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		Christopher Dierolf
-- Create date: 3/20/2020
-- Description:	Retrieve list of available product types
-- =============================================
CREATE PROCEDURE [dbo].[sp_retrieveProductTypes]
AS
BEGIN
	SET NOCOUNT ON;
	SELECT * FROM lu_product_type;
END
GO
/****** Object:  StoredProcedure [dbo].[sp_retrieveTransactionData]    Script Date: 4/12/20 1:34:32 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		Christopher Dierolf
-- Create date: 2-13-2020
-- Description:	Retrieve transaction data from transaction
-- =============================================
CREATE PROCEDURE [dbo].[sp_retrieveTransactionData]
	@baseId INT
AS
BEGIN
	SET NOCOUNT ON;

	SELECT base_id_fk, user_id_fk, product_id_fk, transaction_type_fk FROM [transaction];
END
GO
/****** Object:  StoredProcedure [dbo].[sp_retrieveTransactionDataForBase]    Script Date: 4/12/20 1:34:32 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		Christopher Dierolf
-- Create date: 2-13-2020
-- Description:	Retrieve transaction data for base
-- =============================================
CREATE PROCEDURE [dbo].[sp_retrieveTransactionDataForBase]
	-- Add the parameters for the stored procedure here
	@baseId INT
AS
BEGIN
	SET NOCOUNT ON;

    

	SELECT T.transaction_id,
	 P.product_id, 
	 B.[name],
	  CONCAT(U.first_name, ' ', U.last_name) AS 'crew_member',
	   P.donor_number, 
	   PT.product_type, 
	   TT.transaction_type, 
	   T.transaction_datetime
		FROM [transaction] AS T
		JOIN [product] AS P ON P.product_id = T.product_id_fk
		JOIN [base] AS B ON B.base_id = T.base_id_fk
		JOIN [user] AS U ON U.user_id = T.user_id_fk
		JOIN [lu_product_type] AS PT ON PT.product_type_id = P.product_type_id
		JOIN [lu_transaction_type] AS TT ON TT.transaction_type_id = T.transaction_type_fk
		WHERE B.base_id = @baseId;
END
GO
/****** Object:  StoredProcedure [dbo].[sp_retrieveTransactionTypes]    Script Date: 4/12/20 1:34:32 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		Christopher Dierolf
-- Create date: 2/20/2020
-- Description:	Retrieves a list of transaction types.
-- =============================================
CREATE PROCEDURE [dbo].[sp_retrieveTransactionTypes]
AS
BEGIN
	SET NOCOUNT ON;

    SELECT * FROM lu_transaction_type;
END
GO
/****** Object:  StoredProcedure [dbo].[sp_retrieveUsers]    Script Date: 4/12/20 1:34:32 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		Christopher Dierolf
-- Create date: 4-8-2020
-- Description:	Retrieves resultset of users.
-- =============================================
CREATE PROCEDURE [dbo].[sp_retrieveUsers]
AS
BEGIN
	SET NOCOUNT ON;

    SELECT * FROM dbo.[user];
END
GO
/****** Object:  StoredProcedure [dbo].[sp_updateBase]    Script Date: 4/12/20 1:34:32 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		Christopher Dierolf
-- Create date: 3-28-2020
-- Description:	Updates base table.
-- =============================================
CREATE PROCEDURE [dbo].[sp_updateBase] (
	@baseId INT,
	@baseName nvarchar(50),
	@address nvarchar(50),
	@city nvarchar(50),
	@state nvarchar(50),
	@zip nvarchar(5),
	@result INT OUTPUT)
AS
BEGIN
	SET NOCOUNT ON;
	UPDATE base 
	SET name = @baseName, address = @address, city = @city, state = @state, zipcode = @zip
	WHERE base.base_id = @baseId;

	if @@ERROR = 0
		set @result = 0;
	else set @result = 1;
	return @result
END
GO
/****** Object:  StoredProcedure [dbo].[sp_updateUser]    Script Date: 4/12/20 1:34:32 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		Christopher Dierolf
-- Create date: 4-12-2020
-- Description:	Updates user in the database.
-- =============================================
CREATE PROCEDURE [dbo].[sp_updateUser]
	@userId int,
	@firstName nvarchar(50),
	@lastName nvarchar(50),
	@email nvarchar(50),
	@username nvarchar(50),
    @crewId nvarchar(255),
	@RESULT INT OUTPUT

AS
BEGIN
	SET NOCOUNT ON;

	UPDATE dbo.[user] SET first_name = @firstName, 
	last_name = @lastName, 
	email = @email,
	username = @username,
	crewId = @crewId
	WHERE user_id = @userId;

	IF @@ERROR = 0 
		SET @result  = 1
	ELSE SET @result = 0
	RETURN @result
END
GO
/****** Object:  StoredProcedure [dbo].[sp_validateAndRetrieveUserBasedOnCrewId]    Script Date: 4/12/20 1:34:32 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		Christopher Dierolf
-- Create date: 2/20/2020
-- Description:	Retrieve/validate user based on userId
-- =============================================
CREATE PROCEDURE [dbo].[sp_validateAndRetrieveUserBasedOnCrewId]
	-- Add the parameters for the stored procedure here
	@crewId nvarchar(25),
	@valid INT OUTPUT
AS
BEGIN
	SET NOCOUNT ON;

	SELECT @valid = user_id FROM dbo.[user] WHERE crewId = @CrewId;

	IF @@ROWCOUNT = 0 return 0;

	RETURN @valid
END

GO
USE [master]
GO
ALTER DATABASE [hematrace] SET  READ_WRITE 
GO
